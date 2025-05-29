package com.TechM.Model;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Section {
	public Section() {
	}

	@Getter
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long section_id;
	
	
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

	@OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Content> contents;

	public List<Content> getContents() {
		return contents;
	}

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

    public void setSection_id(long section_id) {
		this.section_id = section_id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getSectiontitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}


	public URL getContent() {
		return content;
	}

	public void setContent(URL content) {
		this.content = content;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
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
