package com.zty.kdd.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zty.framework.dto.ResultDTO;
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

    @PostMapping("/query")
    public ResultDTO query(MaptrackQueryReqAO reqAO) throws UnsupportedEncodingException {
        return ResultDTO.success(maptrackApi.singleQuery(reqAO));
    }

}
