package com.zty.kdd.DO.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChargeInfoDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ChargeInfoDOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andChargeTypeIsNull() {
            addCriterion("charge_type is null");
            return (Criteria) this;
        }

        public Criteria andChargeTypeIsNotNull() {
            addCriterion("charge_type is not null");
            return (Criteria) this;
        }

        public Criteria andChargeTypeEqualTo(Integer value) {
            addCriterion("charge_type =", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeNotEqualTo(Integer value) {
            addCriterion("charge_type <>", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeGreaterThan(Integer value) {
            addCriterion("charge_type >", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("charge_type >=", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeLessThan(Integer value) {
            addCriterion("charge_type <", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeLessThanOrEqualTo(Integer value) {
            addCriterion("charge_type <=", value, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeIn(List<Integer> values) {
            addCriterion("charge_type in", values, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeNotIn(List<Integer> values) {
            addCriterion("charge_type not in", values, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeBetween(Integer value1, Integer value2) {
            addCriterion("charge_type between", value1, value2, "chargeType");
            return (Criteria) this;
        }

        public Criteria andChargeTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("charge_type not between", value1, value2, "chargeType");
            return (Criteria) this;
        }

        public Criteria andRightsIsNull() {
            addCriterion("rights is null");
            return (Criteria) this;
        }

        public Criteria andRightsIsNotNull() {
            addCriterion("rights is not null");
            return (Criteria) this;
        }

        public Criteria andRightsEqualTo(Integer value) {
            addCriterion("rights =", value, "rights");
            return (Criteria) this;
        }

        public Criteria andRightsNotEqualTo(Integer value) {
            addCriterion("rights <>", value, "rights");
            return (Criteria) this;
        }

        public Criteria andRightsGreaterThan(Integer value) {
            addCriterion("rights >", value, "rights");
            return (Criteria) this;
        }

        public Criteria andRightsGreaterThanOrEqualTo(Integer value) {
            addCriterion("rights >=", value, "rights");
            return (Criteria) this;
        }

        public Criteria andRightsLessThan(Integer value) {
            addCriterion("rights <", value, "rights");
            return (Criteria) this;
        }

        public Criteria andRightsLessThanOrEqualTo(Integer value) {
            addCriterion("rights <=", value, "rights");
            return (Criteria) this;
        }

        public Criteria andRightsIn(List<Integer> values) {
            addCriterion("rights in", values, "rights");
            return (Criteria) this;
        }

        public Criteria andRightsNotIn(List<Integer> values) {
            addCriterion("rights not in", values, "rights");
            return (Criteria) this;
        }

        public Criteria andRightsBetween(Integer value1, Integer value2) {
            addCriterion("rights between", value1, value2, "rights");
            return (Criteria) this;
        }

        public Criteria andRightsNotBetween(Integer value1, Integer value2) {
            addCriterion("rights not between", value1, value2, "rights");
            return (Criteria) this;
        }

        public Criteria andChargeCurrIsNull() {
            addCriterion("charge_curr is null");
            return (Criteria) this;
        }

        public Criteria andChargeCurrIsNotNull() {
            addCriterion("charge_curr is not null");
            return (Criteria) this;
        }

        public Criteria andChargeCurrEqualTo(String value) {
            addCriterion("charge_curr =", value, "chargeCurr");
            return (Criteria) this;
        }

        public Criteria andChargeCurrNotEqualTo(String value) {
            addCriterion("charge_curr <>", value, "chargeCurr");
            return (Criteria) this;
        }

        public Criteria andChargeCurrGreaterThan(String value) {
            addCriterion("charge_curr >", value, "chargeCurr");
            return (Criteria) this;
        }

        public Criteria andChargeCurrGreaterThanOrEqualTo(String value) {
            addCriterion("charge_curr >=", value, "chargeCurr");
            return (Criteria) this;
        }

        public Criteria andChargeCurrLessThan(String value) {
            addCriterion("charge_curr <", value, "chargeCurr");
            return (Criteria) this;
        }

        public Criteria andChargeCurrLessThanOrEqualTo(String value) {
            addCriterion("charge_curr <=", value, "chargeCurr");
            return (Criteria) this;
        }

        public Criteria andChargeCurrLike(String value) {
            addCriterion("charge_curr like", value, "chargeCurr");
            return (Criteria) this;
        }

        public Criteria andChargeCurrNotLike(String value) {
            addCriterion("charge_curr not like", value, "chargeCurr");
            return (Criteria) this;
        }

        public Criteria andChargeCurrIn(List<String> values) {
            addCriterion("charge_curr in", values, "chargeCurr");
            return (Criteria) this;
        }

        public Criteria andChargeCurrNotIn(List<String> values) {
            addCriterion("charge_curr not in", values, "chargeCurr");
            return (Criteria) this;
        }

        public Criteria andChargeCurrBetween(String value1, String value2) {
            addCriterion("charge_curr between", value1, value2, "chargeCurr");
            return (Criteria) this;
        }

        public Criteria andChargeCurrNotBetween(String value1, String value2) {
            addCriterion("charge_curr not between", value1, value2, "chargeCurr");
            return (Criteria) this;
        }

        public Criteria andChargePriceIsNull() {
            addCriterion("charge_price is null");
            return (Criteria) this;
        }

        public Criteria andChargePriceIsNotNull() {
            addCriterion("charge_price is not null");
            return (Criteria) this;
        }

        public Criteria andChargePriceEqualTo(Long value) {
            addCriterion("charge_price =", value, "chargePrice");
            return (Criteria) this;
        }

        public Criteria andChargePriceNotEqualTo(Long value) {
            addCriterion("charge_price <>", value, "chargePrice");
            return (Criteria) this;
        }

        public Criteria andChargePriceGreaterThan(Long value) {
            addCriterion("charge_price >", value, "chargePrice");
            return (Criteria) this;
        }

        public Criteria andChargePriceGreaterThanOrEqualTo(Long value) {
            addCriterion("charge_price >=", value, "chargePrice");
            return (Criteria) this;
        }

        public Criteria andChargePriceLessThan(Long value) {
            addCriterion("charge_price <", value, "chargePrice");
            return (Criteria) this;
        }

        public Criteria andChargePriceLessThanOrEqualTo(Long value) {
            addCriterion("charge_price <=", value, "chargePrice");
            return (Criteria) this;
        }

        public Criteria andChargePriceIn(List<Long> values) {
            addCriterion("charge_price in", values, "chargePrice");
            return (Criteria) this;
        }

        public Criteria andChargePriceNotIn(List<Long> values) {
            addCriterion("charge_price not in", values, "chargePrice");
            return (Criteria) this;
        }

        public Criteria andChargePriceBetween(Long value1, Long value2) {
            addCriterion("charge_price between", value1, value2, "chargePrice");
            return (Criteria) this;
        }

        public Criteria andChargePriceNotBetween(Long value1, Long value2) {
            addCriterion("charge_price not between", value1, value2, "chargePrice");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(Long value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(Long value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(Long value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(Long value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(Long value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<Long> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<Long> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(Long value1, Long value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(Long value1, Long value2) {
            addCriterion("amount not between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDisabledIsNull() {
            addCriterion("disabled is null");
            return (Criteria) this;
        }

        public Criteria andDisabledIsNotNull() {
            addCriterion("disabled is not null");
            return (Criteria) this;
        }

        public Criteria andDisabledEqualTo(Byte value) {
            addCriterion("disabled =", value, "disabled");
            return (Criteria) this;
        }

        public Criteria andDisabledNotEqualTo(Byte value) {
            addCriterion("disabled <>", value, "disabled");
            return (Criteria) this;
        }

        public Criteria andDisabledGreaterThan(Byte value) {
            addCriterion("disabled >", value, "disabled");
            return (Criteria) this;
        }

        public Criteria andDisabledGreaterThanOrEqualTo(Byte value) {
            addCriterion("disabled >=", value, "disabled");
            return (Criteria) this;
        }

        public Criteria andDisabledLessThan(Byte value) {
            addCriterion("disabled <", value, "disabled");
            return (Criteria) this;
        }

        public Criteria andDisabledLessThanOrEqualTo(Byte value) {
            addCriterion("disabled <=", value, "disabled");
            return (Criteria) this;
        }

        public Criteria andDisabledIn(List<Byte> values) {
            addCriterion("disabled in", values, "disabled");
            return (Criteria) this;
        }

        public Criteria andDisabledNotIn(List<Byte> values) {
            addCriterion("disabled not in", values, "disabled");
            return (Criteria) this;
        }

        public Criteria andDisabledBetween(Byte value1, Byte value2) {
            addCriterion("disabled between", value1, value2, "disabled");
            return (Criteria) this;
        }

        public Criteria andDisabledNotBetween(Byte value1, Byte value2) {
            addCriterion("disabled not between", value1, value2, "disabled");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNull() {
            addCriterion("create_by is null");
            return (Criteria) this;
        }

        public Criteria andCreateByIsNotNull() {
            addCriterion("create_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreateByEqualTo(Integer value) {
            addCriterion("create_by =", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotEqualTo(Integer value) {
            addCriterion("create_by <>", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThan(Integer value) {
            addCriterion("create_by >", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_by >=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThan(Integer value) {
            addCriterion("create_by <", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByLessThanOrEqualTo(Integer value) {
            addCriterion("create_by <=", value, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByIn(List<Integer> values) {
            addCriterion("create_by in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotIn(List<Integer> values) {
            addCriterion("create_by not in", values, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByBetween(Integer value1, Integer value2) {
            addCriterion("create_by between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateByNotBetween(Integer value1, Integer value2) {
            addCriterion("create_by not between", value1, value2, "createBy");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andFldN1IsNull() {
            addCriterion("fld_n1 is null");
            return (Criteria) this;
        }

        public Criteria andFldN1IsNotNull() {
            addCriterion("fld_n1 is not null");
            return (Criteria) this;
        }

        public Criteria andFldN1EqualTo(Integer value) {
            addCriterion("fld_n1 =", value, "fldN1");
            return (Criteria) this;
        }

        public Criteria andFldN1NotEqualTo(Integer value) {
            addCriterion("fld_n1 <>", value, "fldN1");
            return (Criteria) this;
        }

        public Criteria andFldN1GreaterThan(Integer value) {
            addCriterion("fld_n1 >", value, "fldN1");
            return (Criteria) this;
        }

        public Criteria andFldN1GreaterThanOrEqualTo(Integer value) {
            addCriterion("fld_n1 >=", value, "fldN1");
            return (Criteria) this;
        }

        public Criteria andFldN1LessThan(Integer value) {
            addCriterion("fld_n1 <", value, "fldN1");
            return (Criteria) this;
        }

        public Criteria andFldN1LessThanOrEqualTo(Integer value) {
            addCriterion("fld_n1 <=", value, "fldN1");
            return (Criteria) this;
        }

        public Criteria andFldN1In(List<Integer> values) {
            addCriterion("fld_n1 in", values, "fldN1");
            return (Criteria) this;
        }

        public Criteria andFldN1NotIn(List<Integer> values) {
            addCriterion("fld_n1 not in", values, "fldN1");
            return (Criteria) this;
        }

        public Criteria andFldN1Between(Integer value1, Integer value2) {
            addCriterion("fld_n1 between", value1, value2, "fldN1");
            return (Criteria) this;
        }

        public Criteria andFldN1NotBetween(Integer value1, Integer value2) {
            addCriterion("fld_n1 not between", value1, value2, "fldN1");
            return (Criteria) this;
        }

        public Criteria andFldN2IsNull() {
            addCriterion("fld_n2 is null");
            return (Criteria) this;
        }

        public Criteria andFldN2IsNotNull() {
            addCriterion("fld_n2 is not null");
            return (Criteria) this;
        }

        public Criteria andFldN2EqualTo(Integer value) {
            addCriterion("fld_n2 =", value, "fldN2");
            return (Criteria) this;
        }

        public Criteria andFldN2NotEqualTo(Integer value) {
            addCriterion("fld_n2 <>", value, "fldN2");
            return (Criteria) this;
        }

        public Criteria andFldN2GreaterThan(Integer value) {
            addCriterion("fld_n2 >", value, "fldN2");
            return (Criteria) this;
        }

        public Criteria andFldN2GreaterThanOrEqualTo(Integer value) {
            addCriterion("fld_n2 >=", value, "fldN2");
            return (Criteria) this;
        }

        public Criteria andFldN2LessThan(Integer value) {
            addCriterion("fld_n2 <", value, "fldN2");
            return (Criteria) this;
        }

        public Criteria andFldN2LessThanOrEqualTo(Integer value) {
            addCriterion("fld_n2 <=", value, "fldN2");
            return (Criteria) this;
        }

        public Criteria andFldN2In(List<Integer> values) {
            addCriterion("fld_n2 in", values, "fldN2");
            return (Criteria) this;
        }

        public Criteria andFldN2NotIn(List<Integer> values) {
            addCriterion("fld_n2 not in", values, "fldN2");
            return (Criteria) this;
        }

        public Criteria andFldN2Between(Integer value1, Integer value2) {
            addCriterion("fld_n2 between", value1, value2, "fldN2");
            return (Criteria) this;
        }

        public Criteria andFldN2NotBetween(Integer value1, Integer value2) {
            addCriterion("fld_n2 not between", value1, value2, "fldN2");
            return (Criteria) this;
        }

        public Criteria andFldL1IsNull() {
            addCriterion("fld_l1 is null");
            return (Criteria) this;
        }

        public Criteria andFldL1IsNotNull() {
            addCriterion("fld_l1 is not null");
            return (Criteria) this;
        }

        public Criteria andFldL1EqualTo(Long value) {
            addCriterion("fld_l1 =", value, "fldL1");
            return (Criteria) this;
        }

        public Criteria andFldL1NotEqualTo(Long value) {
            addCriterion("fld_l1 <>", value, "fldL1");
            return (Criteria) this;
        }

        public Criteria andFldL1GreaterThan(Long value) {
            addCriterion("fld_l1 >", value, "fldL1");
            return (Criteria) this;
        }

        public Criteria andFldL1GreaterThanOrEqualTo(Long value) {
            addCriterion("fld_l1 >=", value, "fldL1");
            return (Criteria) this;
        }

        public Criteria andFldL1LessThan(Long value) {
            addCriterion("fld_l1 <", value, "fldL1");
            return (Criteria) this;
        }

        public Criteria andFldL1LessThanOrEqualTo(Long value) {
            addCriterion("fld_l1 <=", value, "fldL1");
            return (Criteria) this;
        }

        public Criteria andFldL1In(List<Long> values) {
            addCriterion("fld_l1 in", values, "fldL1");
            return (Criteria) this;
        }

        public Criteria andFldL1NotIn(List<Long> values) {
            addCriterion("fld_l1 not in", values, "fldL1");
            return (Criteria) this;
        }

        public Criteria andFldL1Between(Long value1, Long value2) {
            addCriterion("fld_l1 between", value1, value2, "fldL1");
            return (Criteria) this;
        }

        public Criteria andFldL1NotBetween(Long value1, Long value2) {
            addCriterion("fld_l1 not between", value1, value2, "fldL1");
            return (Criteria) this;
        }

        public Criteria andFldS1IsNull() {
            addCriterion("fld_s1 is null");
            return (Criteria) this;
        }

        public Criteria andFldS1IsNotNull() {
            addCriterion("fld_s1 is not null");
            return (Criteria) this;
        }

        public Criteria andFldS1EqualTo(String value) {
            addCriterion("fld_s1 =", value, "fldS1");
            return (Criteria) this;
        }

        public Criteria andFldS1NotEqualTo(String value) {
            addCriterion("fld_s1 <>", value, "fldS1");
            return (Criteria) this;
        }

        public Criteria andFldS1GreaterThan(String value) {
            addCriterion("fld_s1 >", value, "fldS1");
            return (Criteria) this;
        }

        public Criteria andFldS1GreaterThanOrEqualTo(String value) {
            addCriterion("fld_s1 >=", value, "fldS1");
            return (Criteria) this;
        }

        public Criteria andFldS1LessThan(String value) {
            addCriterion("fld_s1 <", value, "fldS1");
            return (Criteria) this;
        }

        public Criteria andFldS1LessThanOrEqualTo(String value) {
            addCriterion("fld_s1 <=", value, "fldS1");
            return (Criteria) this;
        }

        public Criteria andFldS1Like(String value) {
            addCriterion("fld_s1 like", value, "fldS1");
            return (Criteria) this;
        }

        public Criteria andFldS1NotLike(String value) {
            addCriterion("fld_s1 not like", value, "fldS1");
            return (Criteria) this;
        }

        public Criteria andFldS1In(List<String> values) {
            addCriterion("fld_s1 in", values, "fldS1");
            return (Criteria) this;
        }

        public Criteria andFldS1NotIn(List<String> values) {
            addCriterion("fld_s1 not in", values, "fldS1");
            return (Criteria) this;
        }

        public Criteria andFldS1Between(String value1, String value2) {
            addCriterion("fld_s1 between", value1, value2, "fldS1");
            return (Criteria) this;
        }

        public Criteria andFldS1NotBetween(String value1, String value2) {
            addCriterion("fld_s1 not between", value1, value2, "fldS1");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}