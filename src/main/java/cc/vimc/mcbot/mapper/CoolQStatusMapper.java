package cc.vimc.mcbot.mapper;

import cc.vimc.mcbot.entity.CoolQStatus;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoolQStatusMapper {


    @Insert({"INSERT INTO `minecraft`.`coolq_status` (`qq`, `qq_group`,`bangumi_flag`) VALUES (#{qq}, #{qqGroup},#{bangumiFlag})"})
    void insertBangumiStatus(Long qq, Long qqGroup, boolean bangumiFlag);

    @Update({"UPDATE `minecraft`.`coolq_status` SET `bangumi_flag`=#{bangumiFlag} WHERE `qq`=#{qq} AND `qq_group`=#{qqGroup}"})
    void updateBangumiStatus(Long qq, Long qqGroup, boolean bangumiFlag);

    @Update({"UPDATE `minecraft`.`coolq_status` SET `bangumi_exclude`=#{bangumiExclude} WHERE `qq`=#{qq} AND `qq_group`=#{qqGroup}"})
    void updateBangumiExclude(Long qq, Long qqGroup, String bangumiExclude);

    @Select("SELECT `qq`, `qq_group`,`bangumi_exclude` FROM `minecraft`.`coolq_status` WHERE `bangumi_flag`= 1")
    @Results(id = "coolQStatusMapper", value = {
            @Result(id = true, property = "qq", column = "qq", jdbcType = JdbcType.BIGINT),
            @Result(id = true, property = "qqGroup", column = "qq_group", jdbcType = JdbcType.BIGINT),
            @Result( property = "bangumiExclude", column = "bangumi_exclude", jdbcType = JdbcType.VARCHAR),
            @Result(property = "bangumiFlag", column = "bangumi_flag", jdbcType = JdbcType.TINYINT)
    })
    List<CoolQStatus> getCoolQStatusList();

    @Select("SELECT `qq`, `qq_group`,`bangumi_flag`,`bangumi_exclude` FROM `minecraft`.`coolq_status` WHERE `qq`= #{qq} AND `qq_group` = #{qqGroup}")
    @ResultMap({"coolQStatusMapper"})
    CoolQStatus checkExist(Long qq, Long qqGroup);

}
