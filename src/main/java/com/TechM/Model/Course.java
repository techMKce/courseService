package com.TechM.Model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;


@Entity
public class Course {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long course_id;
	private String course_title;
	private String course_description;
	private String instructor_name;
	private String Category;
	
	
	@Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;
    
    
    @OneToMany(mappedBy="course")
    private List<Section> Sections;


	public Course(long course_id, String title, String description, String instructor_name, LocalDateTime createdAt,
				  LocalDateTime updatedAt, List<Section> sections,String Category) {
		super();
		this.course_id = course_id;
		this.course_title = title;
		this.course_description = description;
		this.instructor_name = instructor_name;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		Sections = sections;
		this.Category = Category;
	}


	public long getCourse_id() {
		return course_id;
	}


	public void setCourse_id(long course_id) {
		this.course_id = course_id;
	}


	public String getTitle() {
		return course_title;
	}


	public void setTitle(String title) {
		this.course_title = title;
	}


	public String getDescription() {
		return course_description;
	}


	public void setDescription(String description) {
		this.course_description = description;
	}


	public String getInstructor_name() {
		return instructor_name;
	}


	public void setInstructor_name(String instructor_name) {
		this.instructor_name = instructor_name;
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


	public List<Section> getSections() {
		return Sections;
	}


	public void setSections(List<Section> sections) {
		Sections = sections;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}
}
