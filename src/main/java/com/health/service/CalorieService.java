package com.health.service;

import com.health.entity.Calorie;
import com.health.entity.Calorie;

import java.util.List;

public interface CalorieService {
    public List<Calorie> getCalorieAll(String itemnameAll,String flag) throws Exception;
    public int addCalorie(Calorie calorie);
    public int updateCalorie(Calorie calorie);
    public int delCalorie(int id);
}
