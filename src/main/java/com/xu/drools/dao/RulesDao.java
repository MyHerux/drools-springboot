package com.xu.drools.dao;


import com.xu.drools.bean.Rules;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RulesDao {
    @Select("SELECT * FROM rules where id = #{id}")
    Rules getById(@Param("id") Integer id);
}
