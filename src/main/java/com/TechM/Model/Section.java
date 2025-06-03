package com.TechM.Model;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import jakarta.persistence.*;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Section {



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long section_id;

	@ManyToOne
	@JoinColumn(name = "course_id")
	@JsonBackReference
//	@JsonIgnore
	private Course course;

	private String sectionTitle;
	private String sectionDesc;

	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Content> sectionContents;

	public Section() {}

	public Section(long section_id, Course course, String sectionTitle, String sectionDesc, LocalDateTime updatedAt, LocalDateTime createdAt) {
		this.section_id = section_id;
		this.course = course;
		this.sectionTitle = sectionTitle;
		this.sectionDesc = sectionDesc;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}

	public long getSection_id() {
		return section_id;
	}

	public void setSection_id(long section_id) {
		this.section_id = section_id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public String getSectionDesc() {
		return sectionDesc;
	}

	public void setSectionDesc(String sectionDesc) {
		this.sectionDesc = sectionDesc;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Content> getSectionContents() {
		return sectionContents;
	}

	public void setSectionContents(List<Content> sectionContents) {
		this.sectionContents = sectionContents;
	}
}
