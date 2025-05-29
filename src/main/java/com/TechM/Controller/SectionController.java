package com.TechM.Controller;

import com.TechM.Model.Course;
import com.TechM.Model.Section;
import com.TechM.Repository.CourseRepository;
import com.TechM.Repository.SectionRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course/sections")
public class SectionController {
    private CourseRepository courseRepository;
    private SectionRepository sectionRepository;

    public SectionController(CourseRepository courseRepository, SectionRepository sectionRepository) {
        this.courseRepository = courseRepository;
        this.sectionRepository = sectionRepository;
    }

    @PostMapping
    public ResponseEntity<String>addSection(@RequestBody Section section) {
        try {
            Course course = courseRepository.findByCourseTitle(section.getCourse().getCourseTitle());
            if (course == null) {
                throw new ResourceNotFoundException("Course not found with title: '" + section.getCourse().getCourseTitle() + "'");
            }

            section.setCourse(course);
            sectionRepository.save(section);
            return ResponseEntity.ok("Section added successfully!");
        } catch (ResourceNotFoundException ex) {
            // Return 404 with error message instead of logging
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Failed to find course: " + section.getCourse().getCourseTitle());
        } catch (Exception ex) {
            // Return 500 with generic error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while adding the section.");
        }
    }

    @PutMapping("/{sectionid}")
    public ResponseEntity<String> updateSection(@PathVariable Long sectionid, @RequestBody Section section) {
        Optional<Section> optionalSection = sectionRepository.findById(sectionid);
        if (optionalSection.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Section not found");
        }

        Section existingSection = optionalSection.get();

        // Load managed Course entity by courseTitle from the incoming section
        if (section.getCourse() != null) {
            String courseTitle = section.getCourse().getCourseTitle();
            Course managedCourse = courseRepository.findByCourseTitle(courseTitle);
            if (managedCourse == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Course not found with title: " + courseTitle);
            }
            existingSection.setCourse(managedCourse);
        }

        // Update other Section fields
        existingSection.setSectionTitle(section.getSectiontitle());
        existingSection.setContent(section.getContent());
        existingSection.setOrderNumber(section.getOrderNumber());

        // Save managed Section
        sectionRepository.save(existingSection);

        return ResponseEntity.ok("Section updated successfully");
    }


    @DeleteMapping("/{sectionid}")
    public ResponseEntity<String> sectionDelete(@PathVariable Long sectionid) {// required Sectionid
        Optional<Section> optional = sectionRepository.findById(sectionid);
        if(optional.isEmpty())
            return ResponseEntity.ok("sectionid is not valid :"+sectionid);

        sectionRepository.delete(optional.get());
        return ResponseEntity.ok("Section deleted successfully...");
    }

    @GetMapping("/{sectionid}")
    public ResponseEntity<?> sectionDetailsById(@PathVariable Long sectionid) {
        Optional<Section>optional = sectionRepository.findById(sectionid);
        if(optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Section not found with id: " + sectionid);
        }
        return ResponseEntity.ok(optional.get());
    }
    @GetMapping
    public ResponseEntity<?> SectionDetails() {
        List<Section> sectionList = sectionRepository.findAll();
        if (sectionList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sectionList);
    }
}
