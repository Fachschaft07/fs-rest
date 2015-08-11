package edu.hm.cs.fs.common.model;

import java.util.Calendar;

import edu.hm.cs.fs.common.constant.EventType;

/**
 * @author Fabio
 */
public class Event {
    private String id;
    private String name;
    private Calendar start;
    private Calendar end;
    private EventType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}
