package com.weiyin.mobile.neweditor.Bean;


/**
 * Created by jacyayj on 2016/1/28.
 */
public class Page<T> extends SuperT{

    private Page() {
    }

    public static Page newInstance() {
        Page page = new Page();
        return page;
    }

    /**
     *  timeStamp : 分页大小;
     *  recordCount : 记录总数;
     *  currPage : 当前页码;
     *  dataList : 当前页数据列表;
     */

    private int pageSize = 5;
    private int recordCount;
    private int currPage = 0;
    private T[] dataList = null;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public Object getDataList() {
        return dataList;
    }

    public void setDataList(T dataList[]) {
        this.dataList = dataList;
    }
}
