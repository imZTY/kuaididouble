package com.zty.kdd.kdniao.service.impl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.zty.framework.exception.BusinessException;
import com.zty.framework.exception.CommonException;
import com.zty.framework.exception.ParamCheckException;
import com.zty.kdd.kdniao.factory.impl.KdniaoMaptrackQueryRequestFactory;
import com.zty.kdd.kdniao.request.KdniaoMaptrackQueryRequest;
import com.zty.kdd.kdniao.response.KdniaoMaptrackQueryResponse;
import com.zty.kdd.kdniao.util.KdniaoApiUtil;
import com.zty.kdd.third.dto.QueryParamDTO;
import com.zty.kdd.third.enums.ThirdTransStatusEnum;
import com.zty.kdd.third.request.ThirdMaptrackQueryRequest;
import com.zty.kdd.third.response.ThirdMaptrackQueryResponse;
import com.zty.kdd.third.service.AbstractMaptrackQuerySDKService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author: tianyi.zeng
 * @create: 15/9/2021 - 下午 3:31
 */
@Service("Kdniao_service")
public class KdniaoServiceImpl extends AbstractMaptrackQuerySDKService<KdniaoMaptrackQueryRequestFactory, KdniaoMaptrackQueryResponse> {

    private final static Logger log = LoggerFactory.getLogger(KdniaoServiceImpl.class);

    //用户ID，快递鸟提供，注意保管，不要泄漏
    private static final String EBusinessID = "1702726";//即用户ID，登录快递鸟官网会员中心获取 https://www.kdniao.com/UserCenter/v4/UserHome.aspx
    //API key，快递鸟提供，注意保管，不要泄漏
    private static final String ApiKey = "ce70fd04-8862-403f-82c6-f06f72ea6f4f";//即API key，登录快递鸟官网会员中心获取 https://www.kdniao.com/UserCenter/v4/UserHome.aspx

    public KdniaoServiceImpl(KdniaoMaptrackQueryRequestFactory thirdQueryDTOFactory) {
        super(thirdQueryDTOFactory);
    }

    /**
     * 获取接口url，如果走SDK则不需要
     *
     * @return
     */
    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Map<String, Object> getHeaders(QueryParamDTO thirdQueryDTO) {
        return null;
    }

    /**
     * 获取请求body
     * 转换时，注意Object后续的用法
     *
     * @param thirdMaptrackQueryRequest
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    public Map<String, Object> getReqBody(ThirdMaptrackQueryRequest thirdMaptrackQueryRequest) throws UnsupportedEncodingException {
        return null;
    }

    @Override
    public ThirdMaptrackQueryResponse query(Map<String, Object> headers, ThirdMaptrackQueryRequest thirdMaptrackQueryRequest) {
        if (!(thirdMaptrackQueryRequest instanceof KdniaoMaptrackQueryRequest)) {
            log.warn("入参不是KdniaoMaptrackQueryRequest:{}", thirdMaptrackQueryRequest.getClass().getName());
            throw new ParamCheckException("third层异常，入参不是KdniaoMaptrackQueryRequest");
        }
        KdniaoMaptrackQueryRequest request = (KdniaoMaptrackQueryRequest) thirdMaptrackQueryRequest;
        request.setEBusinessID(EBusinessID);
        request.setRequestType("8001");
        String dataSign = null;
        try {
            dataSign = encrypt(request.getRequestData(), ApiKey, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            log.error("快递鸟查询 生成签名异常，", e);
            throw new BusinessException("KdniaoServiceImpl", " 生成签名异常" + e.getMessage());
        }
        request.setDataSign(dataSign);

        KdniaoMaptrackQueryResponse response = null;
        try {
            // 内含出参入参日志打印
            response = KdniaoApiUtil.mapTrackQuery(request);
        } catch (CommonException e) {
            // 已知异常 继续抛出
            throw e;
        } catch (Exception e) {
            log.error("快递鸟查询 未知异常，", e);
            throw new BusinessException("KdniaoServiceImpl", " 未知异常" + e.getMessage());
        }
        // 通信结果
        response.setCommuniecationResult(this.parseCommunicateResult(response, JSON.toJSONString(response)));
        if (response.isCommunicateSuccess()) {
            // 通信成功才判断业务结果
            response.setBusinessResult(this.parseBusinessResult(response));
        }
        return response;
    }

    /**
     * 电商Sign签名生成
     * content 内容
     * keyValue ApiKey
     * charset 编码方式
     * @throws NoSuchAlgorithmException
     * @return DataSign签名
     */
    private  String encrypt(String content, String keyValue, Charset charset) throws NoSuchAlgorithmException
    {
        if (keyValue != null)
        {
            return Base64.encode(MD5(content + keyValue, charset).getBytes(charset));
        }
        return Base64.encode(MD5(content, charset).getBytes(charset));
    }

    /**
     * MD5加密
     * str 内容
     * charset 编码方式
     * @throws Exception
     */
    private String MD5(String str, Charset charset) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes(charset));
        byte[] result = md.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int i = 0; i < result.length; i++) {
            int val = result[i] & 0xff;
            if (val <= 0xf) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 将响应报文里的code转换成内部标准的状态枚举
     *
     * @param state
     * @return
     */
    @Override
    public ThirdTransStatusEnum parseThirdCodeToEnum(String state) {
        if (null == state) {
            return ThirdTransStatusEnum.NOT_EXIST;
        }
        ThirdTransStatusEnum statusEnum = ThirdTransStatusEnum.UNKNOW;
        switch (state) {
            case "0":  //暂无轨迹信息
                statusEnum = ThirdTransStatusEnum.NOT_EXIST;
                break;
            case "收件":
            case "1":  //已揽收
                statusEnum = ThirdTransStatusEnum.COLLECT;
                break;
            case "发件":
            case "到件":
            case "转件":
            case "2":  //在途中
            case "201":  //到达派件城市
            case "211":  //已放入快递柜或驿站
                statusEnum = ThirdTransStatusEnum.ON_THE_WAY;
                break;
            case "派件":
            case "202":  //派件中
                statusEnum = ThirdTransStatusEnum.DISTRIBUTE;
                break;
            case "拒签":
            case "404": //拒收(退件)
                statusEnum = ThirdTransStatusEnum.REJECT;
                break;
            case "问题件":
            case "4":
            case "401": //发货无信息
            case "402": //超时未签收
            case "403": //超时未更新
            case "405": //派件异常
            case "412": //快递柜或驿站超时未取
                statusEnum = ThirdTransStatusEnum.PUZZLE;
                break;
            case "转投":
                statusEnum = ThirdTransStatusEnum.SWITCHING;
                break;
            case "签收":
            case "301": //正常签收
            case "302": //派件异常后最终签收
            case "304": //代收签收
            case "311": //快递柜或驿站签收
                statusEnum = ThirdTransStatusEnum.RECEIVED;
                break;
            case "退件":
            case "407": //退货未签收
                statusEnum = ThirdTransStatusEnum.REJECT_ON_WAY;
                break;
            case "退件完成":
            case "406": //退货签收
                statusEnum = ThirdTransStatusEnum.REJECT_FINISH;
                break;
            default: ;break;
        }
        return statusEnum;
    }

    /**
     * 解析并获取第三方通信结果
     *
     * @param response
     * @param responseStr
     */
    @Override
    protected ThirdMaptrackQueryResponse.CommunicateResult parseCommunicateResult(KdniaoMaptrackQueryResponse response, String responseStr) {
        // 无异常 默认响应成功
        return new ThirdMaptrackQueryResponse.CommunicateResult((byte) 0, System.currentTimeMillis()+"", responseStr);
    }

    /**
     * 解析并获取第三方业务结果
     *
     * @param response
     */
    @Override
    protected ThirdMaptrackQueryResponse.BusinessResult parseBusinessResult(KdniaoMaptrackQueryResponse response) {
        log.debug("快递鸟查询 开始检查是否业务成功 {}", response.isSuccess());
        if (response.isSuccess()) {
            // 业务成功
            ThirdMaptrackQueryResponse.BusinessResult businessResult = new ThirdMaptrackQueryResponse.BusinessResult(true, null, "");
            // 判断是否是空数据
            if (response.getTraces() == null
                    || CollectionUtils.isEmpty(response.getTraces())) {
                log.warn("快递鸟查询 无轨迹结果");
                return businessResult;
            }
            // 轨迹数据逆序排序
            response.sortByTimeDesc();
            // 【重点1】设置物流当前的运输状态
            businessResult.setThirdStateCode(response.getStateEx());
            // 【重点2】设置物流轨迹历史(时间倒序)
            businessResult.setThirdTrackDataList(response.getTraces()
                    .stream()
                    .map(this::parseThirdTrackData)
                    .collect(Collectors.toList()));
            return businessResult;
        } else {
            // 业务失败
            log.warn("快递鸟查询, 业务失败, {}, {}", response, response.getReason());
            return new ThirdMaptrackQueryResponse.BusinessResult(false, "fail", response.getReason());
        }
    }

    private ThirdMaptrackQueryResponse.ThirdTrackDataDTO parseThirdTrackData(KdniaoMaptrackQueryResponse.Trace trace) {
        ThirdMaptrackQueryResponse.ThirdTrackDataDTO thirdTrackDataDTO = new ThirdMaptrackQueryResponse.ThirdTrackDataDTO();
        thirdTrackDataDTO.setContext(trace.getAction() + "/" + trace.getAcceptStation());
        thirdTrackDataDTO.setTime(trace.getAcceptTime());
        thirdTrackDataDTO.setFtime(trace.getLongAcceptTime());
        return thirdTrackDataDTO;
    }
}
