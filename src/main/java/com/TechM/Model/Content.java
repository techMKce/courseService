package com.TechM.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.net.URL;

@Data
@Entity
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private URL content;
    @Lob
    private byte[] document;

    @ManyToOne
    @JoinColumn(name="section_id")
    @JsonIgnoreProperties("sectionContents")
    private Section section;

}
