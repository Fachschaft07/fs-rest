package edu.hm.cs.fs.common.model;

import java.util.List;
import java.util.Map;

import edu.hm.cs.fs.common.constant.Day;
import edu.hm.cs.fs.common.constant.RoomType;
import edu.hm.cs.fs.common.constant.Time;

/**
 * Created by Fabio on 21.07.2015.
 */
public class RoomOccupation {
    private String name;
    private Map<Day, List<Time>> occupied;
    private Integer capacity;
    private RoomType roomType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Day, List<Time>> getOccupied() {
        return occupied;
    }

    public void setOccupied(Map<Day, List<Time>> occupied) {
        this.occupied = occupied;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public RoomType getRoomType() {
      return roomType;
    }
    
    public void setRoomType(RoomType roomType) {
      this.roomType = roomType;
    }
}
