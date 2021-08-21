package com.example.dict.bean;

import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/7/31 Copyright © Leon. All rights reserved.
 * Functions: 文字详情的对象
 */
public class WordBean {

    /**
     * reason : 返回成功
     * result : {"id":"d3874a5d247665aa","zi":"阿","py":"a,e","wubi":"bskg","pinyin":"à,ǎ,ā,ē","bushou":"阝","bihua":"7",
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * id : d3874a5d247665aa
         * zi : 阿
         * py : a,e
         * wubi : bskg
         * pinyin : à,ǎ,ā,ē
         * bushou : 阝
         * bihua : 7
         * jijie : ["阿","ā","加在称呼上的词头：阿大。阿爷。阿爹。阿罗汉。阿毛。阿婆。阿弟。阿姊。","","笔画数：7；","部首：阝；","笔顺编号：5212512"]
         * xiangjie : ["阿","ē","【名】","(形声。从阜,可声。本义:大的山陵,大的土山)","同本义〖bigmound〗","阿,大陵也。一曰曲阜也
         */

        private String id;
        private String zi;
        private String py;
        private String wubi;
        private String pinyin;
        private String bushou;
        private String bihua;
        private List<String> jijie;
        private List<String> xiangjie;

        public ResultBean(String id, String zi, String py, String wubi, String pinyin, String bushou, String bihua,
                          List<String> jijie, List<String> xiangjie) {
            this.id = id;
            this.zi = zi;
            this.py = py;
            this.wubi = wubi;
            this.pinyin = pinyin;
            this.bushou = bushou;
            this.bihua = bihua;
            this.jijie = jijie;
            this.xiangjie = xiangjie;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getZi() {
            return zi;
        }

        public void setZi(String zi) {
            this.zi = zi;
        }

        public String getPy() {
            return py;
        }

        public void setPy(String py) {
            this.py = py;
        }

        public String getWubi() {
            return wubi;
        }

        public void setWubi(String wubi) {
            this.wubi = wubi;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getBushou() {
            return bushou;
        }

        public void setBushou(String bushou) {
            this.bushou = bushou;
        }

        public String getBihua() {
            return bihua;
        }

        public void setBihua(String bihua) {
            this.bihua = bihua;
        }

        public List<String> getJijie() {
            return jijie;
        }

        public void setJijie(List<String> jijie) {
            this.jijie = jijie;
        }

        public List<String> getXiangjie() {
            return xiangjie;
        }

        public void setXiangjie(List<String> xiangjie) {
            this.xiangjie = xiangjie;
        }
    }
}
