package com.zty.kdd.third.request;

/**
 * @author: tianyi.zeng
 * @create: 1/9/2021 - 下午 2:21
 */
public class YtoMaptrackQueryRequest extends ThirdMaptrackQueryRequest {

    private String timestamp = String.valueOf(System.currentTimeMillis());

    /**
     * 请求内容字符串(json)
     */
    private String param;

    /**
     * 签名内容 = Base64( MD5( 请求字段字符串param + 接口方法字符串 + 版本v1 + 客户秘钥secret ) )
     */
    private String sign;

    private String format = "JSON";

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public static class Param {
        /**
         * 圆通物流运单号，一次只能查询一个单号
         */
        private String NUMBER;

        public Param(String NUMBER) {
            this.NUMBER = NUMBER;
        }

        public String getNUMBER() {
            return NUMBER;
        }

        public void setNUMBER(String NUMBER) {
            this.NUMBER = NUMBER;
        }
    }
}
