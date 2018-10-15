package com.sky.hrpro.util;

import test.Common.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableSet;
import com.google.protobuf.Descriptors;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.util.Timestamps;
import com.smartisan.common.util.DateTimeUtils;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.DefaultCookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import io.netty.handler.codec.http.cookie.ServerCookieEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Set;
/**
 * @Author: CarryJey @Date: 2018/10/15 15:26:32
 */
public abstract class ToolsUtils {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String FIELD_NAME_DATUM = "datum";

    private ToolsUtils() {}

    private static final JsonFormat.Printer printer = JsonFormat.printer().preservingProtoFieldNames();

    public static String toJson(MessageOrBuilder messageOrBuilder) {
        try {
            return printer.print(messageOrBuilder);
        } catch (InvalidProtocolBufferException e) {
            logger.warn("Print to json error, class: {}.", messageOrBuilder.getClass(), e);
        }
        return "";
    }

    public static Timestamp parseTimestamp(String string) {
        try {
            return Timestamps.parse(string);
        } catch (ParseException e) {
            logger.info("Parse timestamp error: {}.", string, e);
            throw new RuntimeException(e);
        }
    }

    public static Timestamp ldtToTimestamp(LocalDateTime ldt) {
        return Timestamps.fromMillis(DateTimeUtils.utcLdtToMilli(ldt));
    }

    public static LocalDateTime timestampToLdt(Timestamp timestamp) {
        return DateTimeUtils.milliToUtcLdt(Timestamps.toMillis(timestamp));
    }

    public static Status newStatus(ErrorCode error) {
        return newStatus(Status.UNKNOWN, error);
    }

    public static Status newStatus(Status status, ErrorCode error) {
        return status.withDescription(String.valueOf(error.getNumber()));
    }

    public static StatusRuntimeException newStatusException(ErrorCode error) {
        return newStatusException(Status.UNKNOWN, error);
    }

    public static StatusRuntimeException newStatusException(Status status, ErrorCode error) {
        return newStatus(status, error).asRuntimeException();
    }

    public static ErrorCode getError(Status status) {
        if (status == null || status.getDescription() == null) {
            return ErrorCode.UNRECOGNIZED;
        }

        try {
            int i = Integer.parseInt(status.getDescription());
            ErrorCode error = ErrorCode.forNumber(i);
            return error == null ? ErrorCode.UNRECOGNIZED : error;
        } catch (NumberFormatException e) {
            logger.warn("Parse error.", e);
            return ErrorCode.UNRECOGNIZED;
        }
    }

    public static ErrorCode getError(StatusRuntimeException e) {
        return getError(e.getStatus());
    }

    public static boolean isSameError(Status status, ErrorCode error) {
        return getError(status) == error;
    }

    public static boolean isSameError(StatusRuntimeException e, ErrorCode error) {
        return isSameError(e.getStatus(), error);
    }

    public static Descriptors.FieldDescriptor getOneofFieldDescriptor(
        Descriptors.OneofDescriptor oneofDescriptor, int datumCase) {
        for (Descriptors.FieldDescriptor fieldDescriptor : oneofDescriptor.getFields()) {
            if (fieldDescriptor.getNumber() == datumCase) {
                return fieldDescriptor;
            }
        }

        return null;
    }

    public static String encodeCookie(String name, String value, int maxAge, boolean secure, boolean httpOnly) {
        Cookie cookie = new DefaultCookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setSecure(secure);
        cookie.setHttpOnly(httpOnly);

        return ServerCookieEncoder.STRICT.encode(cookie);
    }

    public static Set<Cookie> decodeCookie(String cookieValue) {
        if (cookieValue == null || cookieValue.isEmpty()) {
            return ImmutableSet.of();
        }

        try {
            return ServerCookieDecoder.STRICT.decode(cookieValue);
        } catch (Exception e) {
            logger.warn("parse cookie error: {}.", cookieValue, e);
            return ImmutableSet.of();
        }
    }

    // 使用GenericJackson2JsonRedisSerializer
    public static String toJsonString(Object object) {
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        if (object == null) {
            return null;
        }
        try {
            Field mapperField = ReflectionUtils.findField(GenericJackson2JsonRedisSerializer.class, "mapper");
            mapperField.setAccessible(true);

            ObjectMapper objectMapper = (ObjectMapper) mapperField.get(serializer);
            // java8 time
            objectMapper.findAndRegisterModules();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            mapperField.setAccessible(false);
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static GeneratedMessageV3 parsePbMessage(
        Descriptors.FieldDescriptor fieldDescriptor, Descriptors.Descriptor descriptor, byte[] datum) {
        try {
            String className =
                fieldDescriptor.getFile().getOptions().getJavaPackage()
                    + "."
                    + descriptor.getName()
                    + "."
                    + fieldDescriptor.getMessageType().getName();

            Class clazz = ClassUtils.forName(className, null);

            Method parseFrom = ReflectionUtils.findMethod(clazz, "parseFrom", byte[].class);
            return (GeneratedMessageV3) ReflectionUtils.invokeMethod(parseFrom, null, datum);
        } catch (Exception e) {
            LoggerUtils.warn("parsePbMessage error, datum: {}. descriptor:{}", datum, descriptor);
            return null;
        }
    }
}
