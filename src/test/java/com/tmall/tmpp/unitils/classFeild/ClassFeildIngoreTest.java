package com.vinthuy.unitils.classFeild;

import java.util.List;

/**
 * Created by huruiyong on 4/20/16.
 */
public class ClassFeildIngoreTest {

    private  int totalNum;
    private  int status ;
    private int  findNum;
    private int pageSize;
    private String errorMsg	;
    private  int currentPage;
    private List<String> errorList;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFindNum() {
        return findNum;
    }

    public void setFindNum(int findNum) {
        this.findNum = findNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }
}
