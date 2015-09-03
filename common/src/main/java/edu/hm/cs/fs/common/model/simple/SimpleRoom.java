package edu.hm.cs.fs.common.model.simple;


import edu.hm.cs.fs.common.model.Room;

import java.util.Calendar;

/**
 * Created by Luca on 02.09.2015.
 */
public class SimpleRoom {
  private String name;
  private int hour;
  private int minute;

  public SimpleRoom() {
  }
  
  public SimpleRoom(final Room room) {
    this.name = room.getName();
    this.hour = room.getFreeUntilEnd().getEnd().get(Calendar.HOUR_OF_DAY);
    this.minute = room.getFreeUntilEnd().getEnd().get(Calendar.MINUTE);
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
}
