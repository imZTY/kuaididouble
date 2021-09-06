package com.best.javaSdk.kdCreateOrderNotify.request;

import com.best.javaSdk.kdCreateOrderNotify.request.Item;
import java.util.List;


public class Items {
	private List<Item> item;

    public List<Item>  getItem()
    {
        return this.item;
    }

    public void setItem(List<Item>  value)
    {
        this.item = value;
    }

}
