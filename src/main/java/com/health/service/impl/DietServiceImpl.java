package com.health.service.impl;

import com.health.entity.Diet;
import com.health.entity.TotalRecord;
import com.health.entity.withPages.DietExtend;
import com.health.service.DietService;
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
public class DietServiceImpl implements DietService {
    @Autowired
    JdbcUtils jdbcUtils;
    @Override
    public HashMap getDietList(DietExtend diet) throws Exception {
        HashMap result = new HashMap();
        long sumCount = 0;

        int pageNum = diet.getPagenum();
        int pageSize = diet.getPagesize();
        int skipNum = (pageNum-1)*pageSize;
        Timestamp startDate = diet.getStartDaytime();//开始时间
        Timestamp endDate = diet.getEndDaytime();//结束时间
        int userId =diet.getUserid();
        StringBuilder querySql = new StringBuilder("select * from h_diet where userid = ? ");
        StringBuilder countSql = new StringBuilder("select count(*) totalcnt from h_diet where userid = ? ");
        List<Diet> dietList = new ArrayList<>();
        List<TotalRecord> totalNumList=null;
        if(!StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)){
            querySql.append("and daytime >= ? ORDER BY daytime DESC LIMIT ? OFFSET ?");
            countSql.append("and daytime >= ? ");
            //查询总共有多少条数据
            totalNumList = jdbcUtils.executeQuery(countSql.toString(),TotalRecord.class,diet.getUserid(),startDate);
            dietList.addAll(jdbcUtils.executeQuery(querySql.toString(), Diet.class, userId,startDate,pageSize,skipNum));
        }else if(StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
            querySql.append("and daytime <= ? ORDER BY daytime DESC LIMIT ? OFFSET ?");
            countSql.append("and daytime <= ? ");
            //查询总共有多少条数据
            totalNumList = jdbcUtils.executeQuery(countSql.toString(),TotalRecord.class,diet.getUserid(),endDate);
            dietList.addAll(jdbcUtils.executeQuery(querySql.toString(), Diet.class, userId,endDate,pageSize,skipNum));
        }else if(!StringUtils.isEmpty(startDate)&&!StringUtils.isEmpty(endDate)){
            querySql.append("and daytime between ? and ? ORDER BY daytime DESC LIMIT ? OFFSET ?");
            countSql.append("and daytime between ? and ? ");
            //查询总共有多少条数据
            totalNumList = jdbcUtils.executeQuery(countSql.toString(),TotalRecord.class,diet.getUserid(),startDate,endDate);
            dietList.addAll(jdbcUtils.executeQuery(querySql.toString(), Diet.class, userId,startDate,endDate,pageSize,skipNum));
        }else{//开始时间和结束时间为空时
            querySql.append("ORDER BY daytime DESC LIMIT ? OFFSET ?");
            dietList.addAll(jdbcUtils.executeQuery(querySql.toString(), Diet.class, userId,pageSize,skipNum));
            //查询总共有多少条数据
            totalNumList = jdbcUtils.executeQuery(countSql.toString(), TotalRecord.class,diet.getUserid());
        }

        // 获取结果集中的总条数
        if(totalNumList.size()>0){
            // 获取表总行数
            sumCount = totalNumList.get(0).getTotalcnt();
        }
        result.put("total",sumCount);
        result.put("record",dietList);
        return result;
    }

    @Override
    public Diet getDietInfo(String id) throws Exception {
        List<Diet> dietList=  jdbcUtils.executeQuery("select * from h_diet where id = ?",Diet.class,Integer.parseInt(id));
        if(dietList.size()>0){
            return  dietList.get(0);
        }
        return null;
    }

    @Override
    public int addDiet(Diet diet) {
        String sql = "insert into h_diet (userid, daytime, scene, food, grammage, heat) values(?,?,?,?,?,?)";
        Object[] params = {diet.getUserid(),diet.getDaytime(),diet.getScene(),diet.getFood(),diet.getGrammage(),diet.getHeat()};
        int total_rs = jdbcUtils.update(sql ,params);
        return total_rs;
    }

    @Override
    public int updateDiet(Diet diet) {
        String sql = "update h_diet set daytime = ?, scene = ?, food=?,grammage=?, heat=?  WHERE id= ?";

        Object[] params = {diet.getDaytime(),diet.getScene(),diet.getFood(),diet.getGrammage(),diet.getHeat(),diet.getId()};

        int total_rs = jdbcUtils.update(sql,params);
        return total_rs;
    }

    @Override
    public int delDiet(int id) {
        String sql = "DELETE FROM h_diet WHERE id= ?";
        Object[] params = {id};
        int total_rs = jdbcUtils.update(sql,params);
        return total_rs;
    }
}
