package com.sky.hrpro.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: CarryJey
 * @Date: 2018/9/27 上午10:33
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Component
public class TestTestService {

    @Autowired
    private TestService testService;

    /**
     * 单元测试，运行某个方法即可
     */
    @Test
    public void test01()
    {
        testService.testService();
    }

    /**
     * 事务单元测试
     */
    @Test
    public void testTransational(){
        testService.testTransactional();
    }

}
