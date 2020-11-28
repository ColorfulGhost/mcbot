package cc.vimc.mcbot.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    @Select("SELECT * FROM flexiblelogin_users")

    List<FlexBleLoginUser> getAllUser();
//    @Select("SELECT UserID,UUID,Username,Email,LastLogin,LoggedIn FROM flexiblelogin_users WHERE Username=#{userName}")

    @Select("SELECT * FROM flexiblelogin_users WHERE Username=#{userName}")
    FlexBleLoginUser getUserBaseInfoByUserName(@Param("userName") String userName);


    @Select("SELECT * FROM flexiblelogin_users WHERE UserID=#{userId}")
    FlexBleLoginUser getUserBaseInfoByUserId(Integer userId);

    //UPDATE `minecraft_test`.`flexiblelogin_users` SET `Password`='$2y$06$JRxfjttS8FGWhcmzmrni5u92PO0cnutyYgoycRf4OXxDUbtaAFPN' WHERE  `UserID`=1;
    @Update({"UPDATE flexiblelogin_users SET Password=#{password} WHERE Username=#{userName}"})
    void editPassword(@Param("userName") String userName, @Param("password") String password);

    @Select("SELECT * FROM `minecraft`.`flexiblelogin_users` WHERE LastLogin >= (select curdate())")
    List<FlexBleLoginUser> getTodayLoginPlayers();
}
