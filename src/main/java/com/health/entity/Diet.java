package com.health.entity;

import java.sql.Timestamp;

public class Diet {
    private int id;
    private int userid;
    private int scene;//场景
    private Timestamp daytime;//时间
    private String food;
    private float grammage;
    private float heat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setScene(int scene) {
        this.scene = scene;
    }

    public Timestamp getDaytime() {
        return daytime;
    }

    public void setDaytime(Timestamp daytime) {
        this.daytime = daytime;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public float getGrammage() {
        return grammage;
    }

    public void setGrammage(float grammage) {
        this.grammage = grammage;
    }

    public float getHeat() {
        return heat;
    }

    public void setHeat(float heat) {
        this.heat = heat;
    }


}
