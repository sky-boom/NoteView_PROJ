package com.wzr.model.Po;

import java.sql.Timestamp;

public class Ext_Schedule
{
    private int id;
    private Timestamp created;
    private String title;
    private String description;
    private int progress;
    private int anticipation;
    private String remarks;
    private int finished;

    public Ext_Schedule()
    {
    }

    public Ext_Schedule(int id, Timestamp created, String title, String description, int progress, int anticipation, String remarks, int finished)
    {
        this.id = id;
        this.created = created;
        this.title = title;
        this.description = description;
        this.progress = progress;
        this.anticipation = anticipation;
        this.remarks = remarks;
        this.finished = finished;  //创建时默认就是0
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Timestamp getCreated()
    {
        return created;
    }

    public void setCreated(Timestamp created)
    {
        this.created = created;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getProgress()
    {
        return progress;
    }

    public void setProgress(int progress)
    {
        this.progress = progress;
    }

    public int getAnticipation()
    {
        return anticipation;
    }

    public void setAnticipation(int anticipation)
    {
        this.anticipation = anticipation;
    }

    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public int getFinished()
    {
        return finished;
    }

    public void setFinished(int finished)
    {
        this.finished = finished;
    }

    @Override
    public String toString()
    {
        return "Ext_Schedule{" +
                "id=" + id +
                ", created=" + created +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", progress=" + progress +
                ", anticipation=" + anticipation +
                ", remarks='" + remarks + '\'' +
                ", finished=" + finished +
                '}';
    }
}
