package edu.hm.cs.fs.common.model;

import java.util.Date;

/**
 * Created by Fabio on 18.02.2015.
 */
public class Meal  {
    
    private String id;
    private Date date;
    private String type;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
