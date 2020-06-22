package cc.vimc.mcbot.mapper;

import cc.vimc.mcbot.pojo.NewHonorPlayer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface NewHonorPlayerMapper {

    @Select("SELECT  `UUID`,  `usinghonor`, `honors`,  `usehonor`,  `autochange`,  `listhonorsstyle`,  `enableeffects` FROM `minecraft`.`NewHonorPlayerData` WHERE UUID = #{UUID}")
    NewHonorPlayer selectPlayerHonorForUUID(String UUID);

    @Update("UPDATE `NewHonorPlayerData` SET `honors`=#{honors} WHERE `UUID` = #{UUID}")
    void updatePlayerHonorForUUID( @Param("honors") String honors,@Param("UUID") String UUID);

}
