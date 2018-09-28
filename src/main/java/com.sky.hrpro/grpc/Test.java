package com.sky.hrpro.grpc;

import com.google.protobuf.Empty;
import com.sky.hrpro.service.TestService;
import com.test.grpc.ProtodemoGrpc;
import com.test.grpc.testRequest;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: CarryJey
 * @Date: 2018/9/27 下午4:55
 */

@GRpcService
public class Test extends ProtodemoGrpc.ProtodemoImplBase {

    @Autowired
    private TestService testService;

    @Override
    public void test(testRequest request, StreamObserver<Empty> responseObserver) {
        testService.testService();
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
