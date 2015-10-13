package edu.hm.cs.fs.restapi.controller.v1;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("/rest/api/1/modules")
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
    @RequestMapping("/rest/api/1/module")
    public Module getModuleById(@RequestParam(value = "id") String moduleId) throws Exception {
        return CachedModuleParser.getInstance()
                .getById(moduleId)
                .orElseThrow(() -> new IllegalStateException("No module found with id '" + moduleId + "'."));
    }
}
