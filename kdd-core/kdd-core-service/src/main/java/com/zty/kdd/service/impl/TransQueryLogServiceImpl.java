package com.zty.kdd.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zty.kdd.DO.TransQueryLogDO;
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
}
