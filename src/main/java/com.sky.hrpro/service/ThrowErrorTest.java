package com.sky.hrpro.service;

import com.sky.hrpro.util.ToolsUtils;
import test.Common;

/**
 * @Author: CarryJey @Date: 2018/10/15 15:28:10
 */
public class ThrowErrorTest {

    public void testThrowError(){
        if(1>0){
            /**
             * server通过抛出异常告知client错误码，client去做相应处理提醒
             */
            throw ToolsUtils.newStatusException(Common.ErrorCode.ERROR_UNDEFINED);
        }
    }

}
