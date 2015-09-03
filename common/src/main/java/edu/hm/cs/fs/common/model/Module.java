package edu.hm.cs.fs.common.model;

import java.util.List;

import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.constant.TeachingForm;
import edu.hm.cs.fs.common.model.simple.SimpleModuleCode;
import edu.hm.cs.fs.common.model.simple.SimplePerson;


/**
 * Created by Fabio on 18.02.2015.
 */
public class Module {
    private String id;
    private String name;
    private int credits;
    private int sws;
    private SimplePerson responsible;
    private List<SimplePerson> teachers;
    private List<String> languages;
    private TeachingForm teachingForm;
    private String expenditure;
    private String requirements;
    private String goals;
    private String content;
    private String media;
    private String literature;
    private Study program;
    private List<SimpleModuleCode> modulCodes;

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

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getSws() {
        return sws;
    }

    public void setSws(int sws) {
        this.sws = sws;
    }

    public SimplePerson getResponsible() {
        return responsible;
    }

    public void setResponsible(SimplePerson responsible) {
        this.responsible = responsible;
    }

    public List<SimplePerson> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<SimplePerson> teachers) {
        this.teachers = teachers;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public TeachingForm getTeachingForm() {
        return teachingForm;
    }

    public void setTeachingForm(TeachingForm teachingForm) {
        this.teachingForm = teachingForm;
    }

    public String getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(String expenditure) {
        this.expenditure = expenditure;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getLiterature() {
        return literature;
    }

    public void setLiterature(String literature) {
        this.literature = literature;
    }

    public Study getProgram() {
        return program;
    }

    public void setProgram(Study program) {
        this.program = program;
    }

    public List<SimpleModuleCode> getModulCodes() {
        return modulCodes;
    }

    public void setModulCodes(List<SimpleModuleCode> modulCodes) {
        this.modulCodes = modulCodes;
    }
}
