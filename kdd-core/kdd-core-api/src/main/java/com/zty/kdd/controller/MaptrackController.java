package com.zty.kdd.controller;

import com.zty.framework.exception.BusinessException;
import com.zty.framework.exception.NetworkException;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.DO.AccountBalanceDO;
import com.zty.kdd.response.MaptrackQueryResponse;
import com.zty.kdd.service.BalanceService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zty.framework.dto.ResultDTO;
import com.zty.kdd.annotation.CheckSign;
import com.zty.kdd.request.MaptrackQueryRequest;
import com.zty.kdd.api.MaptrackApi;

/**
 * @author tianyi
 * @date 2021-02-21 15:46
 */
@RestController
@RequestMapping("/maptrack")
public class MaptrackController {

    private static final Logger log = LoggerFactory.getLogger(MaptrackController.class);

    @Autowired
    private MaptrackApi maptrackApi;

    @Autowired
    private BalanceService balanceService;

    /**
     * 余额处理，并完成业务
     * @param reqAO
     * @return
     */
    @CheckSign
    @RequestMapping(value =  "/query", method = {RequestMethod.POST ,RequestMethod.GET })
    public ResultDTO query(MaptrackQueryRequest reqAO) {
        AccountBalanceDO accountBalanceDO = new AccountBalanceDO().accountId(reqAO.getCurrentUID());
        try {
            // 检查并预扣余额
            balanceService.checkAndFrozen(accountBalanceDO);
            MaptrackQueryResponse successResp = null;
            try {
                successResp = maptrackApi.singleQuery(reqAO);
            } catch (Exception e) {
                // 解冻余额
                balanceService.checkAndUnfrozen(accountBalanceDO);
                throw e;
            }
            // 实扣余额
            balanceService.checkAndCut(accountBalanceDO);
            return ResultDTO.success(successResp);
        } catch (ParamCheckException e) {
            log.warn("MaptrackQuery 参数检查异常, ", e);
            if (StringUtils.isNotBlank(e.getKeyName())) {
                return ResultDTO.error(403, e.getErrorMsg(), e);
            } else {
                return ResultDTO.error(403, e.getErrorMsg(), reqAO);
            }
        } catch (BusinessException e) {
            log.error("MaptrackQuery 业务处理异常, ", e);
            return ResultDTO.error(500, e.getErrorMsg(), reqAO);
        } catch (NetworkException e) {
            log.error("MaptrackQuery 网络通信异常, ", e);
            return ResultDTO.error(504, e.getErrorMsg(), reqAO);
        } catch (Exception e) {
            log.error("MaptrackQuery 未知异常, ", e);
            return ResultDTO.error(500, e.getMessage(), reqAO);
        }
    }

}
