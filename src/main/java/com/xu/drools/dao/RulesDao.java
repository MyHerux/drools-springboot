package com.xu.drools.dao;


import com.xu.drools.bean.Rules;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RulesDao {

    @Select("SELECT * FROM drools_rule where id = #{id}")
    Rules getById(@Param("id") Integer id);

    @Insert("INSERT INTO drools_rule(name,rule) VALUE(#{name},#{rule})")
    Integer setRule(@Param("name") String name,@Param("rule") String rule);

    @Select("SELECT * FROM drools_rule order by create_time DESC")
    List<Rules> getRuleList();

    @Update("UPDATE drools_rule SET visible=0 WHERE id = #{id}")
    Integer deleteRule(@Param("id") Integer id);

    @Update("UPDATE drools_rule SET rule= #{rule} AND name = #{name} WHERE id = #{id}")
    Integer updateRule(@Param("id") Integer id,@Param("name") String name,@Param("rule") String rule);
}
