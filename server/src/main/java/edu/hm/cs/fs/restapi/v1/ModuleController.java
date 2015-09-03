package edu.hm.cs.fs.restapi.v1;

import edu.hm.cs.fs.common.model.simple.SimpleModule;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.restapi.parser.cache.CachedModuleParser;

/**
 * Created by Fabio on 02.09.2015.
 */
@RestController
public class ModuleController {
    /**
     *
     * @return
     */
    @RequestMapping("/rest/api/1/modules")
    public List<SimpleModule> getModules() {
        return new CachedModuleParser().parse()
                .parallelStream()
                .map(SimpleModule::new)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param moduleId
     * @return
     */
    @RequestMapping("/rest/api/1/module")
    public Module getModuleById(@RequestParam(value = "id") String moduleId) {
        return new CachedModuleParser()
                .findById(moduleId)
                .orElseThrow(() -> new IllegalStateException("No module found with id '" + moduleId + "'."));
    }
}
