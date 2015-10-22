package edu.hm.cs.fs.restapi.controller.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
public class EndpointsController {
  private final RequestMappingHandlerMapping handlerMapping;
  private List<String> urls;
  
  @Autowired
  public EndpointsController(RequestMappingHandlerMapping handlerMapping) {
    this.handlerMapping = handlerMapping;
  }

  @RequestMapping("/rest/api/1")
  public String show(Map<String, Object> model) {
     
    if(urls == null){
      urls = new ArrayList<>();
      this.handlerMapping.getHandlerMethods().entrySet().forEach(entry -> {
        urls.addAll(entry.getKey().getPatternsCondition().getPatterns());
        Collections.sort(urls);
      });
    }
    
    model.put("urls", urls);
    return "urls";
  }
}
