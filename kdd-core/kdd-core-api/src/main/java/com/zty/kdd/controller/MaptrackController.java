package com.zty.kdd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zty.framework.dto.ResultDTO;
import com.zty.kdd.annotation.CheckSign;
import com.zty.kdd.ao.MaptrackQueryReqAO;
import com.zty.kdd.api.MaptrackApi;

/**
 * @author tianyi
 * @date 2021-02-21 15:46
 */
@RestController
@RequestMapping("/maptrack")
public class MaptrackController {

    @Autowired
    private MaptrackApi maptrackApi;

    @CheckSign
    @RequestMapping(value =  "/query", method = {RequestMethod.POST ,RequestMethod.GET })
    public ResultDTO query(MaptrackQueryReqAO reqAO) throws Exception {
        return ResultDTO.success(maptrackApi.singleQuery(reqAO));
    }

}
