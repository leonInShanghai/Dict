package com.example.dict.utils;

/**
 * Created by 公众号：IT波 on 2021/7/18 Copyright © Leon. All rights reserved.
 * Functions:
 */
public class URLUtils {

    /**
     * https://www.juhe.cn/docs/api/id/156
     * 接口地址：http://v.juhe.cn/xhzd/querypy
     * 返回格式：json/xml
     * 请求方式：http get/post
     * 请求示例：http://v.juhe.cn/xhzd/querypy?key=&word=ju
     * 接口备注：根据汉字的拼音，查询相关的汉字信息
     */
    public static final String PINYIN_URL = "http://v.juhe.cn/xhzd/querypy?key=";

    public static final String BUSHOU_URL = "http://v.juhe.cn/xhzd/querybs?key=";

    public static final String DICTKEY = "3022583457067131a719f84d10efd275";

    public static final String WORD_URL = "http://v.juhe.cn/xhzd/query?key=";

    public static final String CHENGYUKEY = "e8a46192a557700f9a8c9b21eab233e5";

    public static final String CHENGYU_URL = "http://v.juhe.cn/chengyu/query?key=";

    public static String getChengyuUrl(String word) {
        String url = CHENGYU_URL + CHENGYUKEY + "&word="+ word;
        return url;
    }

    /**
     * 查词只能查一个字
     * @param word
     * @return
     */
    public static String getWordUrl(String word) {
        String url = WORD_URL + DICTKEY +"&word=" + word;
        return url;
    }

    public static String getPinyinUrl(String word, int page, int pagesize) {
        String url = PINYIN_URL + DICTKEY + "&word=" + word + "&page=" + page + "&pagesize=" + pagesize;
        return url;
    }

    public static String getBushouUrl(String bs, int page, int pagesize) {
        String url = BUSHOU_URL + DICTKEY + "&word=" + bs + "&page=" + page + "&pagesize=" + pagesize;
        return url;
    }

}
