package com.TechM.Controller;

import com.TechM.Model.Category;
import com.TechM.Model.Course;
import com.TechM.Repository.CategoryRepository;
import com.TechM.Repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class Controller {

    private CourseRepository c;
    private CategoryRepository ca;

    public Controller(CourseRepository c,CategoryRepository ca) {
        this.c = c;
        this.ca = ca;
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
}
