package com.TechM.Model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
public class Course {


	public Course() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long course_id;
	private String courseTitle;
	private String courseDescription;
	private String instructorName;
	private String dept;

	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;

	@UpdateTimestamp
    private LocalDateTime updatedAt;

	private boolean isActive;

	private int duration;

	private int credit;
    
    
    @OneToMany(mappedBy="course")
    private List<Section> Sections;

	public Course(long course_id, String courseTitle, String instructorName, String courseDescription, String dept, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isActive, int duration, int credit, List<Section> sections) {
		this.course_id = course_id;
		this.courseTitle = courseTitle;
		this.instructorName = instructorName;
		this.courseDescription = courseDescription;
		this.dept = dept;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isActive = isActive;
		this.duration = duration;
		this.credit = credit;
		Sections = sections;
	}

	public String getCategory() {
		return dept;
	}

	public void setCategory(String dept) {
		this.dept = dept;
	}

	public long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(long course_id) {
		this.course_id = course_id;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public List<Section> getSections() {
		return Sections;
	}

	public void setSections(List<Section> sections) {
		Sections = sections;
	}
}
