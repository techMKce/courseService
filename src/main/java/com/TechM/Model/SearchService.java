package com.TechM.Model;

import com.TechM.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    private CourseRepository courseRepository;
    public List<Course> searchitems(String query) {
        return courseRepository.findByCourseTitleIgnoringCaseSensitive(query);

    }
}
