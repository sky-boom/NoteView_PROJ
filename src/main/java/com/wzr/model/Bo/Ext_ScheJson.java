package com.wzr.model.Bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wzr.model.Po.Ext_Schedule;

import java.util.List;

public class Ext_ScheJson
{
    private int code;
    @JsonProperty("data")
    private List<Ext_Schedule> extSchedule;

    public Ext_ScheJson()
    {
    }

    public Ext_ScheJson(int code, List<Ext_Schedule> extSchedule)
    {
        this.code = code;
        this.extSchedule = extSchedule;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public List<Ext_Schedule> getExtSchedule()
    {
        return extSchedule;
    }

    public void setExtSchedule(List<Ext_Schedule> extSchedule)
    {
        this.extSchedule = extSchedule;
    }

    @Override
    public String toString()
    {
        return "Ext_ScheJson{" +
                "code=" + code +
                ", extSchedule=" + extSchedule +
                '}';
    }
}
