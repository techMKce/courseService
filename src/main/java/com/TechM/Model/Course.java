package com.TechM.Model;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
public class Course {




	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long course_id;
	private String courseCode;
	private String courseTitle;
	@Column(columnDefinition = "TEXT")
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


	@Column(name = "imageUrl", columnDefinition="TEXT")
	private String imageUrl;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
    private List<Section> Sections;

	public Course() {

	}

	public Course(long course_id, String courseCode, String courseTitle, String courseDescription, String instructorName, String dept, LocalDateTime createdAt, LocalDateTime updatedAt, boolean isActive, int duration, int credit, String imageUrl, List<Section> sections) {
		this.course_id = course_id;
		this.courseCode = courseCode;
		this.courseTitle = courseTitle;
		this.courseDescription = courseDescription;
		this.instructorName = instructorName;
		this.dept = dept;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isActive = isActive;
		this.duration = duration;
		this.credit = credit;
		this.imageUrl = imageUrl;
		Sections = sections;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public long getCourse_id() {
		return course_id;
	}

	public void setCourse_id(long course_id) {
		this.course_id = course_id;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
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

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean active) {
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
	@JsonIgnore
	public List<Section> getSections() {
		return Sections;
	}

	public void setSections(List<Section> sections) {
		Sections = sections;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
