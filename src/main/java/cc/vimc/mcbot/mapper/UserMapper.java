package cc.vimc.mcbot.mapper;

import cc.vimc.mcbot.pojo.FlexBleLoginUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    @Select("SELECT * FROM flexiblelogin_users")
    List<FlexBleLoginUser> getAllUser();
//    @Select("SELECT UserID,UUID,Username,Email,LastLogin,LoggedIn FROM flexiblelogin_users WHERE Username=#{userName}")

    @Select("SELECT * FROM flexiblelogin_users WHERE Username=#{userName}")
    FlexBleLoginUser getUserBaseInfo(@Param("userName")String userName);
}
