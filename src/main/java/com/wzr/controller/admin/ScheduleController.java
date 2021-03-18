package com.wzr.controller.admin;

import com.alibaba.fastjson.JSON;
import com.wzr.model.Bo.Ext_ScheJson;
import com.wzr.model.Po.Ext_Schedule;
import com.wzr.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  后台管理系统之彩蛋
 */
@RestController
@RequestMapping("/admin/sche")
public class ScheduleController
{
    @Autowired
    ScheduleService scheduleService;

    @RequestMapping(value = "/today")
    public Ext_ScheJson getTodaySche()
    {
        return scheduleService.queryTodaySche();
    }

    @RequestMapping(value = "/all")
    public Ext_ScheJson getAllSche()
    {
        return scheduleService.queryAllSche();
    }

    @RequestMapping(value = "/range")
    public Ext_ScheJson getRangeSche(String start, String end)
    {
        Timestamp t1 = Timestamp.valueOf(start);
        Timestamp t2 = Timestamp.valueOf(end);
        System.out.println(start);
        System.out.println(end);
        return scheduleService.queryRangeSche(t1, t2);
    }

    @RequestMapping(value = "/edit")
    public void edit(String map)
    {
        Map maps = (Map) JSON.parse(map);
        scheduleService.edit(maps);
    }

    @RequestMapping(value = "/add")
    public void addOne()
    {
        scheduleService.addOne(new Ext_Schedule());
    }

    @RequestMapping(value = "/del")
    public void delete(int id)
    {
        scheduleService.delete(id);
    }
}
