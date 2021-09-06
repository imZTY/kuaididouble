package com.zty.kdd.third.request;

import java.util.ArrayList;
import java.util.List;

import com.best.javaSdk.kdTraceQuery.request.KdTraceQueryReq;
import com.best.javaSdk.kdTraceQuery.request.MailNos;

/**
 * 百世物流查询，定制化参数类
 */
public class BestMaptrackQueryRequest extends ThirdMaptrackQueryRequest {

    private KdTraceQueryReq kdTraceQueryReq;

    /**
     * 设置单个运单号
     * @param num 百世运单号
     */
    public void setSingleNum(String num) {
        if (this.kdTraceQueryReq == null) {
            this.kdTraceQueryReq = new KdTraceQueryReq();
        }
        MailNos mailNos = new MailNos();
        List<String> mailNo = new ArrayList<>();
        mailNo.add(num);
        mailNos.setMailNo(mailNo);
        this.kdTraceQueryReq.setMailNos(mailNos);
    }

    public KdTraceQueryReq getKdTraceQueryReq() {
        return kdTraceQueryReq;
    }

    public void setKdTraceQueryReq(KdTraceQueryReq kdTraceQueryReq) {
        this.kdTraceQueryReq = kdTraceQueryReq;
    }
}
