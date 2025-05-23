package com.TechM.Repository;

import com.TechM.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Course findByCourseTitle(String title);
    List<Course> findByCategory(String category);

    Optional<Course> searchCoursesByTitle(String title);

    List<Course> findByTitleContainingIgnoreCase(String title);
}
