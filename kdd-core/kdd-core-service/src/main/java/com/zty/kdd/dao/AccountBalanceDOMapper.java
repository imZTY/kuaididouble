package com.zty.kdd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.zty.kdd.DO.AccountBalanceDO;
import com.zty.kdd.DO.example.AccountBalanceDOExample;

@Mapper
@Component
public interface AccountBalanceDOMapper {
    int countByExample(AccountBalanceDOExample example);

    int deleteByExample(AccountBalanceDOExample example);

    int deleteByPrimaryKey(Integer accountId);

    int insert(AccountBalanceDO record);

    int insertSelective(AccountBalanceDO record);

    List<AccountBalanceDO> selectByExample(AccountBalanceDOExample example);

    AccountBalanceDO selectByPrimaryKey(Integer accountId);

    int updateByExampleSelective(@Param("record") AccountBalanceDO record, @Param("example") AccountBalanceDOExample example);

    int updateByExample(@Param("record") AccountBalanceDO record, @Param("example") AccountBalanceDOExample example);

    int updateByPrimaryKeySelective(AccountBalanceDO record);

    int updateByPrimaryKey(AccountBalanceDO record);

    // 冻结余额
    int checkAndFrozen(AccountBalanceDO queryDo);

    // 解冻余额
    int checkAndUnfrozen(AccountBalanceDO queryDo);

    // 实扣余额
    int checkAndCut(AccountBalanceDO queryDo);
}