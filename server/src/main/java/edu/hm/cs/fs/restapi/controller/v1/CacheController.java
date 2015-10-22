package edu.hm.cs.fs.restapi.controller.v1;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CacheController {

  private final FilenameFilter filter = new FilenameFilter() {
    @Override
    public boolean accept(File arg0, String arg1) {
      return arg1.startsWith("fs_rest") && arg1.endsWith(".json");
    }
  };
  
  @RequestMapping("/rest/api/1/cache")
  public String show(Map<String, Object> model) {
     
    final List<String> files = Arrays.asList(new File(System.getProperty("java.io.tmpdir")).list(filter));
    
    model.put("cache", files);
    //model.put("scheduled", CacheUpdater.getScheduledFutures());
    
    return "cache";
  }
}
