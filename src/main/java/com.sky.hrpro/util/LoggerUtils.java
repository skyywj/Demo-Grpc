package com.sky.hrpro.util;

import com.google.common.collect.ObjectArrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;

/**
 *  @Author: CarryJey
 *  @Date: 2018/9/28 下午4:04
 *
 * <p>附加请求信息到日志中
 */
public abstract class LoggerUtils {
    private static final Logger logger = LoggerFactory.getLogger("LoggerUtils");

    private LoggerUtils() {}

    public static void debug(String format, Object... arguments) {
        attach(format, arguments, logger::debug);
    }

    public static void info(String format, Object... arguments) {
        attach(format, arguments, logger::info);
    }

    public static void warn(String format, Object... arguments) {
        attach(format, arguments, logger::warn);
    }

    public static void error(String format, Object... arguments) {
        attach(format, arguments, logger::error);
    }

    /**
     * TODO 实际的测试一下 stackTrace 的性能
     *
     * @param format
     * @param arguments
     * @param consumer
     */
    private static void attach(String format, Object[] arguments, BiConsumer<String, Object[]> consumer) {
        String codeLine = codeLine();

        //此处应通过拦截器来获取值，先写死了
        String appVersion = "1.0.0";
        String requestId = "001";
        long userId = 001;

        consumer.accept(
                "{} - app version: {}, request id: {}, user id: {}, " + format,
                ObjectArrays.concat(new Object[] {codeLine, appVersion, requestId, userId}, arguments, Object.class));
    }

    private static String codeLine() {
        String className = "";
        String methodName = "";
        int lineNumber = -1;

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 4) {
            StackTraceElement stackTraceElement = stackTrace[4];
            className = stackTraceElement.getClassName();
            methodName = stackTraceElement.getMethodName();
            lineNumber = stackTraceElement.getLineNumber();
        }

        return className + '.' + methodName + '#' + lineNumber;
    }
}
