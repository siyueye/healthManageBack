package com.health.entity;

public class Calorie {
    public int id;
    public String itemname ;
    public float heat ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String unit;

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public float getHeat() {
        return heat;
    }

    public void setHeat(float heat) {
        this.heat = heat;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
