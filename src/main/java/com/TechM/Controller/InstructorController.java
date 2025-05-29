package com.TechM.Controller;

import com.TechM.Model.Course;
import com.TechM.Model.Instructor;
import com.TechM.Repository.CourseRepository;
import com.TechM.Repository.InstructorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses/instructors")
public class InstructorController {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public InstructorController(CourseRepository courseRepository, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
    }

    @PostMapping
    public ResponseEntity<?> addInstructor(@RequestBody Instructor instructor) {
        instructorRepository.save(instructor);
        return ResponseEntity.ok(instructor);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Instructor>> getAllInstructors() {
        List<Instructor>list;
        list=instructorRepository.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{instructorid}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable Long instructorid) {
        Optional<Instructor> instructor = instructorRepository.findById(instructorid);
        if (instructor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(instructor.get());
    }

    @PutMapping("/{instructorid}")
    public ResponseEntity<String> updateInstructor(@PathVariable Long instructorid, @RequestBody Instructor instructor) {
        if(!instructorRepository.existsById(instructorid))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id not exist");
        instructorRepository.save(instructor);
        return ResponseEntity.ok("Instructor Updated successfully...");
    }

    @DeleteMapping("/{instructorid}")
    public ResponseEntity<String> deleteInstructor(@PathVariable Long instructorid) {
        instructorRepository.deleteById(instructorid);
        return ResponseEntity.ok("Instructor deleted successfully...");
    }

    @GetMapping("/test-instructors")
    public ResponseEntity<List<Instructor>> testInstructors() {
        return ResponseEntity.ok(instructorRepository.findInstructorByCourseCategoryNative("Programming"));
    }


    @GetMapping("/by-category")
    public ResponseEntity<List<Instructor>> getInstructorByCategory(@RequestParam String category) {
        String cleanedCategory = category.trim(); // remove spaces and newlines
        System.out.println("Received category: '" + cleanedCategory + "'");
        List<Instructor> instructorList = instructorRepository.findInstructorByCourseCategoryNative(cleanedCategory);
        return ResponseEntity.ok(instructorList);
    }



    @PutMapping("/assign/{courseid}") //
    public ResponseEntity<String> assignInstructorToCourse(@PathVariable Long courseid, @RequestBody List<Long>instructorIds) {
        Optional<Course> optional = courseRepository.findById(courseid);
        if(optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("give correct courseId");
        }
        List<Instructor> instructors = instructorRepository.findAllById(instructorIds);
        Course course = optional.get();
        course.setInstructors(instructors);
        for (Instructor instructor : instructors) {
            if (!instructor.getCourses().contains(course)) {
                instructor.getCourses().add(course);
            }
        }
        courseRepository.save(course);
        instructorRepository.saveAll(instructors);
        return ResponseEntity.ok("Assigned instructors to the course successfully...");
    }

    @PutMapping("/{instructorid}/assign-courses")
    public ResponseEntity<String> assignCoursesToInstructor(@PathVariable Long instructorid, @RequestBody List<Long>coursesId) {
        Optional<Instructor>optional = instructorRepository.findById(instructorid);
        if(optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("instructor Id not found ");
        }
        List<Course> course = courseRepository.findAllById(coursesId);
        Instructor instructor = optional.get();
        instructor.setCourses(course);
        for(Course courses:course) {
            if(!courses.getInstructors().contains(courses))
                courses.getInstructors().add(instructor);
        }
        return ResponseEntity.ok("Courses assigned successfully...");
    }

    @GetMapping("/{instructorid}/courses")
    public ResponseEntity<?> getCourseTaughtById(@PathVariable Long instructorid) {
        Optional<Instructor> instructor = instructorRepository.findById(instructorid);
        if(instructor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Instructor Id not found");
        }
        List<Course> course = instructor.get().getCourses();
        return ResponseEntity.ok(course);
    }

    @GetMapping("/course/{courseid}")
    public ResponseEntity<List<Instructor>> getInstructorsByCourseId(@PathVariable Long courseid) {
     Optional<Course> optional = courseRepository.findById(courseid);
     if(optional.isEmpty())
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
     Course course = optional.get();
     List<Instructor> instructorList = course.getInstructors();
     return ResponseEntity.ok(instructorList);
    }

}
