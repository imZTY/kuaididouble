package com.zty.kdd.third.dto;

/**
 * 关键日志对象，用于记录日志里的关键信息
 * @author: tianyi.zeng
 * @create: 19/8/2021 - 下午 11:49
 */
public class KeyLogDTO {

    private boolean success;

    private String thirdMessageId;

    public static KeyLogDTO success(String thirdMessageId) {
        KeyLogDTO logDTO = new KeyLogDTO();
        logDTO.setSuccess(true);
        logDTO.setThirdMessageId(thirdMessageId);
        return logDTO;
    }

    public static KeyLogDTO fail(String thirdMessageId) {
        KeyLogDTO logDTO = new KeyLogDTO();
        logDTO.setSuccess(false);
        logDTO.setThirdMessageId(thirdMessageId);
        return logDTO;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getThirdMessageId() {
        return thirdMessageId;
    }

    public void setThirdMessageId(String thirdMessageId) {
        this.thirdMessageId = thirdMessageId;
    }
}
