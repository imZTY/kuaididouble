package com.zty.kdd.service;

import java.util.List;

import com.github.pagehelper.Page;

import com.zty.kdd.DO.TransQueryLogDO;

/**
 * @author tianyi
 * @date 2021-06-06 20:23
 */
public interface TransQueryLogService {

    /**
     * 记录查询日志
     * @param dataList
     * @return
     */
    public int logQuery(List<TransQueryLogDO> dataList);

    /**
     * 分页查询充值订单
     * @param pageDo
     * @return
     */
    public Page<TransQueryLogDO> pageListByPage(TransQueryLogDO pageDo);

}
