package com.health.entity;

import java.sql.Timestamp;

public class Sport {
    private int id;
    private int userid;
    int scene;//称重场景
    private Timestamp daytime;//时间
    private float spelltime;
    private float heat;


    private String sportevent;

    public String getSportevent() {
        return sportevent;
    }

    public void setSportevent(String sportevent) {
        this.sportevent = sportevent;
    }

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

    public float getSpelltime() {
        return spelltime;
    }

    public void setSpelltime(float spelltime) {
        this.spelltime = spelltime;
    }

    public float getHeat() {
        return heat;
    }

    public void setHeat(float heat) {
        this.heat = heat;
    }
}
