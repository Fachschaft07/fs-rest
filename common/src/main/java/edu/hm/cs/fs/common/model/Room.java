package edu.hm.cs.fs.common.model;


import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.Time;

import java.util.List;
import java.util.Map;

/**
 * Created by Fabio on 27.04.2015.
 */
public class Room  {
    private String name;
    private Integer capacity;
    private Time freeUntil;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Time getFreeUntil() {
        return freeUntil;
    }

    public void setFreeUntil(Time freeUntil) {
        this.freeUntil = freeUntil;
    }
}
