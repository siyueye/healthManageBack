package com.health.service;

import com.health.entity.Diet;
import com.health.entity.withPages.DietExtend;

import java.util.HashMap;

public interface DietService {
    public HashMap getDietList(DietExtend Diet) throws Exception;

    public Diet getDietInfo(String id) throws Exception;
    public int addDiet(Diet Diet);
    public int updateDiet(Diet Diet);
    public int delDiet(int id);
}
