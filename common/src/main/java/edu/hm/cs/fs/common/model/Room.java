package edu.hm.cs.fs.common.model;


import edu.hm.cs.fs.common.constant.Time;

/**
 * Created by Fabio on 27.04.2015.
 */
public class Room  {
    private String name;
    private Integer capacity;
    private Time freeUntilEnd;

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

    public Time getFreeUntilEnd() {
        return freeUntilEnd;
    }

    public void setFreeUntilEnd(Time freeUntil) {
        this.freeUntilEnd = freeUntil;
    }
}
