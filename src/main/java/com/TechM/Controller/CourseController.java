
package com.TechM.Controller;

import com.TechM.Model.Category;
import com.TechM.Model.Course;
import com.TechM.Repository.CategoryRepository;
import com.TechM.Repository.CourseRepository;
import com.TechM.Repository.InstructorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final InstructorRepository instructorRepository;
    public  CourseController(CourseRepository courseRepository, CategoryRepository categoryRepository, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.categoryRepository = categoryRepository;
        this.instructorRepository = instructorRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody Course course) {
        // Null check
        if (course.getCategory() == null || course.getCategory().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Category cannot be null or empty");
        }

        // Check if category exists
        boolean exists = courseRepository.existsByCourseTitle(course.getCourseTitle());
        if (exists) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Course already exists");

        }

        // Ensure correct repository method
        Optional<Category> optional = categoryRepository.findByCategory(course.getCategory());
        if (optional.isEmpty()) {
            Category newCategory = new Category();
            newCategory.setCategory(course.getCategory());
            categoryRepository.save(newCategory);
        }

        // Save course

        courseRepository.save(course);
        System.out.println(course.getCourseTitle()+" "+course.getCourseid());

        return ResponseEntity.ok("Course added successfully");
    }



    @GetMapping("/details")
    public ResponseEntity<List<Course>> getCourses(@RequestParam("ids") List<Long> courseIds) {
        List<Course> courses = courseRepository.findAllById(courseIds);
        return ResponseEntity.ok(courses); // cleaner response
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCourse(@RequestBody Course course) {
        Course existing = courseRepository.findByCourseTitle(course.getCourseTitle());
        if (existing == null) {
            return new ResponseEntity<>("Course not found.", HttpStatus.NOT_FOUND);
        }
        course.setCourseid(existing.getCourseid()); // preserve ID
        courseRepository.save(course);
        return ResponseEntity.ok("Course modified successfully...");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCourse(@RequestBody String title){
        Course existing = courseRepository.findByCourseTitle(title);
        courseRepository.deleteById(existing.getCourseid());
        return new ResponseEntity<>("Course Deleted Successfully...",HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCourses() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories); // cleaner response
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Course>> getCourses(@RequestParam String category) {
        List<Course> courses = courseRepository.findByCategory(category);
        return ResponseEntity.ok(courses); // cleaner response
    }

    @GetMapping("/search")
    public ResponseEntity<List<Course>> courseSearch(@RequestParam String title) {
        List<Course> list = courseRepository.findByCourseTitleContainingIgnoreCase(title);
        if(list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/isActive")
    public ResponseEntity<List<Course>> getCourseByisActive() {
        List<Course>courseList = courseRepository.findByIsactiveTrue();
        return ResponseEntity.ok(courseList);
    }

    @GetMapping("/disable")
    public ResponseEntity<List<Course>> getCourseByDisable() {
        List<Course> courseList = courseRepository.findByIsactiveFalse();
        return ResponseEntity.ok(courseList);
    }

}
