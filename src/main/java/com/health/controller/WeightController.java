package com.health.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.health.entity.Weight;
import com.health.entity.withPages.WeightExtend;
import com.health.service.WeightService;
import com.health.util.ResponseData;
import com.health.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/weight")
public class WeightController {
        @Autowired
        private WeightService weightService;

        @RequestMapping(path="/add" , method= RequestMethod.POST)
        @CrossOrigin(allowCredentials = "true")
        public Result<?> addWeightRecord(@RequestBody Weight weight){
           int result = weightService.addWeight(weight);
           return ResponseData.success(result);
        }

        @RequestMapping(path="/del",method = RequestMethod.DELETE)
        @CrossOrigin(allowCredentials = "true")
        public Result<?> delWeightRecord(@RequestParam(name = "id") int id){
            int result = weightService.delWeight(id);
            return ResponseData.success(result);
        }


        @RequestMapping(path="/update", method= RequestMethod.PUT)
        @CrossOrigin(allowCredentials = "true")
        public  Result<?> updateWeightRecord(@RequestBody Weight weight){
            int result = weightService.updateWeight(weight);
            return ResponseData.success(result);
        }

        @RequestMapping(path="/getInfo", method= RequestMethod.GET)
        @CrossOrigin(allowCredentials = "true")
        public Result<?> getWeightInfo(@RequestParam(name = "id") String id) throws Exception {
            Weight wight = weightService.getWeightInfo(id);
            return ResponseData.success(wight);
        }

        @RequestMapping(path ="/getList", method= RequestMethod.POST)
        @CrossOrigin(allowCredentials = "true")
        public Result<?> getWeightList(@RequestBody WeightExtend weight) throws Exception {
            HashMap result = weightService.getWeightList(weight);
            return ResponseData.success(result);
        }

        @RequestMapping(path ="/getLine", method= RequestMethod.POST)
        @CrossOrigin(allowCredentials = "true")
        public Result<?> getWeightLine(@RequestBody WeightExtend weight) throws Exception {
            List<Weight> lineList = weightService.getWeightLine(weight);
            JSONObject result = new JSONObject();
            if (lineList.size() > 0) {
                JSONArray weightArray = new JSONArray();
                JSONArray waistlineArray = new JSONArray();
                JSONArray bmiArray = new JSONArray();
                JSONArray daytimeArray = new JSONArray();
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < lineList.size(); i++) {
                    weightArray.add(lineList.get(i).getWeight());
                    waistlineArray.add(lineList.get(i).getWaistline());
                    bmiArray.add(lineList.get(i).getBmi());
                    daytimeArray.add(sdf1.format(lineList.get(i).getDaytime()));
                }
                result.put("weightArr", weightArray);
                result.put("waistlineArr", waistlineArray);
                result.put("bmiArr", bmiArray);
                result.put("daytimeArr", daytimeArray);
            }
            return ResponseData.success(result);
        }

}
