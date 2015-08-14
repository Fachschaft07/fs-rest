package edu.hm.cs.fs.restapi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.hm.cs.fs.common.model.Presence;
import edu.hm.cs.fs.restapi.parser.PresenceParser;

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
