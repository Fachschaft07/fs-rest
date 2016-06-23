package edu.hm.cs.fs.common.model;


import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Time;
import edu.hm.cs.fs.common.model.simple.SimpleModule;
import edu.hm.cs.fs.common.model.simple.SimplePerson;

import java.util.List;
import java.util.Set;

public class Lesson {
    private Day day;
    private int hour;
    private int minute;
    private Set<String> rooms;
    private String suffix;
    private SimpleModule module;
    private SimplePerson teacher;

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public Set<String> getRooms() {
        return rooms;
    }

    public void setRooms(Set<String> rooms) {
        this.rooms = rooms;
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
