package edu.hm.cs.fs.restapi.controller.v1;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CacheController {

  private final FilenameFilter filter = (arg0, arg1) -> arg1.startsWith("fs_rest") && arg1.endsWith(".json");
  
  @RequestMapping("/rest/api/1/cache")
  public String show(Map<String, Object> model) {
     
    final List<String> files = Arrays.asList(new File(System.getProperty("java.io.tmpdir")).listFiles(filter))
            .stream()
            .map(file -> file.getName() + " (" + String.format(Locale.getDefault(), "%1$tY-%1$tm-%1$td %1$tH:%1$tM", new Date(file.lastModified())) + ")")
            .collect(Collectors.toList());
    
    model.put("cache", files);
    //model.put("scheduled", CacheUpdater.getScheduledFutures());
    
    return "cache";
  }
}
