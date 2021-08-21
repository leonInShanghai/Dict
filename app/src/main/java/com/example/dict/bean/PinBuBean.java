package com.example.dict.bean;

import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/7/18 Copyright © Leon. All rights reserved.
 * Functions: 拼音和部首的bean对象
 */
public class PinBuBean {
    /**
     * reason : success
     * result : [{"id":"1","bihua":"1","bushou":"丨","pinyin_key":"Z","pinyin":"zuan"},]
     * error_code : 0
     */

    private String reason;
    private int error_code;
    private List<ResultBean> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 1
         * bihua : 1
         * bushou : 丨
         * pinyin_key : Z
         * pinyin : zuan
         */

        private String id;
        private String bihua;
        private String bushou;
        private String pinyin_key;
        private String pinyin;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBihua() {
            return bihua;
        }

        public void setBihua(String bihua) {
            this.bihua = bihua;
        }

        public String getBushou() {
            return bushou;
        }

        public void setBushou(String bushou) {
            this.bushou = bushou;
        }

        public String getPinyin_key() {
            return pinyin_key;
        }

        public void setPinyin_key(String pinyin_key) {
            this.pinyin_key = pinyin_key;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "id='" + id + '\'' +
                    ", bihua='" + bihua + '\'' +
                    ", bushou='" + bushou + '\'' +
                    ", pinyin_key='" + pinyin_key + '\'' +
                    ", pinyin='" + pinyin + '\'' +
                    '}';
        }
    }
}
