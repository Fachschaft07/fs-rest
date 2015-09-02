package edu.hm.cs.fs.common.model;


import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Time;

public class Lesson {
    private Day day;
    private Time time;
    private String room;
    private String suffix;
    private SimpleModule module;
    private SimplePerson teacher;

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public SimpleModule getModule() {
        return module;
    }

    public void setModule(SimpleModule module) {
        this.module = module;
    }

    public SimplePerson getTeacher() {
        return teacher;
    }

    public void setTeacher(SimplePerson teacher) {
        this.teacher = teacher;
    }
}
