package com.TechM.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
public class Course  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // or GenerationType.SEQUENCE if your DB supports it
	@Column(name = "course_id", updatable = false, nullable = false)
	private Long courseid;
	@Column
	private String courseTitle;
	private String courseDescription;
	private String instructorName;
	private String category;
	private int duration;
	private boolean isactive;
	@Column(nullable = false)
	private Integer credit = 0;// default 0

	@Column(name = "imageurl", columnDefinition = "TEXT")
	private String imageUrl;

	public Course() {

	}
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setCourseid(Long courseid) {
		this.courseid = courseid;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}


	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	@CreationTimestamp
    private LocalDateTime createdAt;

	@UpdateTimestamp
    private LocalDateTime updatedAt;
    
    
    @OneToMany(mappedBy="course")
    private List<Section> Sections;

	@ManyToMany(mappedBy = "courses")
	private List<Instructor> instructors;

	// 2. Getter and Setter for instructors
	public List<Instructor> getInstructors() {
		return instructors;
	}

	public void setInstructors(List<Instructor> instructors) {
		this.instructors = instructors;
	}

	public Course(long courseid, String courseTitle, String instructorName, String courseDescription, String category, LocalDateTime createdAt, LocalDateTime updatedAt, List<Section> sections, boolean isactive, int duration, int credit, String imageUrl) {
		this.courseid = courseid;
		this.courseTitle = courseTitle;
		this.instructorName = instructorName;
		this.courseDescription = courseDescription;
		this.category = category;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		Sections = sections;
		this.courseid = courseid;
		this.isactive = isactive;
		this.duration = duration;
		this.credit = credit;
		this.imageUrl = imageUrl;
	}

	public Long getCourseid() {
		return courseid;
	}

	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
}
