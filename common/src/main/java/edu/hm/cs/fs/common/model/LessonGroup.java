package edu.hm.cs.fs.common.model;

import java.util.List;

/**
 * @author Fabio
 */
public class LessonGroup {
    private SimpleModule module;
    private SimplePerson teacher;
    private List<Integer> groups;

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
