package edu.hm.cs.fs.common.model.simple;

import java.util.List;

import edu.hm.cs.fs.common.constant.ExamType;
import edu.hm.cs.fs.common.constant.Offer;
import edu.hm.cs.fs.common.constant.Semester;
import edu.hm.cs.fs.common.model.ModuleCode;


public class SimpleModuleCode {
  private Offer offer;
  private ExamType examType;
  private List<Semester> semesters;
  private String curriculum;

  public SimpleModuleCode() {
  }
  
  public SimpleModuleCode(final ModuleCode moduleCode) {
    offer = moduleCode.getOffer();
    examType = moduleCode.getServices();
    semesters = moduleCode.getSemesters();
    curriculum = moduleCode.getCurriculum();
  }
  
  public Offer getOffer() {
    return offer;
  }

  public void setOffer(Offer offer) {
    this.offer = offer;
  }

  public ExamType getExamType() {
    return examType;
  }

  public void setExamType(ExamType examType) {
    this.examType = examType;
  }

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
