package com.health.service.impl;

import com.health.entity.Sport;
import com.health.entity.Sport;
import com.health.entity.Sport;
import com.health.entity.TotalRecord;
import com.health.entity.withPages.SportExtend;
import com.health.service.SportService;
import com.health.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SportServiceImpl implements SportService {
    @Autowired
    JdbcUtils jdbcUtils;
    @Override
    public HashMap getSportList(SportExtend sport) throws Exception {
        HashMap result = new HashMap();
        long sumCount = 0;
        int pageNum = sport.getPagenum();
        int pageSize = sport.getPagesize();
        int skipNum = (pageNum-1)*pageSize;
        Timestamp startDate = sport.getStartDaytime();//开始时间
        Timestamp endDate = sport.getEndDaytime();//结束时间
        int userId =sport.getUserid();
        StringBuilder querySql = new StringBuilder("select * from h_sport where userid = ? ");
        StringBuilder countSql = new StringBuilder("select count(*) totalcnt from h_sport where userid = ? ");
        List<Sport> sportList = new ArrayList<>();
        List<TotalRecord> totalNumList=null;
        if(!StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)){
            querySql.append("and daytime >= ? ORDER BY daytime DESC LIMIT ? OFFSET ?");
            countSql.append("and daytime >= ? ");
            //查询总共有多少条数据
            totalNumList = jdbcUtils.executeQuery(countSql.toString(), TotalRecord.class,sport.getUserid(),startDate);
            sportList.addAll(jdbcUtils.executeQuery(querySql.toString(), Sport.class, userId,startDate,pageSize,skipNum));
        }else if(StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
            querySql.append("and daytime <= ? ORDER BY daytime DESC LIMIT ? OFFSET ?");
            countSql.append("and daytime <= ? ");
            //查询总共有多少条数据
            totalNumList = jdbcUtils.executeQuery(countSql.toString(),TotalRecord.class,sport.getUserid(),endDate);
            sportList.addAll(jdbcUtils.executeQuery(querySql.toString(), Sport.class, userId,endDate,pageSize,skipNum));
        }else if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
            querySql.append("and daytime between ? and ? ORDER BY daytime DESC LIMIT ? OFFSET ?");
            countSql.append("and daytime between ? and ? ");
            //查询总共有多少条数据
            totalNumList = jdbcUtils.executeQuery(countSql.toString(),TotalRecord.class,sport.getUserid(),startDate,endDate);
            sportList.addAll(jdbcUtils.executeQuery(querySql.toString(), Sport.class, userId,startDate,endDate,pageSize,skipNum));
        }else{//开始时间和结束时间为空时
            querySql.append("ORDER BY daytime DESC LIMIT ? OFFSET ?");
            totalNumList = jdbcUtils.executeQuery(countSql.toString(),TotalRecord.class,sport.getUserid());
            sportList.addAll(jdbcUtils.executeQuery(querySql.toString(), Sport.class, userId,pageSize,skipNum));
        }
        // 获取结果集中的总条数
        if(totalNumList.size()>0){
            // 获取表总行数
            sumCount = totalNumList.get(0).getTotalcnt();
        }
        result.put("total",sumCount);
        result.put("record",sportList);
        return result;
    }

    @Override
    public Sport getSportInfo(String id) throws Exception {
        List<Sport> sportList=  jdbcUtils.executeQuery("select * from h_sport where id = ?",Sport.class,Integer.parseInt(id));
        if(sportList.size()>0){
            return  sportList.get(0);
        }
        return null;
    }

    @Override
    public int addSport(Sport sport) {
        String sql = "insert into h_sport (userid, daytime, scene,sportevent, spelltime, heat) values(?,?,?,?,?,?)";
        Object[] params = {sport.getUserid(),sport.getDaytime(),sport.getScene(),sport.getSportevent(),sport.getSpelltime(),sport.getHeat()};
        int total_rs = jdbcUtils.update(sql ,params);
        return total_rs;
    }

    @Override
    public int updateSport(Sport sport) {
        String sql = "update h_sport set daytime = ?, scene = ?,sportevent = ?, spelltime=?,heat=? WHERE id= ?";

        Object[] params = {sport.getDaytime(),sport.getScene(),sport.getSportevent(),sport.getSpelltime(),sport.getHeat(),sport.getId()};

        int total_rs = jdbcUtils.update(sql,params);
        return total_rs;
    }

    @Override
    public int delSport(int id) {
        String sql = "DELETE FROM h_sport WHERE id= ?";
        Object[] params = {id};
        int total_rs = jdbcUtils.update(sql,params);
        return total_rs;
    }
}
