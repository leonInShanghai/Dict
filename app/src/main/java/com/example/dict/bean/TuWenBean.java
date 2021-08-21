package com.example.dict.bean;

import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/8/7 Copyright © Leon. All rights reserved.
 * Functions: 扫描图片上的文字（OCR）结果对象 此处需要用到百度的sdk
 */
public class TuWenBean {

    /**
     * words_result : [{"words":"轴回图"},{"words":"确加仓,这3只芯片个股或"},{"words":"友,金前的女友,蒲巴甲的女"}]
     * words_result_num : 3
     * direction : 0
     * log_id : 1423940540466951190
     */

    private int words_result_num;
    private int direction;
    private long log_id;
    private List<WordsResultBean> words_result;

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public List<WordsResultBean> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WordsResultBean> words_result) {
        this.words_result = words_result;
    }

    public static class WordsResultBean {

        /**
         * words : 轴回图
         */
        private String words;

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }
}
