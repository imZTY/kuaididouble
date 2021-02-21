package com.zty.kdd.dao;

import com.zty.kdd.DO.SecretKeyInfoDO;
import com.zty.kdd.DO.example.SecretKeyInfoDOExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SecretKeyInfoDOMapper {
    int countByExample(SecretKeyInfoDOExample example);

    int deleteByExample(SecretKeyInfoDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SecretKeyInfoDO record);

    int insertSelective(SecretKeyInfoDO record);

    List<SecretKeyInfoDO> selectByExample(SecretKeyInfoDOExample example);

    SecretKeyInfoDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SecretKeyInfoDO record, @Param("example") SecretKeyInfoDOExample example);

    int updateByExample(@Param("record") SecretKeyInfoDO record, @Param("example") SecretKeyInfoDOExample example);

    int updateByPrimaryKeySelective(SecretKeyInfoDO record);

    int updateByPrimaryKey(SecretKeyInfoDO record);
}