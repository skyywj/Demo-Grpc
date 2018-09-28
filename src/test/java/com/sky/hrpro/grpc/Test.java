package com.sky.hrpro.grpc;



/**
 * @Author: YanWenjie
 * @Date: 2018/9/27 下午5:25
 */

import com.test.grpc.ProtodemoGrpc;
import com.test.grpc.testRequest;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.invoke.MethodHandles;


/**
 * grpc 单元测试还是比较其他的麻烦一些的，涉及request
 * 1、建立TestBase(复制过去就行)，TestBase还需要PrintRequesIdClientIntercepter
 * 2、class继承TestBase
 * 3、重写doGetStub
 * 4、通过getstub（）调用测试方法
 */


/**
 * @author CarryJey
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Component
public class Test extends TestBase<ProtodemoGrpc.ProtodemoBlockingStub> {

    /**
     * 日志打印,在代码中应注重适当的添加必要的错误日志（注意有些地方不能用error，因为会block程序进行）
     * 用到最多的是logger.error()  和 logger.warn()
     */
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    protected ProtodemoGrpc.ProtodemoBlockingStub doGetStub() {
        return ProtodemoGrpc.newBlockingStub(getManagedChannel());
    }

    /**
     * setid(1)没有任何意义，根本没用传参
     * 此处为了写一个传参 和 一个空返回值的例子  所以写了个传参id
     */
    @org.junit.Test
    public void test(){
        getStub().test(testRequest.newBuilder().setId(1).build());
        logger.error("test error..........");
    }
}
