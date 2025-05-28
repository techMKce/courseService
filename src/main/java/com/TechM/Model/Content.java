package com.TechM.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.net.URL;

@Entity
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long content_id;
    private String contentType;
    private URL content;
    @ManyToOne
    @JoinColumn(name="section_id")
    @JsonIgnoreProperties("sectionContents")
    private Section section;

    public Content() {
    }

    public Content(URL content, Section section,String contentType) {
        this.content = content;
        this.section = section;
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public URL getContent() {
        return content;
    }

    public void setContent(URL content) {
        this.content = content;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}
