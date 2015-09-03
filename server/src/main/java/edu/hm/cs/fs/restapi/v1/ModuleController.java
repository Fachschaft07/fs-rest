package edu.hm.cs.fs.restapi.v1;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.xpath.XPathExpressionException;

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
     * @throws IOException 
     * @throws XPathExpressionException 
     * @throws MalformedURLException 
     */
    @RequestMapping("/rest/api/1/modules")
    public List<SimpleModule> getModules() throws MalformedURLException, XPathExpressionException, IOException {
        return new CachedModuleParser().parse()
                .parallelStream()
                .map(SimpleModule::new)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param moduleId
     * @return
     * @throws IllegalStateException 
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws XPathExpressionException 
     */
    @RequestMapping("/rest/api/1/module")
    public Module getModuleById(@RequestParam(value = "id") String moduleId) throws IllegalStateException, MalformedURLException, XPathExpressionException, IOException {
        return new CachedModuleParser()
                .findById(moduleId)
                .orElseThrow(() -> new IllegalStateException("No module found with id '" + moduleId + "'."));
    }
}
