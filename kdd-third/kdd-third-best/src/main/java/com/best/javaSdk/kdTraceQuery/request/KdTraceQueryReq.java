package com.best.javaSdk.kdTraceQuery.request;

import com.best.javaSdk.BaseRequest;
import com.best.javaSdk.BaseResponse;
import com.best.javaSdk.Parser;


public class KdTraceQueryReq implements BaseRequest {
	private MailNos mailNos;

    public MailNos getMailNos()
    {
        return this.mailNos;
    }

    public void setMailNos(MailNos value)
    {
        this.mailNos = value;
    }

    public String obtainServiceType() {
        return "KD_TRACE_QUERY";
    }

    public BaseResponse makeResponse(String rsp, String format) {
        if ("xml".equalsIgnoreCase(format)) {
			return Parser.coverXml2Object(rsp, com.best.javaSdk.kdTraceQuery.response.KdTraceQueryRsp.class);
		}
		return Parser.convertJson2Object(rsp, com.best.javaSdk.kdTraceQuery.response.KdTraceQueryRsp.class);

    }

}
