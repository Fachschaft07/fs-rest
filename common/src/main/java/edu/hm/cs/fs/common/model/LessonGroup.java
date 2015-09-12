package edu.hm.cs.fs.common.model;

import edu.hm.cs.fs.common.model.simple.SimpleModule;
import edu.hm.cs.fs.common.model.simple.SimplePerson;

import java.util.List;

/**
 * @author Fabio
 */
public class LessonGroup {
    private Group group;
    private SimpleModule module;
    private SimplePerson teacher;
    private List<Integer> groups;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public SimpleModule getModule() {
        return module;
    }

    public void setModule(SimpleModule module) {
        this.module = module;
    }

    public SimplePerson getTeacher() {
        return teacher;
    }

    public void setTeacher(SimplePerson teacher) {
        this.teacher = teacher;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public void setGroups(List<Integer> groups) {
        this.groups = groups;
    }
}
