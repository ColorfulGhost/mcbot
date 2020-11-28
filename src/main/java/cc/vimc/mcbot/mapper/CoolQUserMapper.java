package cc.vimc.mcbot.mapper;

import cc.vimc.mcbot.entity.CoolQUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ghost
 */
@Repository
public interface CoolQUserMapper {


    @Insert({"INSERT INTO coolq_user(`qq`,`flexblelogin_id`,`uuid`,`user_name`)values (#{qq},#{flexbleloginId},#{uuid},#{userName})"})
    int insertUser(@Param("qq") String qq, @Param("flexbleloginId") int flexbleloginId, @Param("uuid") String uuid, @Param("userName") String userName);


    @Update({"UPDATE `minecraft`.`coolq_user` SET `skin`=#{skin} WHERE  `user_name`=#{userName};"})
    void updateSkin(@Param("skin") String skin, @Param("userName") String userName);

    @Update({"UPDATE `minecraft`.`coolq_user` SET `yuanshen_uid`=#{yuanShenUID} WHERE  `qq`=#{qq};"})
    void updateYuanShenUID(@Param("yuanShenUID") String yuanShenUID, @Param("qq") Long qq);

    @Update({"UPDATE `minecraft`.`coolq_user` SET `yuanshen_cookie`=#{cookie} WHERE  `qq`=#{qq};"})
    void updateYuanShenCookie(@Param("cookie") String cookie, @Param("qq") Long qq);

    @Select("SELECT * FROM coolq_user WHERE user_name=#{userName}")
    @Results(
            id = "coolQUser", value = {
            @Result(id = true, column = "qq", property = "qq", jdbcType = JdbcType.BIGINT),
            @Result(column = "flexblelogin_id", property = "flexbleloginId", jdbcType = JdbcType.INTEGER),
            @Result(column = "user_name", property = "userName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "uuid", property = "uuid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "yuanshen_uid", property = "yuanshenUid", jdbcType = JdbcType.VARCHAR),
            @Result(column = "yuanshen_cookie", property = "yuanshenCookie", jdbcType = JdbcType.VARCHAR),
            @Result(column = "create_time", property = "createTime", jdbcType = JdbcType.TIMESTAMP)
    })
    CoolQUser selectUserExist(String userName);

    @Insert({"INSERT INTO coolq_user(`qq`,`yuanshen_uid`)values (#{qq},#{yuanshenUid})"})
    int insertYuanshenUserUID(@Param("qq") Long qq, @Param("yuanshenUid") String yuanshenUid);

    @Insert({"INSERT INTO coolq_user(`qq`,`cookie`)values (#{qq},#{cookie})"})
    int insertYuanshenUserCookie(@Param("qq") Long qq, @Param("cookie") String cookie);

    @Select("SELECT * FROM coolq_user WHERE qq=#{qq}")
    @ResultMap(value = "coolQUser")
    CoolQUser selectForQQ(Long qq);

    @Select("SELECT  * FROM `minecraft`.`coolq_user` WHERE yuanshen_cookie IS NOT NULL;")
    @ResultMap(value = "coolQUser")
    List<CoolQUser> selectYuanShenCookieNotNull();
}
