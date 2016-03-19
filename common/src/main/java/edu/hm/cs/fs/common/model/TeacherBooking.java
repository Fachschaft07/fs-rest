package edu.hm.cs.fs.common.model;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.EventType;
import edu.hm.cs.fs.common.constant.Time;
import edu.hm.cs.fs.common.model.simple.SimplePerson;

import java.util.Date;

/**
 * @author Fabio
 */
public class TeacherBooking {
    private Time time;
    private Day day;
    private Person teacher;
    private String room;
    private Module module;

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

    public Person getTeacher() {
        return teacher;
    }

    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
}
