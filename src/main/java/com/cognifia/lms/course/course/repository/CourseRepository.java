package com.cognifia.lms.course.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<com.cognifia.lms.course.course.entity.Course,Integer> {

}
