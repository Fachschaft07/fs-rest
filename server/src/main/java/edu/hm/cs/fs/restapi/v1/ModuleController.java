package edu.hm.cs.fs.restapi.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

import javax.xml.xpath.XPathExpressionException;

import edu.hm.cs.fs.common.model.Module;
import edu.hm.cs.fs.restapi.parser.cache.CachedModuleParser;

/**
 * Created by Fabio on 02.09.2015.
 */
@RestController
public class ModuleController {
    /**
     *
     * @param moduleId
     * @return
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws XPathExpressionException 
     */
    @RequestMapping("/rest/api/1/module")
    public Module getModules(@RequestParam(value = "id") String moduleId) throws MalformedURLException, IOException, XPathExpressionException {
        final Optional<Module> module = new CachedModuleParser().findById(moduleId);
        if(module.isPresent()) {
            return module.get();
        }
        return null;
    }
}
