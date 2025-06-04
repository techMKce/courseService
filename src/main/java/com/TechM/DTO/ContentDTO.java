package com.TechM.DTO;

import com.TechM.Model.Content;
import lombok.Data;

import java.net.URL;

@Data
public class ContentDTO {

    private long id;
    private URL content;
    private String pdfViewUrl;

    public ContentDTO(Content c, String baseUrl) {
        this.id = c.getId();
        this.content = c.getContent();
        this.pdfViewUrl = baseUrl + "/api/v1/course/section/content/view/" + c.getId();
    }
}
