package com.cognifia.lms.course.course.port;

import com.cognifia.lms.course.course.entity.Course;

public interface CoursePort {

    public Course createNewCourse(String categoryCode, String title,
                                  String courseTypeCode, int duaration,
                                  String description, String courseLevelCode,
                                  String image, String video, int updateUserId) ;

    public void updateCourseInfo(int courseId, String categoryCode, String title,
                                  String courseTypeCode, int duaration,
                                  String description, String courseLevelCode,
                                  String image, String video, int updateUserId) ;
    public void approveCourse();
    public void disableCourse();

}
