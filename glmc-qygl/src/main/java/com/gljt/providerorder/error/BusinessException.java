package com.gljt.providerorder.error;

/**
* @Author liwei
* @Date 2022/8/19 14:05
* @Version 1.0
*/
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 错误原因
     */
    private String errorMsg;


    /**
     * @param errorEnum
     */
    public BusinessException(ErrorEnum errorEnum) {
        this.errorCode = errorEnum.getErrCode();
        this.errorMsg = errorEnum.getErrMsg();
    }

    /**
     * 接收自定义errMsg的方式构造业务异常（通过覆盖原本errMsg）
     *
     * @param errorEnum
     * @param errMsg
     */
    public BusinessException(ErrorEnum errorEnum, String errMsg) {
        this.errorCode = errorEnum.getErrCode();
        this.errorMsg = errMsg;
    }


    @Override
    public String toString() {
        return "BusinessException {" +
                "errorCode = " + this.errorCode +
                "; errorMsg = " + this.errorMsg +
                " }";
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}