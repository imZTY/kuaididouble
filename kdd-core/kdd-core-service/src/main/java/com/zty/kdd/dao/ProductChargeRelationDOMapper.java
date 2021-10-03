package com.zty.kdd.dao;

import com.zty.kdd.DO.ProductChargeRelationDO;
import com.zty.kdd.DO.example.ProductChargeRelationDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface ProductChargeRelationDOMapper {
    int countByExample(ProductChargeRelationDOExample example);

    int deleteByExample(ProductChargeRelationDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProductChargeRelationDO record);

    int insertSelective(ProductChargeRelationDO record);

    List<ProductChargeRelationDO> selectByExample(ProductChargeRelationDOExample example);

    ProductChargeRelationDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProductChargeRelationDO record, @Param("example") ProductChargeRelationDOExample example);

    int updateByExample(@Param("record") ProductChargeRelationDO record, @Param("example") ProductChargeRelationDOExample example);

    int updateByPrimaryKeySelective(ProductChargeRelationDO record);

    int updateByPrimaryKey(ProductChargeRelationDO record);
}