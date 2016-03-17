package edu.hm.cs.fs.restapi.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.util.stream.Collectors;

import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CacheController {

    private final FilenameFilter filter = (arg0, arg1) -> arg1.startsWith("fs_rest") && arg1.endsWith(".json");

    @ApiOperation(value = "getCache")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/cache", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public String show(Map<String, Object> model) {
        model.put("cache", Arrays.asList(new File(System.getProperty("java.io.tmpdir")).listFiles(filter))
                .stream()
                .map(file -> file.getName() + " (" + String.format(Locale.getDefault(), "%1$tY-%1$tm-%1$td %1$tH:%1$tM", new Date(file.lastModified())) + ")")
                .collect(Collectors.toList()));
        //model.put("scheduled", CacheUpdater.getScheduledFutures());
        return "cache";
    }
}
