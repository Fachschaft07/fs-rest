package edu.hm.cs.fs.common.constant;

import java.util.Arrays;
import java.util.List;

public enum RoomType {

  ALL, AUDITORIUM("r0005", "r0006", "r0007", "r0009", "r0010", "r0011", "r0012", "r0058", "r1006",
      "r1007", "r1008", "r2007"), LABORATORY("r1009", "r1010a", "r1012", "r2008", "r2009", "r2010",
          "r2012", "r2014", "r3019", "r3020", "r3021", "r3024",
          "r3026"), LOUNGE("r0014", "r0015", "r1010");


  private final List<String> rooms;

  private RoomType(String... rooms) {
    this.rooms = Arrays.asList(rooms);
  }

  public boolean contains(String name) {
    return rooms.contains(name);
  }

  public static RoomType getByName(String name) {

    for (RoomType type : values()) {
      if (type.contains(name)) {
        return type;
      }
    }

    return RoomType.AUDITORIUM;
  }

}
