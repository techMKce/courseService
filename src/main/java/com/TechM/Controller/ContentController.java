package com.TechM.Controller;

import com.TechM.Model.Content;
import com.TechM.Model.Course;
import com.TechM.Model.Section;
import com.TechM.Repository.ContentRepository;
import com.TechM.Repository.CourseRepository;
import com.TechM.Repository.SectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/courses/{courseid}/sections/{sectionid}/contents")
public class ContentController {
    private final CourseRepository courseRepository;
    private final SectionRepository sectionRepository;
    private final ContentRepository contentRepository;

    public ContentController(CourseRepository courseRepository, SectionRepository sectionRepository, ContentRepository contentRepository) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
        this.contentRepository = contentRepository;
    }

    @PostMapping
    public ResponseEntity<String> addContent(@PathVariable Long courseid,@PathVariable Long sectionid, @RequestBody Content content) {
        Optional<Course> course = courseRepository.findById(courseid);
        if (course.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CourseId not found");
        Optional<Section> sectionoptional = sectionRepository.findById(sectionid);
        if (sectionoptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Section not found");
        Section section = sectionoptional.get();
        content.setSection(section);
        contentRepository.save(content);
        return ResponseEntity.ok("Content saved successfully");
    }

    @GetMapping
    public ResponseEntity<List<Content>> getAllContent(@PathVariable Long courseid,@PathVariable Long sectionid) {
        Optional<Course> course = courseRepository.findById(courseid);
        if (course.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        Optional<Section> sectionoptional = sectionRepository.findById(sectionid);
        if (sectionoptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        Section section = sectionoptional.get();
        List<Content> contentList = section.getContents();
        if (contentList.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return ResponseEntity.ok(contentList);
    }

    @GetMapping("/{contentid}")
    public ResponseEntity<Content> getContentById(@PathVariable Long courseid, Long sectionid, Long contentid) {
        Optional<Course> course = courseRepository.findById(courseid);
        if (course.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        Optional<Section> sectionoptional = sectionRepository.findById(sectionid);
        if (sectionoptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        Optional<Content> contentOptional = contentRepository.findById(contentid);
        if (contentOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        Content content = contentOptional.get();
        return ResponseEntity.ok(content);
    }

    @PutMapping("/{contentsid}")
    public ResponseEntity<String> update(@PathVariable Long courseid, Long sectionid, Long contentid, @RequestBody Content content) {
        Optional<Content> contentOptional = contentRepository.findById(contentid);
        if (contentOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        Content exist = contentOptional.get();
        if( !(content.getSection().getSection_id().equals(sectionid)) || !(content.getSection().getCourse().getCourseid().equals(courseid)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        exist.setUpdatedAt(content.getUpdatedAt());

        contentRepository.save(exist);
        return ResponseEntity.ok("Content updated successfully");
    }

    @DeleteMapping("/{contentsid}")
    public ResponseEntity<String> delete(@PathVariable Long contentsid) {
        Optional<Content> optional = contentRepository.findById(contentsid);
        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ContentId not found");
        contentRepository.deleteById(contentsid);
        return ResponseEntity.ok("Content deleted successfully");
    }






}
