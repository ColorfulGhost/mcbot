package cc.vimc.mcbot.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface CoolQUser {

    @Insert({"INSERT INTO coolq_user(`qq`,`flexblelogin_id`,`uuid`,`user_name`)values (#{qq},#{flexbleloginId},#{uuid},#{userName})"})
    int insertUser(@Param("qq") String qq, @Param("flexbleloginId") int flexbleloginId, @Param("uuid") String uuid, @Param("userName") String userName);

    @Select("SELECT user_name FROM coolq_user WHERE user_name=#{userName}")
    String selectUserExist(String userName);
}
