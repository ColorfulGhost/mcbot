package cc.vimc.mcbot.mapper;

import cc.vimc.mcbot.pojo.Soul;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Repository
public interface CoolQSoulMapper {
    @Select({"select * from coolq_soul order by rand() limit 1"})
    @Results({
            @Result(property = "id", column = "id", jdbcType = JdbcType.INTEGER, id = true),
            @Result(property = "title", column = "title", jdbcType = JdbcType.VARCHAR),
            @Result(property = "hits", column = "hits", jdbcType = JdbcType.VARCHAR),
    })
    Soul getSoul();
}
