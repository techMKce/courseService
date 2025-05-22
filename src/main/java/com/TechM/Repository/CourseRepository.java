package com.TechM.Repository;

import com.TechM.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Course findByCourseTitle(String title);
    List<Course> findByCategory(String category);
}
