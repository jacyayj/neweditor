package com.weiyin.mobile.neweditor.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jacyayj on 2016/1/28.
 */
public class Response<T> {

    /**
     *  requestId : 标识一次请求和反馈
     *  code : 结果代码
     *  desc : 结果描述(code=0时，为空)
     *  data : 泛型数据
     */

    //标识一次请求和反馈
    @SerializedName("requestId")
    private String requestId = null;

    //结果代码
    //0：操作成功,其余错误代码单独给出
    @SerializedName("code")
    private int code = -1;

    //结果描述(code=0时，为空)
    @SerializedName("desc")
    private String desc = null;

    //泛型数据
    @SerializedName("data")
    private T data = null;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
