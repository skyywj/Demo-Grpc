package com.sky.hrpro.dao;

import com.sky.hrpro.entity.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: CarryJey
 * @Date: 2018/9/27 上午10:29
 */
@Repository
@CacheConfig(cacheNames = "testgrpc_cache")
public class TestDao {
    private static final BeanPropertyRowMapper<TestEntity> rowMapper = BeanPropertyRowMapper.newInstance(TestEntity.class);


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

    @Cacheable(key = "'test_id_' + #id")
    public TestEntity getTest(int id){
        String sql = "SELECT id,name,age FROM test WHERE id =:id";
        List<TestEntity> list = jdbcTemplate.query(sql, new MapSqlParameterSource("id", id),rowMapper);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }
}
