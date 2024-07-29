package com.health.service;


import com.health.entity.Weight;
import com.health.entity.withPages.WeightExtend;

import java.util.HashMap;
import java.util.List;

public interface WeightService {
    public HashMap getWeightList(WeightExtend weight) throws Exception;

    public Weight getWeightInfo(String id) throws Exception;
    public int addWeight(Weight weight);
    public int updateWeight(Weight weight);
    public int delWeight(int id);

    public List<Weight> getWeightLine(WeightExtend weight) throws Exception;
}
