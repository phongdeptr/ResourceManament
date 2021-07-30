/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.List;

/**
 *
 * @author ADMIN
 */
public class Pagenation<T> {

    private List<T> result;
    private int pageNum;
    private int totalResult;

    public Pagenation() {
    }

    public Pagenation(List<T> result, int pageNum, int totalResult) {
        this.result = result;
        this.pageNum = pageNum;
        this.totalResult = totalResult;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }
}
