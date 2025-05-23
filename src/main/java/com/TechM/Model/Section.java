package com.TechM.Model;

import java.net.URL;
import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Section {
	public Section() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long section_id;
	
	
	@ManyToOne
	@JoinColumn(name="course_id")
	private Course course;
	
	private String sectionTitle;
	private URL content;
	private int orderNumber;
	@CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

	public Section(long section_id, Course course, String sectionTitle, URL content, int orderNumber,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.section_id = section_id;
		this.course = course;
		this.sectionTitle = sectionTitle;
		this.content = content;
		this.orderNumber = orderNumber;
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
		return sectionTitle;
	}

	public void setSection_title(String section_title) {
		this.sectionTitle = section_title;
	}

	public URL getContent() {
		return content;
	}

	public void setContent(URL content) {
		this.content = content;
	}

	public int getOrder_number() {
		return orderNumber;
	}

	public void setOrder_number(int order_number) {
		this.orderNumber = order_number;
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
