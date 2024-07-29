package com.health.service.impl;

import com.health.entity.TotalRecord;
import com.health.entity.Weight;
import com.health.entity.withPages.WeightExtend;
import com.health.service.WeightService;
import com.health.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class weightServiceImpl implements WeightService {
    @Autowired
    JdbcUtils jdbcUtils;
    @Override
    public List<Weight> getWeightLine(WeightExtend weight) throws Exception {
        Timestamp startDate = weight.getStartDaytime();//开始时间
        Timestamp endDate = weight.getEndDaytime();//结束时间
        int userId =weight.getUserid();
        StringBuilder querySql = new StringBuilder("select * from h_weight where userid = ? ");
        List<Weight> weightList = new ArrayList<>();
        if(!StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)){
            querySql.append("and daytime >= ? ORDER BY daytime DESC");
            weightList.addAll(jdbcUtils.executeQuery(querySql.toString(), Weight.class, userId,startDate));
        }else if(StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
            querySql.append("and daytime <= ? ORDER BY daytime DESC");
            weightList.addAll(jdbcUtils.executeQuery(querySql.toString(), Weight.class, userId,endDate));
        }else if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
            querySql.append("and daytime between ? and ? ORDER BY daytime DESC ");
            weightList.addAll(jdbcUtils.executeQuery(querySql.toString(), Weight.class, userId,startDate,endDate));
        }else{//开始时间和结束时间为空时
            querySql.append("ORDER BY daytime DESC ");
            weightList.addAll(jdbcUtils.executeQuery(querySql.toString(), Weight.class, userId));
        }
        return weightList;
    }
    @Override
    public HashMap getWeightList(WeightExtend weight) throws Exception {
        HashMap result = new HashMap();
        long sumCount = 0;

        int pageNum = weight.getPagenum();
        int pageSize = weight.getPagesize();
        int skipNum = (pageNum-1)*pageSize;
        Timestamp startDate = weight.getStartDaytime();//开始时间
        Timestamp endDate = weight.getEndDaytime();//结束时间
        int userId =weight.getUserid();
        List<TotalRecord> totalNumList=null;
        StringBuilder querySql = new StringBuilder("select * from h_weight where userid = ? ");
        StringBuilder countSql = new StringBuilder("select count(*) totalcnt from h_weight where userid = ? ");
        List<Weight> weightList = new ArrayList<>();
        if(!StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)){
             querySql.append("and daytime >= ? ORDER BY daytime DESC LIMIT ? OFFSET ?");
             countSql.append("and daytime >= ? ");
            //查询总共有多少条数据
            totalNumList = jdbcUtils.executeQuery(countSql.toString(), TotalRecord.class,weight.getUserid(),startDate);
             weightList.addAll(jdbcUtils.executeQuery(querySql.toString(), Weight.class, userId,startDate,pageSize,skipNum));
        }else if(StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
            querySql.append("and daytime <= ? ORDER BY daytime DESC LIMIT ? OFFSET ?");
            countSql.append("and daytime<= ? ");
            //查询总共有多少条数据
            totalNumList = jdbcUtils.executeQuery(countSql.toString(),TotalRecord.class,weight.getUserid(),endDate);
            weightList.addAll(jdbcUtils.executeQuery(querySql.toString(), Weight.class, userId,endDate,pageSize,skipNum));
        }else if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
            querySql.append("and daytime between ? and ? ORDER BY daytime DESC LIMIT ? OFFSET ?");
            countSql.append("and daytime between ? and ? ");
            //查询总共有多少条数据
            totalNumList = jdbcUtils.executeQuery(countSql.toString(),TotalRecord.class,weight.getUserid(),startDate,endDate);
            weightList.addAll(jdbcUtils.executeQuery(querySql.toString(), Weight.class, userId,startDate,endDate,pageSize,skipNum));
        }else{//开始时间和结束时间为空时
            querySql.append("ORDER BY daytime DESC LIMIT ? OFFSET ?");
            weightList.addAll(jdbcUtils.executeQuery(querySql.toString(), Weight.class, userId,pageSize,skipNum));
            totalNumList = jdbcUtils.executeQuery(countSql.toString(),TotalRecord.class,weight.getUserid());
        }
        // 获取结果集中的总条数
        if(totalNumList.size()>0){
            // 获取表总行数
            sumCount = totalNumList.get(0).getTotalcnt();
        }
        result.put("total",sumCount);
        result.put("record",weightList);
        return result;
    }

    @Override
    public Weight getWeightInfo(String id) throws Exception {
        List<Weight> weightList=  jdbcUtils.executeQuery("select * from h_weight where id = ?",Weight.class,Integer.parseInt(id));
        if(weightList.size()>0){
            return  weightList.get(0);
        }
        return null;
    }

    @Override
    public int addWeight(Weight weight) {
        String sql = "insert into h_weight (userid, daytime, scene, weight, waistline, bmi, faterate, targetweight) values(?,?,?,?,?,?,?,?)";
        Object[] params = {weight.getUserid(),weight.getDaytime(),weight.getScene(),weight.getWeight(),weight.getWaistline(),weight.getBmi(),weight.getFaterate(),weight.getTargetweight()};
        int total_rs = jdbcUtils.update(sql ,params);
        return total_rs;
    }

    @Override
    public int updateWeight(Weight weight) {
        String sql = "update h_weight set daytime = ?, scene = ?, weight=?,waistline=?, bmi=?, faterate=?, targetweight=? WHERE id= ?";

        Object[] params = {weight.getDaytime(),weight.getScene(),weight.getWeight(),weight.getWaistline(),weight.getBmi(),weight.getFaterate(),weight.getTargetweight(),weight.getId()};

        int total_rs = jdbcUtils.update(sql,params);
        return total_rs;
    }

    @Override
    public int delWeight(int id) {
        String sql = "DELETE FROM h_weight WHERE id= ?";
        Object[] params = {id};
        int total_rs = jdbcUtils.update(sql,params);
        return total_rs;
    }
}
