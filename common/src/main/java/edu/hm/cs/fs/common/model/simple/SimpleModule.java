package edu.hm.cs.fs.common.model.simple;

import edu.hm.cs.fs.common.model.Module;

/**
 * @author Fabio
 */
public class SimpleModule {
    private String id;
    private String name;

    public SimpleModule() {
    }

    public SimpleModule(final Module module) {
        id = module.getId();
        name = module.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
