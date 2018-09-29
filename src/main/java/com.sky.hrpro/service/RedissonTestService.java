package com.sky.hrpro.service;

import com.sky.hrpro.HrProsApplication;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: CarryJey
 * @Date: 2018/9/29 12:28:35
 */
@Service
public class RedissonTestService {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private HrProsApplication.RedisConfig redisConfig;

    public void testRedisson(){
        RBucket<String > bucket = redissonClient.getBucket(redisConfig.addPrefix("CarryJey"));
        System.out.println(bucket.getName());
    }
}
