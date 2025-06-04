package com.TechM.Controller;

import com.TechM.DTO.ContentDTO;
import com.TechM.Model.Category;
import com.TechM.Model.Content;
import com.TechM.Model.Course;
import com.TechM.Model.Section;
import com.TechM.Repository.CategoryRepository;
import com.TechM.Repository.ContentRepository;
import com.TechM.Repository.CourseRepository;
import com.TechM.Repository.SectionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/course")
@Transactional
public class Controller {

    private CourseRepository c;
    private final CategoryRepository ca;
    private final SectionRepository sr;
    private final ContentRepository cr;
    public Controller(CourseRepository c, CategoryRepository ca, SectionRepository sr, ContentRepository cr) {
        this.c = c;
        this.ca = ca;
        this.sr=sr;
        this.cr = cr;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCourse(@RequestBody Course course) {
        if (c.findByCourseCode(course.getCourseCode()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Course already exists with course code: " + course.getCourseCode());
        }
        c.save(course);
        String category = course.getDept()  ;
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
//        System.out.println(courses.get(1).getImageUrl());
        return ResponseEntity.ok(courses); // cleaner response
    }
    @GetMapping("/detailsbyId")
    public ResponseEntity<List<Course>> getAllCoursesById(@RequestParam List<Long> id) {
        List<Course> courses = c.findAllById(id);
        return ResponseEntity.ok(courses); // cleaner response
    }
    @GetMapping("/detailsbyCourseCode")
    public ResponseEntity<Optional<Course>> getAllCoursesByCourseCode(@RequestParam String Code) {
        Optional<Course> courses = c.findByCourseCode(Code);
        return ResponseEntity.ok(courses); // cleaner response
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateCourse(@RequestBody Course course) {
        Course existingCourse = c.findById(course.getCourse_id())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        existingCourse.setCourseTitle(course.getCourseTitle());
        existingCourse.setCourseDescription(course.getCourseDescription());
        existingCourse.setImageUrl(course.getImageUrl());
        existingCourse.setCredit(course.getCredit());
        existingCourse.setDept(course.getDept());
        existingCourse.setCreatedAt(course.getCreatedAt());
        existingCourse.setDuration(course.getDuration());
        existingCourse.setIsActive(course.getIsActive());
        existingCourse.setInstructorName(course.getInstructorName());
        c.save(existingCourse);
        return ResponseEntity.ok("Course modified successfully...");
    }

    @PutMapping("/toggle/{course_id}")
    public ResponseEntity<String> toggleActivity(@PathVariable("course_id") String course_id){
        Course course = c.findById(Long.parseLong(course_id)).orElse(null);
        if(course == null){
            return new ResponseEntity<>("No course Found...",HttpStatus.OK);
        }
        course.setIsActive(!course.getIsActive());
        c.save(course);
        return new ResponseEntity<>("Course Updated", HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCourse(@RequestParam String course_id){
        System.out.println(course_id);
        c.deleteById(Long.parseLong(course_id));
        return new ResponseEntity<>("Course Deleted Successfully...",HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCourses() {
        List<Category> categories = ca.findAll();
        return ResponseEntity.ok(categories); // cleaner response
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Course>> getCourses(@RequestParam String dept) {
        List<Course> courses = c.findByDept(dept);
        return ResponseEntity.ok(courses); // cleaner response
    }
    
    //By Sanjay
    @GetMapping("/filtercourse")
    public ResponseEntity<List<Course>> getCoursesByPrefix(@RequestParam String courseTitle) {
        List<Course> courses = c.findCoursesByPrefix(courseTitle);
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
        Section existingSection = sr.findById(section.getSection_id())
                .orElseThrow(() -> new RuntimeException("Section not found"));

        existingSection.setSectionTitle(section.getSectionTitle());
        existingSection.setSectionDesc(section.getSectionDesc());
        existingSection.setCreatedAt(section.getCreatedAt());
        sr.save(existingSection);
        return ResponseEntity.ok("Section updated successfully");
    }
    @DeleteMapping("section/delete")
    public ResponseEntity<String> deleteSection(@RequestBody String section_id){
        sr.deleteById(Long.parseLong(section_id));
        return ResponseEntity.ok("Section Deleted successfully");
    }
    @DeleteMapping("section/content/delete")
    public ResponseEntity<String> deleteContent(@RequestBody String content_id){
        cr.deleteById(Long.parseLong(content_id));
        return ResponseEntity.ok("Content Deleted successfully");
    }
//    @GetMapping("section/content/details")
//    public ResponseEntity<Content> getContent(@RequestParam("id") long section_id){
//        return cr.findById(section_id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
    @GetMapping("section/content/details")
    public ResponseEntity<ContentDTO> getContent(@RequestParam("id") long section_id,
                                                 HttpServletRequest request) {
        return cr.findById(section_id)
                .map(content -> {
                    String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
                    return ResponseEntity.ok(new ContentDTO(content, baseUrl));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/count")
    public ResponseEntity<Integer> courseCount(){
        int val = c.findAll().size();
        return new ResponseEntity<>(val,HttpStatus.OK);
    }
    @GetMapping("/active")
    public ResponseEntity<List<Course>> getActiveCourses() {
        List<Course> activeCourses = c.findByIsActiveTrue();
        return ResponseEntity.ok(activeCourses);
    }
    @GetMapping("/disable")
    public ResponseEntity<List<Course>> getDisableCourses() {
        List<Course> activeCourses = c.findByIsActiveFalse();
        return ResponseEntity.ok(activeCourses);
    }
    @PostMapping("section/content/upload")
    public ResponseEntity<String> uploadPdf(
            @RequestParam("sectionId") Long sectionId,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "contentUrl", required = false) String contentUrl) {

        try {
            Section section = sr.findById(sectionId)
                    .orElseThrow(() -> new RuntimeException("Section not found"));

            // Validation: At least one of file or contentUrl must be provided
            if ((file == null || file.isEmpty()) && (contentUrl == null || contentUrl.isEmpty())) {
                return ResponseEntity.badRequest().body("Either file or content URL must be provided");
            }

            Content content = new Content();
            content.setSection(section);

            // Handle PDF file if present
            if (file != null && !file.isEmpty()) {
                content.setDocument(file.getBytes());
            }

            // Handle content URL if present
            if (contentUrl != null && !contentUrl.isEmpty()) {
                try {
                    content.setContent(new URL(contentUrl));
                } catch (MalformedURLException e) {
                    return ResponseEntity.badRequest().body("Invalid content URL");
                }
            }

            cr.save(content);
            return ResponseEntity.ok("Content uploaded successfully");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing the PDF file");
        }
    }


    @GetMapping("section/content/download/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        return cr.findById(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"document_" + id + ".pdf\"")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(content.getDocument()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null));
    }
    @GetMapping("section/content/view/{id}")
    public ResponseEntity<byte[]> viewPdf(@PathVariable Long id) {
        Content content = cr.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Content not found"));

        byte[] pdfBytes = content.getDocument(); // assuming this holds the PDF

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("document.pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
