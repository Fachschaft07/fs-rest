package edu.hm.cs.fs.restapi.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @RequestMapping("/rest/api/fs/1/presence")
    public List<Presence> getPresence() {
        return new PresenceParser().parse();
    }
}
