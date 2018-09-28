package com.sky.hrpro.dao;

import com.sky.hrpro.entity.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @Author: CarryJey
 * @Date: 2018/9/27 上午10:29
 */
@Repository
public class TestDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void addTest(String name ,int age){
        String sql = "insert into test (name,age) values(:name,:age)";
        int rows = jdbcTemplate.update(sql,new MapSqlParameterSource("name",name).addValue("age",age));
        assert rows == 1;
    }


    /**
     * 传参 实体 插入示例
     *
     * @param
     */
    public void batchAdd(TestEntity testEntity){
        String sql = "insert into test(name,age) values(:name,:age)";
        int rows = jdbcTemplate.update(sql,new BeanPropertySqlParameterSource(testEntity));
        assert rows == 1;
    }


}
