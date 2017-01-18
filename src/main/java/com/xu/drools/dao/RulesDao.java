package com.xu.drools.dao;


import com.xu.drools.bean.Rules;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RulesDao {

    @Select("SELECT * FROM drools_rule where id = #{id}")
    Rules getById(@Param("id") Integer id);

    @Select("INSERT INTO drools_rule(name,rule) VALUE(#{name},#{rule})")
    Integer setRule(@Param("name") String name,@Param("rule") String rule);

    @Select("SELECT * FROM drools_rule order by create_time DESC")
    List<Rules> getRuleList();
}
