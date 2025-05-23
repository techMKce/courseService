package com.TechM.Controller;

import com.TechM.Model.Category;
import com.TechM.Model.Course;
import com.TechM.Model.Section;
import com.TechM.Repository.CategoryRepository;
import com.TechM.Repository.CourseRepository;
import com.TechM.Repository.SectionRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
public class Controller {

    private final CourseRepository c;
    private final CategoryRepository ca;
    private final SectionRepository sr;
    public Controller(CourseRepository c,CategoryRepository ca,SectionRepository sr) {
        this.c = c;
        this.ca = ca;
        this.sr=sr;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody Course course) {
        c.save(course);
        String category = course.getCategory();
        System.out.println(category);
        System.out.println(ca.findById(category));
        if(!ca.findById(category).isPresent()){
            ca.save(new Category(category));
        }
        return ResponseEntity.ok("Course added successfully");
    }

    @GetMapping("/details")
    public ResponseEntity<List<Course>> getCourses(@RequestParam("ids") List<Long> courseIds) {
        List<Course> courses = c.findAllById(courseIds);
        return ResponseEntity.ok(courses); // cleaner response
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCourse(@RequestBody Course course) {
        Course existing = c.findByCourseTitle(course.getCourseTitle());
        if (existing == null) {
                return new ResponseEntity<>("Course not found.", HttpStatus.NOT_FOUND);
        }
        course.setCourse_id(existing.getCourse_id()); // preserve ID
        c.save(course);
        return ResponseEntity.ok("Course modified successfully...");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCourse(@RequestBody String title){
        Course existing = c.findByCourseTitle(title);
        c.deleteById(existing.getCourse_id());
        return new ResponseEntity<>("Course Deleted Successfully...",HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCourses() {
        List<Category> categories = ca.findAll();
        return ResponseEntity.ok(categories); // cleaner response
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Course>> getCourses(@RequestParam String category) {
        List<Course> courses = c.findByCategory(category);
        return ResponseEntity.ok(courses); // cleaner response
    }
    @PostMapping("/section/add")
    public ResponseEntity<String> addSection(@RequestBody Section section) {
        try {
            Course course = c.findByCourseTitle(section.getCourse().getCourseTitle());
            if (course == null) {
                throw new ResourceNotFoundException("Course not found with title: '" + section.getCourse().getCourseTitle() + "'");
            }

            section.setCourse(course);
            sr.save(section);
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
    @PutMapping("/section/update")
    public ResponseEntity<String> updateSection(@RequestBody Section section) {//if there is no
        sr.save(section);
        return ResponseEntity.ok("section updated successfully ...");
    }
    @DeleteMapping("/section/delete")
    public ResponseEntity<String> sectionDelete(@RequestBody Section section) {// required Sectionid
        Optional<Section> optional = sr.findById(section.getSection_id());
        if(optional.isEmpty())
            return ResponseEntity.ok("sectionid is not valid :"+section.getSection_id());

        sr.delete(optional.get());
        return ResponseEntity.ok("Section deleted successfully...");
    }
     @GetMapping("/section/details{id}")
    public ResponseEntity<?> sectionDetailsById(@PathVariable Long id) {
        Optional<Section>optional = sr.findById(id);
        if(optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Section not found with id: " + id);
        }
         return ResponseEntity.ok(optional.get());
     }
     @GetMapping("/section/details")
    public ResponseEntity<?> SectionDetails() {
        List<Section> sectionList = sr.findAll();
        if(sectionList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
         return ResponseEntity.ok(sectionList);
     }
     @GetMapping("/course/search")
    public ResponseEntity<List<Course>> courseSearch(@RequestParam String title) {
        List<Course> list = c.findByTitleContainingIgnoreCase(title);
        if(list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(list);
      }

}
