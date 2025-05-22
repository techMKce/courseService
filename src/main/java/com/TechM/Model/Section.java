package com.TechM.Model;

import java.net.URL;
import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Section {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long section_id;
	
	
	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course;
	
	private String section_title;
	private URL content;
	private int order_number;
	@Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

	public Section(long section_id, Course course, String section_title, URL content, int order_number,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.section_id = section_id;
		this.course = course;
		this.section_title = section_title;
		this.content = content;
		this.order_number = order_number;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

	public String getSection_title() {
		return section_title;
	}

	public void setSection_title(String section_title) {
		this.section_title = section_title;
	}

	public URL getContent() {
		return content;
	}

	public void setContent(URL content) {
		this.content = content;
	}

	public int getOrder_number() {
		return order_number;
	}

	public void setOrder_number(int order_number) {
		this.order_number = order_number;
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
}
