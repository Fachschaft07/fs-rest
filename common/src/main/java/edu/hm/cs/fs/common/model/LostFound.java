package edu.hm.cs.fs.common.model;

import java.util.Date;

/**
 * Created by Fabio on 18.02.2015.
 */
public class LostFound {

    private String id;
    private String subject;
    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
