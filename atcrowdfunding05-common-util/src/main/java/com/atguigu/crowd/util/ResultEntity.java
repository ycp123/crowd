package com.atguigu.crowd.util;

/**
 * 用于统一项目中所有 Ajax 请求的返回值类型
 */
public class ResultEntity<T> {
    public static String SUCCESS = "SUCCESS";
    public static String FAILED = "FAILED";
    public static final String NO_MESSAGE = "NO_MESSAGE";
    public static final String NO_DATA = "NO_DATA";
    private String operationResult;
    private String operationMessage;
    private T queryData;
    public ResultEntity(){

    }

    public ResultEntity(String operationResult, String operationMessage, T queryData) {
        super();
        this.operationResult = operationResult;
        this.operationMessage = operationMessage;
        this.queryData = queryData;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public String getOperationMessage() {
        return operationMessage;
    }

    public T getQueryData() {
        return queryData;
    }

    public void setOperationResult(String operationResult) {
        this.operationResult = operationResult;
    }

    public void setOperationMessage(String operationMessage) {
        this.operationMessage = operationMessage;
    }

    public void setQueryData(T queryData) {
        this.queryData = queryData;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "operationResult='" + operationResult + '\'' +
                ", operationMessage='" + operationMessage + '\'' +
                ", queryData=" + queryData +
                '}';
    }

    /**
     * 请求成功不返回数据
     * @param
     * @return
     */
    public static <Type> ResultEntity<Type> successWithOutData(){
        return new ResultEntity<Type>(SUCCESS,NO_MESSAGE,null);
    }

    /**
     * 请求成功返回数据
     * @param queryData
     * @return
     */
    public static <Type> ResultEntity<Type> successWithData(Type queryData){
        return new ResultEntity<Type>(SUCCESS,NO_MESSAGE,queryData);
    }

    /**
     * 请求失败返回信息
     * @param operationMessage
     * @param <Type>
     * @return
     */
    public static <Type> ResultEntity<Type> failed(String operationMessage){
        return new ResultEntity<Type>(FAILED,operationMessage,null);
    }

}
