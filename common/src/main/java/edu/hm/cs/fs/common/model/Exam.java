package edu.hm.cs.fs.common.model;

import java.util.List;


/**
 * Created by Fabio on 18.02.2015.
 */
public class Exam  {
	
	private String id;
	private String code;
	private String group;
	private String module;
	private String subtitle;
	private List<String> references;
	private List<String> examiners;
	private String type;
	private String material;
	private String allocation;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public List<String> getReferences() {
		return references;
	}

	public void setReferences(List<String> references) {
		this.references = references;
	}

	public List<String> getExaminers() {
		return examiners;
	}

	public void setExaminers(List<String> examiners) {
		this.examiners = examiners;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getAllocation() {
		return allocation;
	}

	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}
}
