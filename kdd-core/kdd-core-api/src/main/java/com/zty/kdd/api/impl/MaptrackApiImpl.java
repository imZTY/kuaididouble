package com.zty.kdd.api.impl;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import com.zty.kdd.ao.MaptrackQueryReqAO;
import com.zty.kdd.ao.MaptrackQueryRespAO;
import com.zty.kdd.api.MaptrackApi;
import com.zty.kuaididouble.third.AbstractCustomizedService;

/**
 * @author tianyi
 * @date 2021-03-01 22:49
 */
@Service
public class MaptrackApiImpl implements MaptrackApi {

    @Resource(name = "Yunda_Customized")
    private AbstractCustomizedService thirdService;

    @Override
    public MaptrackQueryRespAO query(MaptrackQueryReqAO reqAO) {
        // TODO: 2021/3/2 根据快递公司编码，拼接BEAN名称
        String result = thirdService.query(null, JSON.parseObject("{\n" +
                "    \"mailno\":\"" + reqAO.getParam().getOrder() + "\"\n" +
                "}"));
        return new MaptrackQueryRespAO();
    }
}
