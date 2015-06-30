package edu.hm.cs.fs.common.model;

import java.util.List;


/**
 * Created by Fabio on 18.02.2015.
 */
public class Module  {
	
	private String id;
	private String name;
	private int credits;
	private int sws;
	private String responsible;
	private List<String> teachers;
	private List<String> languages;
	private String teachingForm;
	private String expenditure;
	private String requirements;
	private String goals;
	private String content;
	private String media;
	private String literature;
	private String program;
	private List<ModuleCode> modulCodes;

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

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public List<String> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<String> teachers) {
		this.teachers = teachers;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public String getTeachingForm() {
		return teachingForm;
	}

	public void setTeachingForm(String teachingForm) {
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

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public List<ModuleCode> getModulCodes() {
		return modulCodes;
	}

	public void setModulCodes(List<ModuleCode> modulCodes) {
		this.modulCodes = modulCodes;
	}
}
