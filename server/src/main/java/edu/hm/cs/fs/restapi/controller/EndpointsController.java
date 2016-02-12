package edu.hm.cs.fs.restapi.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
public class EndpointsController {
    private final RequestMappingHandlerMapping handlerMapping;
    private List<String> urls;

    @Autowired
    public EndpointsController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @ApiOperation(value = "getSiteMap")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success")
    })
    public String show(Map<String, Object> model) {
        if (urls == null) {
            urls = this.handlerMapping.getHandlerMethods().entrySet().stream()
                    .flatMap(entry -> entry.getKey().getPatternsCondition().getPatterns().stream())
                    .collect(Collectors.toList());
        }

        model.put("urls", urls);
        return "urls";
    }
}
