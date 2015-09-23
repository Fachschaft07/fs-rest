package edu.hm.cs.fs.common.model.simple;


import java.util.Calendar;

import edu.hm.cs.fs.common.constant.RoomType;
import edu.hm.cs.fs.common.model.Room;

/**
 * Created by Luca on 02.09.2015.
 */
public class SimpleRoom {
  private String name;
  private int hour;
  private int minute;
  private RoomType roomType;

  public SimpleRoom() {
  }
  
  public SimpleRoom(final Room room) {
    this.name = room.getName();
    this.hour = room.getFreeUntilEnd().getEnd().get(Calendar.HOUR_OF_DAY);
    this.minute = room.getFreeUntilEnd().getEnd().get(Calendar.MINUTE);
    this.roomType = room.getRoomType();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getHour() {
    return hour;
  }

  public void setHour(int hour) {
    this.hour = hour;
  }

  public int getMinute() {
    return minute;
  }

  public void setMinute(int minute) {
    this.minute = minute;
  }
  
  public RoomType getRoomType() {
    return roomType;
  }

  public void setRoomType(RoomType roomType) {
    this.roomType = roomType;
  }
}
