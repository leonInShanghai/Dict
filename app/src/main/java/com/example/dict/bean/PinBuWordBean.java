package com.example.dict.bean;

import java.util.List;

/**
 * Created by 公众号：IT波 on 2021/7/25 Copyright © Leon. All rights reserved.
 * Functions: 拼音查询，部首查询，右侧的GridView对应的数据源
 */
public class PinBuWordBean {


    /**
     * reason : 返回成功
     * result : {"list":[{"id":"6fee5fa25472dba7","zi":"八","py":"ba","wubi":"wty","pinyin":"bā","bushou":"八","bihua":
     * "2"},}
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
         * list : [{"id":"6fee5fa25472dba7","zi":"八","py":"ba","wubi":"wty","pinyin":"bā","bushou":"八","bihua":"2"},]
         * page : 1
         * pagesize : 10
         * totalpage : 3
         * totalcount : 21
         */

        private int page;
        private int pagesize;
        private int totalpage;
        private int totalcount;
        private List<ListBean> list;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPagesize() {
            return pagesize;
        }

        public void setPagesize(int pagesize) {
            this.pagesize = pagesize;
        }

        public int getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(int totalpage) {
            this.totalpage = totalpage;
        }

        public int getTotalcount() {
            return totalcount;
        }

        public void setTotalcount(int totalcount) {
            this.totalcount = totalcount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 6fee5fa25472dba7
             * zi : 八
             * py : ba
             * wubi : wty
             * pinyin : bā
             * bushou : 八
             * bihua : 2
             */

            private String id;
            private String zi;
            private String py;
            private String wubi;
            private String pinyin;
            private String bushou;
            private String bihua;

            public ListBean(String id, String zi, String py, String wubi, String pinyin, String bushou, String bihua) {
                this.id = id;
                this.zi = zi;
                this.py = py;
                this.wubi = wubi;
                this.pinyin = pinyin;
                this.bushou = bushou;
                this.bihua = bihua;
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

            @Override
            public String toString() {
                return "ListBean{" +
                        "id='" + id + '\'' +
                        ", zi='" + zi + '\'' +
                        ", py='" + py + '\'' +
                        ", wubi='" + wubi + '\'' +
                        ", pinyin='" + pinyin + '\'' +
                        ", bushou='" + bushou + '\'' +
                        ", bihua='" + bihua + '\'' +
                        '}';
            }
        }
    }
}
