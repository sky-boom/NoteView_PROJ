package com.wzr.dao;

import com.wzr.model.Po.Ext_Schedule;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public interface ScheduleMapper
{
    //查询所有计划
    List<Ext_Schedule> queryAllSche();

    //查询当天计划
    List<Ext_Schedule> queryTodaySche();

    //查询范围计划
    List<Ext_Schedule> queryRangeSche(Timestamp start, Timestamp end);

    //修改计划
    void edit(Map map);

    //添加一条计划
    void addOne(Ext_Schedule extSchedule);

    //删除一条计划
    void delete(int id);
}
