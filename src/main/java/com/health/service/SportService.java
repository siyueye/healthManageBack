package com.health.service;

import com.health.entity.Sport;
import com.health.entity.withPages.SportExtend;

import java.util.HashMap;

public interface SportService {
    public HashMap getSportList(SportExtend Sport) throws Exception;

    public Sport getSportInfo(String id) throws Exception;
    public int addSport(Sport Sport);
    public int updateSport(Sport Sport);
    public int delSport(int id);
}
