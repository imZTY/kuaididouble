package com.zty.kdd.service.impl;

import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zty.kdd.DO.TransQueryLogDO;
import com.zty.kdd.DO.example.TransQueryLogDOExample;
import com.zty.kdd.dao.TransQueryLogDOMapper;
import com.zty.kdd.service.TransQueryLogService;

/**
 * @author tianyi
 * @date 2021-06-06 20:26
 */
@Service
public class TransQueryLogServiceImpl implements TransQueryLogService {

    private static final Logger log = LoggerFactory.getLogger(TransQueryLogServiceImpl.class);

    @Autowired
    private TransQueryLogDOMapper transQueryLogDOMapper;

    /**
     * 记录查询日志
     *
     * @param dataList
     * @return
     */
    @Override
    public int logQuery(List<TransQueryLogDO> dataList) {
        // 以后可以优化为批量插入
        int count = 0;
        log.info("即将插入查询记录, {}条, {}", dataList.size(), dataList);
        for (TransQueryLogDO data : dataList) {
            count += transQueryLogDOMapper.insertSelective(data);
        }
        log.info("实际插入查询记录, {}条", count);
        return count;
    }

    /**
     * 分页查询充值订单
     *
     * @param pageDo
     * @return
     */
    @Override
    public Page<TransQueryLogDO> pageListByPage(TransQueryLogDO pageDo) {
        TransQueryLogDOExample example = new TransQueryLogDOExample();
        if (pageDo.getAccountId() != null) {
            example.createCriteria().andAccountIdEqualTo(pageDo.getAccountId());
        }
        example.setOrderByClause("create_time DESC");
        PageHelper.startPage(pageDo.getPage(), pageDo.getRows());
        return (Page<TransQueryLogDO>) transQueryLogDOMapper.selectByExample(example);
    }
}
