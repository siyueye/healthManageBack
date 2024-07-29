package com.health.entity.withPages;

import com.health.entity.Weight;

import java.sql.Timestamp;

public class WeightExtend extends Weight {
    int pagenum;
    int pagesize;
    Timestamp startDaytime;//开始称重时间
    Timestamp endDaytime;//结束称重时间
    public int getPagenum() {
        return pagenum;
    }

    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
    public Timestamp getStartDaytime() {
        return startDaytime;
    }

    public void setStartDaytime(Timestamp startDaytime) {
        this.startDaytime = startDaytime;
    }

    public Timestamp getEndDaytime() {
        return endDaytime;
    }

    public void setEndDaytime(Timestamp endDaytime) {
        this.endDaytime = endDaytime;
    }

}
