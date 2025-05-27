package com.TechM.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.net.URL;

@Entity
public class Content {
    private URL content;
    @ManyToOne
    @JoinColumn(name="section_id")
    @JsonIgnoreProperties("sectionContents")
    private Section section;

    public Content() {
    }

    public Content(URL content, Section section) {
        this.content = content;
        this.section = section;
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
