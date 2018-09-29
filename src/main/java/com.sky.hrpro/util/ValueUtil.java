package com.sky.hrpro.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hibernate.annotations.common.util.StringHelper.isEmpty;

/**
 * @author CarryJey
 * @since 2018/9/29 19:30:02
 */
public class ValueUtil {

    /**
     * 获取无“-”的Uuid字符串
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    // email 正则
    private static final String emailRegex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    private static final Pattern emailPattern = Pattern.compile(emailRegex);
    // 中国手机号正则
    private static final String cnMobileRegex = "^[1][\\d]{10}";
    private static final Pattern cnMobilePattern = Pattern.compile(cnMobileRegex);
    //国际手机号正则
    private static final String mobileRegex = "^[\\d]{5,11}$";
    private static final Pattern mobilePattern = Pattern.compile(mobileRegex);

    //国际手机区号：（不全舍弃）
    public static final String[] phonePrefixArray = {
        "+54", "+20", "+61", "+45", "+49", "+7", "+33", "+63", "+358", "+57", "+53", "+82", "+31", "+1", "+420", "+60",
        "+1", "+1684", "+1340", "+976", "+51", "+212", "+377", "+52", "+27", "+977", "+234", "+47", "+351", "+81",
        "+46", "+41", "+966", "+421", "+66", "+886", "+90", "+380", "+598", "+34", "+30", "+65", "+64", "+39", "+44",
        "+962", "+84", "+86", "+853", "+852"
    };

    /**
     * 邮箱地址校验正则
     */
    private static final Pattern EMAIL_ADDRESS_PATTERN =
        Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    public static boolean checkEmail(String mail) {
        if (isEmpty(mail)) {
            return false;
        }
        Matcher m = emailPattern.matcher(mail);
        return m.matches();
    }

    public static boolean checkChinaPhone(String chinaPhone) {
        if (isEmpty(chinaPhone)) {
            return false;
        }
        Matcher m = cnMobilePattern.matcher(chinaPhone);
        return m.matches();
    }

    public static boolean checkPhone(String phone) {
        if (isEmpty(phone)) {
            return false;
        }
        String[] phoneArray = phone.split(" ");
        if (phoneArray.length == 1) {
            return checkChinaPhone(phoneArray[0]);
        } else if (phoneArray.length == 2) {
            if (phoneArray[0].equals("+86")) {
                return checkChinaPhone(phoneArray[1]);
            } else if (!isEmpty(phoneArray[1])) {
                return checkPhonePrefix(phoneArray[0]) && mobilePattern.matcher(phoneArray[1]).matches();
            }
        }
        return false;
    }

    public static boolean checkPhonePrefix(String phonePrefix) {
        if (isEmpty(phonePrefix)) {
            return false;
        }
        //不全，废弃国家号校验
        //        for (String s : phonePrefixArray) {
        //            if (s.equals(phonePrefix)) return true;
        //        }
        //        return false;
        return true;
    }

    public static String[] uniqStringArray(String[] strArray) {
        if (strArray == null) {
            return strArray;
        }
        Set<String> tr = new HashSet<String>();
        for (String str : strArray) {
            tr.add(str);
        }
        return tr.toArray(new String[] {});
    }

    public static boolean checkEmailAddress(String address) {
        if (isEmpty(address)) {
            return false;
        }
        Matcher matcher = EMAIL_ADDRESS_PATTERN.matcher(address);
        return matcher.matches();
    }
}
