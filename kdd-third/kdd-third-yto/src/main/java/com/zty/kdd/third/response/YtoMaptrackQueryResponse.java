package com.zty.kdd.third.response;

/**
 * @author: tianyi.zeng
 * @create: 5/9/2021 - 上午 10:20
 */
public class YtoMaptrackQueryResponse extends ThirdMaptrackQueryResponse {

    private boolean success;

    private SuccessMaptrackResponse successMaptrackResponse;

    private ErrorMaptrackResponse errorMaptrackResponse;

    public YtoMaptrackQueryResponse(SuccessMaptrackResponse successMaptrackResponse) {
        this.success = true;
        this.successMaptrackResponse = successMaptrackResponse;
    }

    public YtoMaptrackQueryResponse(ErrorMaptrackResponse errorMaptrackResponse) {
        this.success = false;
        this.errorMaptrackResponse = errorMaptrackResponse;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public SuccessMaptrackResponse getSuccessMaptrackResponse() {
        return successMaptrackResponse;
    }

    public void setSuccessMaptrackResponse(SuccessMaptrackResponse successMaptrackResponse) {
        this.successMaptrackResponse = successMaptrackResponse;
    }

    public ErrorMaptrackResponse getErrorMaptrackResponse() {
        return errorMaptrackResponse;
    }

    public void setErrorMaptrackResponse(ErrorMaptrackResponse errorMaptrackResponse) {
        this.errorMaptrackResponse = errorMaptrackResponse;
    }
}
