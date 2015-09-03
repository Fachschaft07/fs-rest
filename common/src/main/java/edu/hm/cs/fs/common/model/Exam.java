package edu.hm.cs.fs.common.model;

import java.util.Date;
import java.util.List;

import edu.hm.cs.fs.common.constant.ExamGroup;
import edu.hm.cs.fs.common.constant.ExamType;
import edu.hm.cs.fs.common.constant.Study;
import edu.hm.cs.fs.common.model.simple.SimpleModule;
import edu.hm.cs.fs.common.model.simple.SimplePerson;


/**
 * Created by Fabio on 18.02.2015.
 */
public class Exam {
    private String code;
    private Study study;
    private SimpleModule module;
    private String subtitle;
    private List<Study> references;
    private List<SimplePerson> examiners;
    private List<String> rooms;
    private ExamType type;
    private String material;
    private ExamGroup allocation;
    private Date date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public SimpleModule getModule() {
        return module;
    }

    public void setModule(SimpleModule module) {
        this.module = module;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<Study> getReferences() {
        return references;
    }

    public void setReferences(List<Study> references) {
        this.references = references;
    }

    public List<SimplePerson> getExaminers() {
        return examiners;
    }

    public void setExaminers(List<SimplePerson> examiners) {
        this.examiners = examiners;
    }

    public ExamType getType() {
        return type;
    }

    public void setType(ExamType type) {
        this.type = type;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public ExamGroup getAllocation() {
        return allocation;
    }

    public void setAllocation(ExamGroup allocation) {
        this.allocation = allocation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getRooms() {
        return rooms;
    }

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }
}
