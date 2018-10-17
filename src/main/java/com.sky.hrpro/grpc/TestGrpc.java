package com.sky.hrpro.grpc;

import com.google.protobuf.Empty;
import com.sky.hrpro.entity.TestEntity;
import com.sky.hrpro.service.TestService;
import com.test.grpc.ProtodemoGrpc;
import com.test.grpc.TestRequest;
import com.test.grpc.TestResponse;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: CarryJey
 * @Date: 2018/9/27 下午4:55
 */

@GRpcService
public class TestGrpc extends ProtodemoGrpc.ProtodemoImplBase {

    @Autowired
    private TestService testService;

    @Override
    public void test(TestRequest request, StreamObserver<Empty> responseObserver) {
        testService.testService();
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void testHttp(TestRequest request, StreamObserver<TestResponse> responseObserver) {
        TestEntity testEntity = testService.testCache(request.getId());
        TestResponse response = beanToProto(testEntity);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * 实体转换为grpc-Response函数
     * @param testEntity
     * @return
     */
    public TestResponse beanToProto(TestEntity testEntity){
        TestResponse.Builder builder = TestResponse.newBuilder();
        builder.setId(testEntity.getId());
        builder.setName(testEntity.getName());
        builder.setAge(testEntity.getAge());
        return builder.build();
    }
}
