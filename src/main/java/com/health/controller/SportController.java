package com.health.controller;

import com.health.entity.Sport;
import com.health.entity.withPages.SportExtend;
import com.health.service.SportService;
import com.health.util.ResponseData;
import com.health.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/sport")
public class SportController {
    @Autowired
    private SportService sportService;

    @RequestMapping(path="/add" , method= RequestMethod.POST)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> addSportRecord(@RequestBody Sport sport){
        int result = sportService.addSport(sport);
        return ResponseData.success(result);
    }

    @RequestMapping(path="/del",method = RequestMethod.DELETE)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> delSportRecord(@RequestParam(name = "id") int id){
        int result = sportService.delSport(id);
        return ResponseData.success(result);
    }


    @RequestMapping(path="/update", method= RequestMethod.PUT)
    @CrossOrigin(allowCredentials = "true")
    public  Result<?> updateSportRecord(@RequestBody Sport sport){
        int result = sportService.updateSport(sport);
        return ResponseData.success(result);
    }

    @RequestMapping(path="/getInfo", method= RequestMethod.GET)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> getSportInfo(@RequestParam(name = "id") String id) throws Exception {
        Sport sport = sportService.getSportInfo(id);
        return ResponseData.success(sport);
    }

    @RequestMapping(path ="/getList", method= RequestMethod.POST)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> getSportList(@RequestBody SportExtend sport) throws Exception {
        HashMap result = sportService.getSportList(sport);
        return ResponseData.success(result);
    }
}
