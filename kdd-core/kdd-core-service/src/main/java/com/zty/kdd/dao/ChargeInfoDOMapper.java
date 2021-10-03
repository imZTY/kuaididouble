package com.zty.kdd.dao;

import com.zty.kdd.DO.ChargeInfoDO;
import com.zty.kdd.DO.example.ChargeInfoDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ChargeInfoDOMapper {
    int countByExample(ChargeInfoDOExample example);

    int deleteByExample(ChargeInfoDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ChargeInfoDO record);

    int insertSelective(ChargeInfoDO record);

    List<ChargeInfoDO> selectByExample(ChargeInfoDOExample example);

    ChargeInfoDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ChargeInfoDO record, @Param("example") ChargeInfoDOExample example);

    int updateByExample(@Param("record") ChargeInfoDO record, @Param("example") ChargeInfoDOExample example);

    int updateByPrimaryKeySelective(ChargeInfoDO record);

    int updateByPrimaryKey(ChargeInfoDO record);
}