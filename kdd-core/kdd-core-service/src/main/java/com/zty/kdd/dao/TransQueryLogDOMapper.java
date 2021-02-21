package com.zty.kdd.dao;

import com.zty.kdd.DO.TransQueryLogDO;
import com.zty.kdd.DO.example.TransQueryLogDOExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TransQueryLogDOMapper {
    int countByExample(TransQueryLogDOExample example);

    int deleteByExample(TransQueryLogDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TransQueryLogDO record);

    int insertSelective(TransQueryLogDO record);

    List<TransQueryLogDO> selectByExample(TransQueryLogDOExample example);

    TransQueryLogDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TransQueryLogDO record, @Param("example") TransQueryLogDOExample example);

    int updateByExample(@Param("record") TransQueryLogDO record, @Param("example") TransQueryLogDOExample example);

    int updateByPrimaryKeySelective(TransQueryLogDO record);

    int updateByPrimaryKey(TransQueryLogDO record);
}