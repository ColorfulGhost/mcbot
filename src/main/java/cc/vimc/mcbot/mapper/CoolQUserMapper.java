package cc.vimc.mcbot.mapper;

import cc.vimc.mcbot.pojo.CoolQUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

/**
 * @author Ghost
 */
@Repository
public interface CoolQUserMapper {


    @Insert({"INSERT INTO coolq_user(`qq`,`flexblelogin_id`,`uuid`,`user_name`)values (#{qq},#{flexbleloginId},#{uuid},#{userName})"})
    int insertUser(@Param("qq") String qq, @Param("flexbleloginId") int flexbleloginId, @Param("uuid") String uuid, @Param("userName") String userName);


    @Update({"UPDATE `minecraft`.`coolq_user` SET `skin`=#{skin} WHERE  `user_name`=#{userName};"})
    void updateSkin(@Param("skin") String skin, @Param("userName") String userName);

    @Select("SELECT * FROM coolq_user WHERE user_name=#{userName}")
    @Results(
            id = "coolQUser", value = {
            @Result(id = true, column = "qq", property = "qq", jdbcType = JdbcType.BIGINT),
            @Result(column = "flexblelogin_id", property = "flexbleloginId", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uuid", property = "uuid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP)
    })
    CoolQUser selectUserExist(String userName);


    int insertUser(@Param("coolq_user") CoolQUser coolQUser);

    @Select("SELECT * FROM coolq_user WHERE qq=#{qq}")
    @ResultMap(value = "coolQUser")
    CoolQUser selectQQExist(Long qq);
}
