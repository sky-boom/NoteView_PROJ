package com.wzr.service;

import com.wzr.model.Bo.Ext_ScheJson;
import com.wzr.model.Po.Ext_Schedule;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface ScheduleService
{
    //查询所有计划
    Ext_ScheJson queryAllSche();

    //查询当天计划
    Ext_ScheJson queryTodaySche();

    //查询范围计划
    Ext_ScheJson queryRangeSche(Timestamp start, Timestamp end);

    //修改计划
    void edit(Map map);

    //添加一条计划
    void addOne(Ext_Schedule extSchedule);

    //删除一条计划
    void delete(int id);
}
