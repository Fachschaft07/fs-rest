package edu.hm.cs.fs.common.model;

import java.util.Date;

/**
 * @author Fabio
 */
public class Holiday {
    private String name;
    private Date start;
    private Date end;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
