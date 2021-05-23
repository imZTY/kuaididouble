package com.zty.kdd.aop;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zty.bo.api.LoginCacheApi;
import com.zty.common.service.RoleService;
import com.zty.framework.dto.ResultDTO;
import com.zty.kdd.DO.SecretKeyInfoDO;
import com.zty.kdd.ao.BaseReqAO;
import com.zty.kdd.service.SecretKeyService;

/**
 * @author tianyi
 * @date 2020-05-03 15:49
 */
@Aspect
@Component
public class SignCheck {

    Logger log = LoggerFactory.getLogger(SignCheck.class);

    @Autowired
    private SecretKeyService secretKeyService;

    @Autowired
    private LoginCacheApi loginCacheApi;

    @Autowired
    private RoleService roleService;

    @Pointcut("@annotation(com.zty.kdd.annotation.CheckSign)")
    public void check() {

    }

    @Around("check()")
    public Object preHandle(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        //原参数
        Object[] originArgs = joinPoint.getArgs();
        // 要求第一个必须的签名对象
        BaseReqAO reqAO = (BaseReqAO)originArgs[0];


        if (!StringUtils.isBlank(reqAO.getSign())){
            // 获取请求参数，生成签名
            String customer = reqAO.getCustomer();
            String param = reqAO.getParam();
            // 获取秘钥Key
            SecretKeyInfoDO secretKeyInfoDO = new SecretKeyInfoDO();
            secretKeyInfoDO.setCustomerCode(customer);
            SecretKeyInfoDO keyResult = secretKeyService.getSecretByCustomerCode(secretKeyInfoDO);
            if (keyResult != null && StringUtils.isBlank(keyResult.getSecretKey())) {
                log.warn("客户号{}的秘钥不存在", customer);
                return ResultDTO.error(400,"秘钥key未启用");
            }
            String key = keyResult.getSecretKey();
            String sign = DigestUtils.md5Hex(param + key + customer).toUpperCase();
            // 比较签名是否一致
            if (sign.equals(reqAO.getSign())) {
                try {
                    reqAO.setParamObj(param);
                    originArgs[0] = reqAO;
                    return joinPoint.proceed(originArgs);
                } catch (Throwable throwable) {  // 若原方法执行出错，这里也可以截取到异常
                    // log.error("系统出错：", throwable);
                    log.error("RuntimeException ", throwable);
                    return ResultDTO.error(500,
                            StringUtils.isBlank(throwable.getMessage()) ? "系统异常" : throwable.getMessage());
                }
            } else {
                return ResultDTO.error(400,"签名错误");
            }
        } else {
            return ResultDTO.error(400,"签名为空");
        }
    }
}
