package com.TechM.Repository;

import com.TechM.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    Course findByCourseTitle(String courseTitle);
    List<Course> findByDept(String dept);
    List<Course>  findByIsActiveTrue();
    List<Course>  findByIsActiveFalse();
//    Optional<Course> searchCoursesByCourseTitle(String courseTitle);
//
//    List<Course> findByTitleContainingIgnoreCase(String courseTitle);

    
    //By Sanjay => I'm not sure about DB structure ,so i made a generic query
    @Query("SELECT c FROM Course c WHERE c.courseTitle LIKE CONCAT(:prefix, '%')")
    List<Course> findCoursesByPrefix(@Param("prefix") String prefix);
//    List<Course> findByCourseTitle1(String courseTitle);
    Optional<Course> findByCourseCode(String courseCode);


}
