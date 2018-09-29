package com.sky.hrpro.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: CarryJey
 * @Date: 2018/9/29 12:35:13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Component
public class TestRedissonService {

    @Autowired
    private RedissonTestService redissonService;

    @Test
    public void testRedisson(){
        redissonService.testRedisson();
    }
}
