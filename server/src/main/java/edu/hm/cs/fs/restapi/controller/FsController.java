package edu.hm.cs.fs.restapi.controller;

import edu.hm.cs.fs.common.model.Presence;
import edu.hm.cs.fs.restapi.parser.Parser;
import edu.hm.cs.fs.restapi.parser.PresenceParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Fabio
 */
@RestController
public class FsController {
    /**
     * 
     * @return
     */
    @RequestMapping("/rest/api/fs/presence")
    public List<Presence> getPresence() {
        return new PresenceParser().parse();
    }
}
