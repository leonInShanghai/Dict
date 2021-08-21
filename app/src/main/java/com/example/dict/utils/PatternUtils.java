package com.example.dict.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 公众号：IT波 on 2021/8/8 Copyright © Leon. All rights reserved.
 * Functions: 过滤图片识别出字符串工具类。
 */
public class PatternUtils {

    /**
     * 剔除数字
     */
    public static String removeDigital(String value) {
        // \ 转义符 加\d 是一个正字表达式，标识所有数字及0-9
        Pattern p = Pattern.compile("[\\d]");
        Matcher matcher = p.matcher(value);
        // 将value中的数字全部替换成空格
        String result = matcher.replaceAll("");
        return result;
    }

    /**
     * 剔除字母
     */
    public static String removeLetter(String value) {
        // \ 转义符 加\d 是一个正字表达式，标识所有数字及0-9
        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher matcher = p.matcher(value);
        // 将value中的字母全部替换成空格
        String result = matcher.replaceAll("");
        return result;
    }

    /**
     * 剔除标点符号
     */
    public static String removePunctuation(String value) {
        // \ 转义符 加\d 是一个正字表达式，标识所有数字及0-9
        Pattern p = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]");
        Matcher matcher = p.matcher(value);
        // 将value中的字母全部替换成空格
        String result = matcher.replaceAll("");
        return result;
    }

    /**
     * 剔除全部数字,字母,标点符号
     */
    public static String removeAll(String value) {
        // \ 转义符 加\d 是一个正字表达式，标识所有数字及0-9
        Pattern p = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|a-zA-Z\\d]");
        Matcher matcher = p.matcher(value);
        // 将value中的字母全部替换成空格
        String result = matcher.replaceAll("");
        return result;
    }
}
