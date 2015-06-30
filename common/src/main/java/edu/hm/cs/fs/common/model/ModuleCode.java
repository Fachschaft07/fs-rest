package edu.hm.cs.fs.common.model;

import java.util.List;


public class ModuleCode  {
	
	private String modul;
	private String regulation;
	private String offer;
	private String services;
	private String code;
	private List<String> semesters;
	private String curriculum;

	public String getModul() {
		return modul;
	}

	public void setModul(String modul) {
		this.modul = modul;
	}

	public String getRegulation() {
		return regulation;
	}

	public void setRegulation(String regulation) {
		this.regulation = regulation;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<String> getSemesters() {
		return semesters;
	}

	public void setSemesters(List<String> semesters) {
		this.semesters = semesters;
	}

	public String getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(String curriculum) {
		this.curriculum = curriculum;
	}
}
