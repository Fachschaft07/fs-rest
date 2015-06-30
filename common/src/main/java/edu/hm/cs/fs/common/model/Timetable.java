package edu.hm.cs.fs.common.model;

import java.util.List;


public class Timetable  {
    
    private String id;
	private List<Lesson> lessons;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}
}
