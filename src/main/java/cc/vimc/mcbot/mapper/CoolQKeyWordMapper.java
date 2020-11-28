package cc.vimc.mcbot.mapper;


import cc.vimc.mcbot.entity.CoolQKeyWord;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoolQKeyWordMapper {

    @Insert({"INSERT INTO coolq_keyword (`key_word`, `answer`, `match_type`, `qq`) values (#{keyWord},#{answer},#{matchType},#{qq})"})
    void insert(@Param("keyWord") String keyWord, @Param("answer") String answer, @Param("matchType") Integer matchType, @Param("qq") Long qq);

    @Select("SELECT * FROM coolq_keyword")
    @Results({
            @Result(id = true, property = "id", column = "id", jdbcType = JdbcType.INTEGER),
            @Result(property = "keyWord", column = "key_word", jdbcType = JdbcType.VARCHAR),
            @Result(property = "answer", column = "answer", jdbcType = JdbcType.VARCHAR),
            @Result(property = "matchType", column = "match_type", jdbcType = JdbcType.TINYINT),
            @Result(property = "qq", column = "qq", jdbcType = JdbcType.BIGINT),
            @Result(property = "createTime", column = "create_time", jdbcType = JdbcType.TIMESTAMP),
    })
    List<CoolQKeyWord> getAll();
}
