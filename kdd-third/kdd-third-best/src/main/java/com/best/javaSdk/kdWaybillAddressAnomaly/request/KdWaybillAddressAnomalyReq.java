package com.best.javaSdk.kdWaybillAddressAnomaly.request;

import com.best.javaSdk.BaseRequest;
import com.best.javaSdk.BaseResponse;
import com.best.javaSdk.Parser;


public class KdWaybillAddressAnomalyReq implements BaseRequest {
	private String provinceName;
	private String cityName;
	private String countyName;
	private String address;

    public String getProvinceName()
    {
        return this.provinceName;
    }

    public void setProvinceName(String value)
    {
        this.provinceName = value;
    }

    public String getCityName()
    {
        return this.cityName;
    }

    public void setCityName(String value)
    {
        this.cityName = value;
    }

    public String getCountyName()
    {
        return this.countyName;
    }

    public void setCountyName(String value)
    {
        this.countyName = value;
    }

    public String getAddress()
    {
        return this.address;
    }

    public void setAddress(String value)
    {
        this.address = value;
    }

    public String obtainServiceType() {
        return "KD_WAYBILL_ADDRESS_ANOMALY";
    }

    public BaseResponse makeResponse(String rsp, String format) {
        if ("xml".equalsIgnoreCase(format)) {
			return Parser.coverXml2Object(rsp, com.best.javaSdk.kdWaybillAddressAnomaly.response.KdWaybillAddressAnomalyRsp.class);
		}
		return Parser.convertJson2Object(rsp, com.best.javaSdk.kdWaybillAddressAnomaly.response.KdWaybillAddressAnomalyRsp.class);

    }

}
