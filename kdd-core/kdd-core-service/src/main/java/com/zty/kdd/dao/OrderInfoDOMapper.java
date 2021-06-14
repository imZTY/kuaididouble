package com.zty.kdd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zty.kdd.DO.OrderInfoDO;
import com.zty.kdd.DO.example.OrderInfoDOExample;

public interface OrderInfoDOMapper {
    int countByExample(OrderInfoDOExample example);

    int deleteByExample(OrderInfoDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderInfoDO record);

    int insertSelective(OrderInfoDO record);

    List<OrderInfoDO> selectByExample(OrderInfoDOExample example);

    OrderInfoDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderInfoDO record, @Param("example") OrderInfoDOExample example);

    int updateByExample(@Param("record") OrderInfoDO record, @Param("example") OrderInfoDOExample example);

    int updateByPrimaryKeySelective(OrderInfoDO record);

    int updateByPrimaryKey(OrderInfoDO record);
}