package com.TechM.Repository;

import com.TechM.Model.Content;
import com.TechM.Model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    List<Content> findBySection(Section section);

}
