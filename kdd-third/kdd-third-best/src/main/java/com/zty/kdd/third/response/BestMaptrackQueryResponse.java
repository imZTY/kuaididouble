package com.zty.kdd.third.response;

import com.best.javaSdk.kdTraceQuery.response.KdTraceQueryRsp;

/**
 * 百世轨迹查询响应
 * 文档地址：https://open.800best.com/doc/api/63627/63658
 */
public class BestMaptrackQueryResponse extends ThirdMaptrackQueryResponse {

    private KdTraceQueryRsp kdTraceQueryRsp;

    public BestMaptrackQueryResponse(KdTraceQueryRsp kdTraceQueryRsp) {
        this.kdTraceQueryRsp = kdTraceQueryRsp;
    }

    public KdTraceQueryRsp getKdTraceQueryRsp() {
        return kdTraceQueryRsp;
    }

    public void setKdTraceQueryRsp(KdTraceQueryRsp kdTraceQueryRsp) {
        this.kdTraceQueryRsp = kdTraceQueryRsp;
    }
}
