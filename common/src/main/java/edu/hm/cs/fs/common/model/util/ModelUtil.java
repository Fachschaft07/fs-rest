package edu.hm.cs.fs.common.model.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModelUtil {

  public static <Q, T> T convert(Q element, Class<T> to) {
    T obj = null;

    try {
      obj = (T) to.getConstructor(element.getClass()).newInstance(element);
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
    }

    return obj;
  }

  public static <Q, T> List<T> convert(List<Q> elements, Class<T> to) {
    Q element = null;
    List<T> list = new ArrayList<T>();
    Iterator<Q> it = elements.listIterator();

    while (it.hasNext()) {
      element = it.next();
      try {
        list.add((T) to.getConstructor(element.getClass()).newInstance(element));
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
          | InvocationTargetException | NoSuchMethodException | SecurityException e) {
        e.printStackTrace();
      }
    }

    return list;
  }

}
