package com.zty.kdd.third.sf;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.sf.csim.express.service.CallExpressServiceTools;
import com.sf.csim.express.service.EspServiceCode;
import com.sf.csim.express.service.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zty.kdd.third.dto.ThirdQueryDTO;
import com.zty.kdd.third.enums.ThirdTransStatusEnum;
import com.zty.kdd.third.service.AbstractSDKService;

/**
 * @author tianyi
 * @date 2021-05-05 22:17
 */
@Service("Shunfeng_SDK")
public class ShunfengSDKServiceImpl extends AbstractSDKService {

    private static final Logger logger = LoggerFactory.getLogger(ShunfengSDKServiceImpl.class);

    //丰桥新沙箱测试顾客编码  Yg4Zf06w_sxZs3A5D
    //校验码                          3Xdk1jqeG1Xod9nUXus8Op7DNOkchTnw
    private static final String CLIENT_CODE = "KMKJPh";  //此处替换为您在丰桥平台获取的顾客编码
    private static final String CHECK_WORD = "DGJiz0VoHeEKnSAuK98sPIwqTQX6xupI";//此处替换为您在丰桥平台获取的校验码

    //沙箱环境的地址
    private static final String CALL_URL_BOX = "https://sfapi-sbox.sf-express.com/std/service";
    //生产环境的地址
    private static final String CALL_URL_PROD = "https://sfapi.sf-express.com/std/service";

    /**
     * 文档：https://qiao.sf-express.com/pages/developDoc/index.html?level2=324185
     * @param headers
     * @param params 顺丰请求参数{
     * 	"language": "0",   //错误信息语语言
     * 	"trackingType": "1",  //1=运单号查询，2=订单号查询
     * 	"trackingNumber": ["444003077898", "441003077850"],
     * 	"methodType": "1"  //1=标准路由查询，2=定制路由查询
     * }
     * @return 注意，示例里的JSONObject实际全是jo字符串{
     *     "apiErrorMsg": "",
     *     "apiResponseID": "0001793CFE595E3FEB9425417E3E3F3F",
     *     "apiResultCode": "A1000",
     *     "apiResultData": {
     *         "success": true,
     *         "errorCode": "S0000",
     *         "errorMsg": null,
     *         "msgData": {"routeResps": [
     *             {
     *                 "mailNo": "444003077898",
     *                 "routes": [{
     *                     "acceptAddress": "深圳市",
     *                     "acceptTime": "2021-05-05 22:46:12",
     *                     "remark": "顺丰速运 已收取快件",
     *                     "opCode": "50"
     *                 }]
     *             },
     *             {
     *                 "mailNo": "441003077850",
     *                 "routes": [{
     *                     "acceptAddress": "深圳市",
     *                     "acceptTime": "2021-05-05 22:46:12",
     *                     "remark": "顺丰速运 已收取快件",
     *                     "opCode": "50"
     *                 }]
     *             }
     *         ]}
     *     }
     * }
     */
    @Override
    public String query(Map<String, Object> headers, Map<String, Object> params) {
        Map<String, String> reqBody;
        try {
            reqBody = prepareReqBody(params);
            logger.info("顺丰查询，请求：{}", reqBody);
            String resp = HttpClientUtil.post(CALL_URL_PROD, reqBody);
            logger.info("顺丰查询，响应：{}", resp);
            return resp;
        } catch (UnsupportedEncodingException e) {
            logger.error("顺丰物流查询，生成请求报文时加密出错", e);
            throw new RuntimeException("顺丰物流查询，生成请求报文时加密出错" + e.getMessage());
        }
    }

    private Map<String, String> prepareReqBody(Map<String, Object> params) throws UnsupportedEncodingException {
        Map<String, String> reqBody = new HashMap<String, String>();

        String timeStamp = String.valueOf(System.currentTimeMillis());
        String msgData = JSONObject.toJSONString(params);

        reqBody.put("partnerID", CLIENT_CODE);  // 顾客编码 ，对应丰桥上获取的clientCode
        reqBody.put("requestID", UUID.randomUUID().toString().replace("-", ""));
        reqBody.put("serviceCode", EspServiceCode.EXP_RECE_SEARCH_ROUTES.getCode());// 接口服务码
        reqBody.put("timestamp", timeStamp);
        reqBody.put("msgData", msgData);
        reqBody.put("msgDigest", CallExpressServiceTools.getMsgDigest(msgData,timeStamp,CHECK_WORD));

        return reqBody;
    }

    @Override
    public Map<String, Object> getReqBody(ThirdQueryDTO thirdQueryDTO) {
        Map<String, Object> params = new HashMap<>();
        params.put("language", "0");
        params.put("trackingType", "1");
        params.put("methodType", "1");
        params.put("trackingNumber", "[\""+thirdQueryDTO.getTransOrderNo()+"\"]");
        return params;
    }

    @Override
    public ThirdTransStatusEnum parseThirdCodeToEnum(String transCode) {
        ThirdTransStatusEnum statusEnum = ThirdTransStatusEnum.ON_THE_WAY;
        switch (transCode) {
            case "50":
                statusEnum = ThirdTransStatusEnum.COLLECT;
                break;
            case "30": ;
            case "31": ;
            case "3036": ;
            case "44": ;
            case "130": ;
            case "123":
                statusEnum = ThirdTransStatusEnum.ON_THE_WAY;
                break;
            case "33": ;
            case "70": ;
                statusEnum = ThirdTransStatusEnum.PUZZLE;
                break;
            case "80": ;
            case "8000": ;
            case "607":
                statusEnum = ThirdTransStatusEnum.RECEIVED;
                break;
            case "99":
                statusEnum = ThirdTransStatusEnum.REJECT_ON_WAY;
                break;
            case "648":
                statusEnum = ThirdTransStatusEnum.REJECT_FINISH;
                break;
            default: ;break;
        }
        return statusEnum;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public Object getHeaders(Map<String, Object> headers) {
        return null;
    }
}
