package com.health.entity.withPages;


import com.health.entity.Diet;

import java.sql.Timestamp;

public class DietExtend extends Diet {
    int pagenum;
    int pagesize;

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

    Timestamp startDaytime;//开始时间
    Timestamp endDaytime;//结束时间
}
