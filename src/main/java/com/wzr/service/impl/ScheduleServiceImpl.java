package com.wzr.service.impl;
import com.wzr.dao.ScheduleMapper;
import com.wzr.model.Bo.Ext_ScheJson;
import com.wzr.model.Po.Ext_Schedule;
import com.wzr.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService
{
    @Autowired
    ScheduleMapper scheduleMapper;

    //查询所有计划
    @Override
    public Ext_ScheJson queryAllSche()
    {
        Ext_ScheJson extScheJson = new Ext_ScheJson(0, scheduleMapper.queryAllSche() );
        return extScheJson;
    }

    //查询当天计划
    @Override
    public Ext_ScheJson queryTodaySche()
    {
        Ext_ScheJson extScheJson = new Ext_ScheJson(0, scheduleMapper.queryTodaySche() );
        return extScheJson;
    }

    //查询指定范围计划
    @Override
    public Ext_ScheJson queryRangeSche(Timestamp start, Timestamp end)
    {
        Ext_ScheJson extScheJson = new Ext_ScheJson(0, scheduleMapper.queryRangeSche(start, end) );
        return extScheJson;
    }

    @Override
    public void edit(Map map)
    {
        scheduleMapper.edit(map);
    }

    @Override
    public void addOne(Ext_Schedule extSchedule)
    {
        scheduleMapper.addOne(extSchedule);
    }

    @Override
    public void delete(int id)
    {
        scheduleMapper.delete(id);
    }
}
