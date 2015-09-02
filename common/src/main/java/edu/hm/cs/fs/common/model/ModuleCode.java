package edu.hm.cs.fs.common.model;

import java.util.List;

import edu.hm.cs.fs.common.constant.ExamType;
import edu.hm.cs.fs.common.constant.Offer;
import edu.hm.cs.fs.common.constant.Semester;


public class ModuleCode {
    private String modul;
    //private String regulation;
    private Offer offer;
    private ExamType services;
    //private String code;
    private List<Semester> semesters;
    private String curriculum;

    public String getModul() {
        return modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

    /*
    public String getRegulation() {
        return regulation;
    }

    public void setRegulation(String regulation) {
        this.regulation = regulation;
    }
    */

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public ExamType getServices() {
        return services;
    }

    public void setServices(ExamType services) {
        this.services = services;
    }

    /*
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    */

    public List<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }
}
