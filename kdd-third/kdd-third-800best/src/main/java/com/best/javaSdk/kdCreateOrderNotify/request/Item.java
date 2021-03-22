package com.best.javaSdk.kdCreateOrderNotify.request;



public class Item {
	private String itemName;
	private String number;
	private double itemValue;
	private String remark;

    public String getItemName()
    {
        return this.itemName;
    }

    public void setItemName(String value)
    {
        this.itemName = value;
    }

    public String getNumber()
    {
        return this.number;
    }

    public void setNumber(String value)
    {
        this.number = value;
    }

    public double getItemValue()
    {
        return this.itemValue;
    }

    public void setItemValue(double value)
    {
        this.itemValue = value;
    }

    public String getRemark()
    {
        return this.remark;
    }

    public void setRemark(String value)
    {
        this.remark = value;
    }


}
