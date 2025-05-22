package com.TechM.Model;

import jakarta.persistence.Entity;

@Entity
public class Enrollement {
    private long student_id;
    private long course_id;
    private String status;

    public Enrollement(long student_id, long course_id, String status) {
        this.student_id = student_id;
        this.course_id = course_id;
        this.status = status;
    }

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

}
