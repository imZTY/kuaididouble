package com.best.javaSdk.kdTraceQuery.response;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Trace {

    private static final DateTimeFormatter ACCEPT_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String acceptTime;
	private String acceptAddress;
	private String scanType;
	private String remark;

    public String getAcceptTime()
    {
        return this.acceptTime;
    }

    public void setAcceptTime(String value)
    {
        this.acceptTime = value;
    }

    public String getAcceptAddress()
    {
        return this.acceptAddress;
    }

    public void setAcceptAddress(String value)
    {
        this.acceptAddress = value;
    }

    public String getScanType()
    {
        return this.scanType;
    }

    public void setScanType(String value)
    {
        this.scanType = value;
    }

    public String getRemark()
    {
        return this.remark;
    }

    public void setRemark(String value)
    {
        this.remark = value;
    }

    public long getLongAcceptTime() {
        return LocalDateTime.parse(this.acceptTime, ACCEPT_TIME_FORMAT).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }
}
