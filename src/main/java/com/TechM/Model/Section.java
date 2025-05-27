package com.TechM.Model;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	@JsonIgnoreProperties("sections")
	private Course course;

	private String sectionTitle;
	private String sectionDesc;

	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@OneToMany(mappedBy = "section")
	private List<Content> sectionContents;

	// Constructors
	public Section() {}

	public Section(long section_id, Course course, String sectionTitle, String sectionDesc, LocalDateTime updatedAt, LocalDateTime createdAt) {
		this.section_id = section_id;
		this.course = course;
		this.sectionTitle = sectionTitle;
		this.sectionDesc = sectionDesc;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}
}
