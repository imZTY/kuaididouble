package com.zty.kdd.DO.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransQueryLogDOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TransQueryLogDOExample() {
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

        public Criteria andAccountIdIsNull() {
            addCriterion("account_id is null");
            return (Criteria) this;
        }

        public Criteria andAccountIdIsNotNull() {
            addCriterion("account_id is not null");
            return (Criteria) this;
        }

        public Criteria andAccountIdEqualTo(Integer value) {
            addCriterion("account_id =", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotEqualTo(Integer value) {
            addCriterion("account_id <>", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThan(Integer value) {
            addCriterion("account_id >", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("account_id >=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThan(Integer value) {
            addCriterion("account_id <", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("account_id <=", value, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdIn(List<Integer> values) {
            addCriterion("account_id in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotIn(List<Integer> values) {
            addCriterion("account_id not in", values, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("account_id between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("account_id not between", value1, value2, "accountId");
            return (Criteria) this;
        }

        public Criteria andThirdApiCompanyIsNull() {
            addCriterion("third_api_company is null");
            return (Criteria) this;
        }

        public Criteria andThirdApiCompanyIsNotNull() {
            addCriterion("third_api_company is not null");
            return (Criteria) this;
        }

        public Criteria andThirdApiCompanyEqualTo(Byte value) {
            addCriterion("third_api_company =", value, "thirdApiCompany");
            return (Criteria) this;
        }

        public Criteria andThirdApiCompanyNotEqualTo(Byte value) {
            addCriterion("third_api_company <>", value, "thirdApiCompany");
            return (Criteria) this;
        }

        public Criteria andThirdApiCompanyGreaterThan(Byte value) {
            addCriterion("third_api_company >", value, "thirdApiCompany");
            return (Criteria) this;
        }

        public Criteria andThirdApiCompanyGreaterThanOrEqualTo(Byte value) {
            addCriterion("third_api_company >=", value, "thirdApiCompany");
            return (Criteria) this;
        }

        public Criteria andThirdApiCompanyLessThan(Byte value) {
            addCriterion("third_api_company <", value, "thirdApiCompany");
            return (Criteria) this;
        }

        public Criteria andThirdApiCompanyLessThanOrEqualTo(Byte value) {
            addCriterion("third_api_company <=", value, "thirdApiCompany");
            return (Criteria) this;
        }

        public Criteria andThirdApiCompanyIn(List<Byte> values) {
            addCriterion("third_api_company in", values, "thirdApiCompany");
            return (Criteria) this;
        }

        public Criteria andThirdApiCompanyNotIn(List<Byte> values) {
            addCriterion("third_api_company not in", values, "thirdApiCompany");
            return (Criteria) this;
        }

        public Criteria andThirdApiCompanyBetween(Byte value1, Byte value2) {
            addCriterion("third_api_company between", value1, value2, "thirdApiCompany");
            return (Criteria) this;
        }

        public Criteria andThirdApiCompanyNotBetween(Byte value1, Byte value2) {
            addCriterion("third_api_company not between", value1, value2, "thirdApiCompany");
            return (Criteria) this;
        }

        public Criteria andRequestMsgIsNull() {
            addCriterion("request_msg is null");
            return (Criteria) this;
        }

        public Criteria andRequestMsgIsNotNull() {
            addCriterion("request_msg is not null");
            return (Criteria) this;
        }

        public Criteria andRequestMsgEqualTo(String value) {
            addCriterion("request_msg =", value, "requestMsg");
            return (Criteria) this;
        }

        public Criteria andRequestMsgNotEqualTo(String value) {
            addCriterion("request_msg <>", value, "requestMsg");
            return (Criteria) this;
        }

        public Criteria andRequestMsgGreaterThan(String value) {
            addCriterion("request_msg >", value, "requestMsg");
            return (Criteria) this;
        }

        public Criteria andRequestMsgGreaterThanOrEqualTo(String value) {
            addCriterion("request_msg >=", value, "requestMsg");
            return (Criteria) this;
        }

        public Criteria andRequestMsgLessThan(String value) {
            addCriterion("request_msg <", value, "requestMsg");
            return (Criteria) this;
        }

        public Criteria andRequestMsgLessThanOrEqualTo(String value) {
            addCriterion("request_msg <=", value, "requestMsg");
            return (Criteria) this;
        }

        public Criteria andRequestMsgLike(String value) {
            addCriterion("request_msg like", value, "requestMsg");
            return (Criteria) this;
        }

        public Criteria andRequestMsgNotLike(String value) {
            addCriterion("request_msg not like", value, "requestMsg");
            return (Criteria) this;
        }

        public Criteria andRequestMsgIn(List<String> values) {
            addCriterion("request_msg in", values, "requestMsg");
            return (Criteria) this;
        }

        public Criteria andRequestMsgNotIn(List<String> values) {
            addCriterion("request_msg not in", values, "requestMsg");
            return (Criteria) this;
        }

        public Criteria andRequestMsgBetween(String value1, String value2) {
            addCriterion("request_msg between", value1, value2, "requestMsg");
            return (Criteria) this;
        }

        public Criteria andRequestMsgNotBetween(String value1, String value2) {
            addCriterion("request_msg not between", value1, value2, "requestMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgIsNull() {
            addCriterion("response_msg is null");
            return (Criteria) this;
        }

        public Criteria andResponseMsgIsNotNull() {
            addCriterion("response_msg is not null");
            return (Criteria) this;
        }

        public Criteria andResponseMsgEqualTo(String value) {
            addCriterion("response_msg =", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgNotEqualTo(String value) {
            addCriterion("response_msg <>", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgGreaterThan(String value) {
            addCriterion("response_msg >", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgGreaterThanOrEqualTo(String value) {
            addCriterion("response_msg >=", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgLessThan(String value) {
            addCriterion("response_msg <", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgLessThanOrEqualTo(String value) {
            addCriterion("response_msg <=", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgLike(String value) {
            addCriterion("response_msg like", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgNotLike(String value) {
            addCriterion("response_msg not like", value, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgIn(List<String> values) {
            addCriterion("response_msg in", values, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgNotIn(List<String> values) {
            addCriterion("response_msg not in", values, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgBetween(String value1, String value2) {
            addCriterion("response_msg between", value1, value2, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andResponseMsgNotBetween(String value1, String value2) {
            addCriterion("response_msg not between", value1, value2, "responseMsg");
            return (Criteria) this;
        }

        public Criteria andCostTimeIsNull() {
            addCriterion("cost_time is null");
            return (Criteria) this;
        }

        public Criteria andCostTimeIsNotNull() {
            addCriterion("cost_time is not null");
            return (Criteria) this;
        }

        public Criteria andCostTimeEqualTo(Long value) {
            addCriterion("cost_time =", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeNotEqualTo(Long value) {
            addCriterion("cost_time <>", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeGreaterThan(Long value) {
            addCriterion("cost_time >", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeGreaterThanOrEqualTo(Long value) {
            addCriterion("cost_time >=", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeLessThan(Long value) {
            addCriterion("cost_time <", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeLessThanOrEqualTo(Long value) {
            addCriterion("cost_time <=", value, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeIn(List<Long> values) {
            addCriterion("cost_time in", values, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeNotIn(List<Long> values) {
            addCriterion("cost_time not in", values, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeBetween(Long value1, Long value2) {
            addCriterion("cost_time between", value1, value2, "costTime");
            return (Criteria) this;
        }

        public Criteria andCostTimeNotBetween(Long value1, Long value2) {
            addCriterion("cost_time not between", value1, value2, "costTime");
            return (Criteria) this;
        }

        public Criteria andIsErrorIsNull() {
            addCriterion("is_error is null");
            return (Criteria) this;
        }

        public Criteria andIsErrorIsNotNull() {
            addCriterion("is_error is not null");
            return (Criteria) this;
        }

        public Criteria andIsErrorEqualTo(Byte value) {
            addCriterion("is_error =", value, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorNotEqualTo(Byte value) {
            addCriterion("is_error <>", value, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorGreaterThan(Byte value) {
            addCriterion("is_error >", value, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_error >=", value, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorLessThan(Byte value) {
            addCriterion("is_error <", value, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorLessThanOrEqualTo(Byte value) {
            addCriterion("is_error <=", value, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorIn(List<Byte> values) {
            addCriterion("is_error in", values, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorNotIn(List<Byte> values) {
            addCriterion("is_error not in", values, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorBetween(Byte value1, Byte value2) {
            addCriterion("is_error between", value1, value2, "isError");
            return (Criteria) this;
        }

        public Criteria andIsErrorNotBetween(Byte value1, Byte value2) {
            addCriterion("is_error not between", value1, value2, "isError");
            return (Criteria) this;
        }

        public Criteria andMessageIdIsNull() {
            addCriterion("message_id is null");
            return (Criteria) this;
        }

        public Criteria andMessageIdIsNotNull() {
            addCriterion("message_id is not null");
            return (Criteria) this;
        }

        public Criteria andMessageIdEqualTo(String value) {
            addCriterion("message_id =", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotEqualTo(String value) {
            addCriterion("message_id <>", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdGreaterThan(String value) {
            addCriterion("message_id >", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdGreaterThanOrEqualTo(String value) {
            addCriterion("message_id >=", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdLessThan(String value) {
            addCriterion("message_id <", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdLessThanOrEqualTo(String value) {
            addCriterion("message_id <=", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdLike(String value) {
            addCriterion("message_id like", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotLike(String value) {
            addCriterion("message_id not like", value, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdIn(List<String> values) {
            addCriterion("message_id in", values, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotIn(List<String> values) {
            addCriterion("message_id not in", values, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdBetween(String value1, String value2) {
            addCriterion("message_id between", value1, value2, "messageId");
            return (Criteria) this;
        }

        public Criteria andMessageIdNotBetween(String value1, String value2) {
            addCriterion("message_id not between", value1, value2, "messageId");
            return (Criteria) this;
        }

        public Criteria andRequestIpIsNull() {
            addCriterion("request_ip is null");
            return (Criteria) this;
        }

        public Criteria andRequestIpIsNotNull() {
            addCriterion("request_ip is not null");
            return (Criteria) this;
        }

        public Criteria andRequestIpEqualTo(String value) {
            addCriterion("request_ip =", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpNotEqualTo(String value) {
            addCriterion("request_ip <>", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpGreaterThan(String value) {
            addCriterion("request_ip >", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpGreaterThanOrEqualTo(String value) {
            addCriterion("request_ip >=", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpLessThan(String value) {
            addCriterion("request_ip <", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpLessThanOrEqualTo(String value) {
            addCriterion("request_ip <=", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpLike(String value) {
            addCriterion("request_ip like", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpNotLike(String value) {
            addCriterion("request_ip not like", value, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpIn(List<String> values) {
            addCriterion("request_ip in", values, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpNotIn(List<String> values) {
            addCriterion("request_ip not in", values, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpBetween(String value1, String value2) {
            addCriterion("request_ip between", value1, value2, "requestIp");
            return (Criteria) this;
        }

        public Criteria andRequestIpNotBetween(String value1, String value2) {
            addCriterion("request_ip not between", value1, value2, "requestIp");
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