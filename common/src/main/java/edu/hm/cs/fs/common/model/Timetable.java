package edu.hm.cs.fs.common.model;

import java.util.List;
import java.util.Map;

import edu.hm.cs.fs.common.constant.Day;


public class Timetable  {
    private String id;
    private Map<Day, List<Lesson>> lessons;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<Day, List<Lesson>> getLessons() {
        return lessons;
    }

    public void setLessons(Map<Day, List<Lesson>> lessons) {
        this.lessons = lessons;
    }
}
