package edu.hm.cs.fs.restapi.parser.cache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CacheUpdater {

  private static List<ScheduledFuture<?>> futures = new ArrayList<>();
  
  private static ScheduledExecutorService executer = Executors.newScheduledThreadPool(5);
  
  public static void scheduleAtFixedTime(Runnable runnable, long targetHour) {
    futures.add(executer.scheduleAtFixedRate(runnable, getHoursUntilTarget(targetHour), 24, TimeUnit.HOURS));
  }
  
  public static void scheduleAtFixedInterval(Runnable runnable, long interval, TimeUnit timeunit){
    futures.add(executer.scheduleAtFixedRate(runnable, interval, interval, timeunit));
  }

  public static void execute(Runnable runnable) {
    executer.schedule(runnable, 0, TimeUnit.MILLISECONDS);
  }
  
  private static long getHoursUntilTarget(long targetHour) {
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    return hour < targetHour ? targetHour - hour : targetHour - hour + 24;
}
}
