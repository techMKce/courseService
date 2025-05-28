package com.TechM.Controller;

import com.TechM.Model.Category;
import com.TechM.Model.Course;
import com.TechM.Model.Section;
import com.TechM.Repository.CategoryRepository;
import com.TechM.Repository.CourseRepository;
import com.TechM.Repository.SectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class Controller {

    private CourseRepository c;
    private CategoryRepository ca;
    private SectionRepository sr;
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
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = c.findAll();
        return ResponseEntity.ok(courses); // cleaner response
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCourse(@RequestBody Course course) {
        c.save(course);
        return ResponseEntity.ok("Course modified successfully...");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCourse(@RequestBody String course_id){
        c.deleteById(Long.parseLong(course_id));
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
    
    //By Sanjay
    @GetMapping("/filtercourse")
    public ResponseEntity<List<Course>> getCoursesByPrefix(@RequestParam String course) {
        List<Course> courses = c.findCoursesByPrefix(course);
        return ResponseEntity.ok(courses); // clean
    }

    @PostMapping("/section/add")
    public ResponseEntity<String> addSection(@RequestBody Section section){
        sr.save(section);
        return ResponseEntity.ok("Section added successfully..!");
    }

    @GetMapping("/section/details")
    public ResponseEntity<List<Section>> getSection(@RequestParam("id") long course_id){
        Course course=c.getReferenceById(course_id);
        List<Section>  sectionlist=course.getSections();
        return ResponseEntity.ok(sectionlist);
    }
    @PutMapping("/section/update")
    public ResponseEntity<String> updateSection(@RequestBody Section section){
        sr.save(section);
        return ResponseEntity.ok("Section updated successfully");
    }
    @DeleteMapping("section/delete")
    public ResponseEntity<String> deleteSection(@RequestBody String section_id){
        sr.deleteById(Long.parseLong(section_id));
        return ResponseEntity.ok("Section Deleted successfully");
    }
    @GetMapping("/count")
    public ResponseEntity<Integer> courseCount(){
        int val = c.findAll().size();
        return new ResponseEntity<>(val,HttpStatus.OK);
    }
}
