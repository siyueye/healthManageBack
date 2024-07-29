package com.health.entity;

import javax.imageio.plugins.bmp.BMPImageWriteParam;
import java.sql.Timestamp;

public class Weight {
   private int id ;
   private int userid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int scene;//称重场景
    private float weight;//重量
    private float targetweight;//目标体重
    private float waistline;//腰围

    private float bmi;//BMI
    private float faterate;//体脂率

    private Timestamp daytime;//称重时间

    public float getTargetweight() {
        return targetweight;
    }

    public void setTargetweight(float targetweight) {
        this.targetweight = targetweight;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getScene() {
        return scene;
    }

    public Timestamp getDaytime() {
        return daytime;
    }

    public void setDaytime(Timestamp daytime) {
        this.daytime = daytime;
    }

    public void setScene(int scene) {
        this.scene = scene;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWaistline() {
        return waistline;
    }

    public void setWaistline(float waistline) {
        this.waistline = waistline;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public float getFaterate() {
        return faterate;
    }

    public void setFaterate(float faterate) {
        this.faterate = faterate;
    }



}
