package edu.hm.cs.fs.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.common.model.simple.SimpleModule;
import edu.hm.cs.fs.restapi.parser.cache.CachedModuleParser;

/**
 * Created by Fabio on 02.09.2015.
 */
@RestController
public class ModuleController {
    /**
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "getModules")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/modules", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public List<SimpleModule> getModules() throws Exception {
        return CachedModuleParser.getInstance().getAll()
                .parallelStream()
                .map(SimpleModule::new)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param moduleId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "getModuleById")
    @RequestMapping(method = RequestMethod.GET, value = "/rest/api/1/module", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID of a module", required = true, dataType = "string", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 101, message = "java.lang.RuntimeException"),
            @ApiResponse(code = 103, message = "org.springframework.web.bind.MissingServletRequestParameterException"),
            @ApiResponse(code = 107, message = "java.lang.IllegalStateException"),
            @ApiResponse(code = 109, message = "java.io.IOException"),
            @ApiResponse(code = 113, message = "javax.xml.xpath.XPathExpressionException"),
            @ApiResponse(code = 200, message = "Success")
    })
    public Module getModuleById(@RequestParam(value = "id") String moduleId) throws Exception {
        return CachedModuleParser.getInstance()
                .getById(moduleId)
                .orElseThrow(() -> new IllegalStateException("No module found with id '" + moduleId + "'."));
    }
}
