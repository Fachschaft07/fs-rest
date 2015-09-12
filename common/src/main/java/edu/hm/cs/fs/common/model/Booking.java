package edu.hm.cs.fs.common.model;

import edu.hm.cs.fs.common.constant.EventType;

import java.util.Date;

/**
 * @author Fabio
 */
public class Booking {
    private Date start;
    private Date end;
    private String room;
    private EventType type;
    private String suffix;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
