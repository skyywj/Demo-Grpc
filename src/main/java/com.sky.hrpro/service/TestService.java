package com.sky.hrpro.service;

import com.sky.hrpro.dao.TestDao;
import com.sky.hrpro.entity.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: CarryJey
 * @Date: 2018/9/27 上午10:31
 */
@Service
public class TestService {
    @Autowired
    private TestDao testDao;

    /**
     * 普通方法
     */
    public void testService(){
        //此处写逻辑，注意不要在接口层去写过多逻辑，尽量放到service层
        testDao.addTest("ywj",22);
    }

    /**
     * springboot使用事务
     * 全部sql操作执行成功才能成功，一处出错则全部回滚，此方法执行失败
     * 对于drds，事务写只能对同一个分库写，读没有限制
     */
    @Transactional
    public void  testTransactional(){

        TestEntity testEntity = new TestEntity();
         for (int i = 1;i<=100;i++){
             testEntity.setName("ywj"+i);
             testEntity.setAge(i);
             testDao.batchAdd(testEntity);
         }
    }


    /**
     * 测试缓存
     * @param id
     */
    public void testCache(int id){
        TestEntity testEntity = testDao.getTest(id);
        System.out.println(testEntity);
    }


}
