package com.cognifia.lms.course.course.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="training_Course", schema="peaktraining")
//@NamedQuery(name = "course.findByUUID", query = "select u from TransactionLog u where u.uuid = ?1 ")
public class Course implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="transactionlogid")
    Integer courseId ;

    @NotNull
    //@Size(min = 2, message="Street Name is at least 2 characters long!")
    @Column(name="uuid")
    String uuid ;

    @NotNull
    String categoryCode;
    String title;

    @NotNull
    String courseTypeCode;
    @NotNull
    int duaration;

    String description;

    @NotNull
    String courseLevelCode ;
    String image;
    String video;

    @NotNull
    String statusCode;

    @NotNull
    @Column(name="utimestamp")
    LocalDateTime utimestamp ;

    public static Builder createBuilder() {
        return new Builder();
    }

    public static class Builder {
        //String uuid = UUID.randomUUID().toString();
        String categoryCode;
        String title;
        String courseTypeCode;
        int duaration;
        String description;
        String courseLevelCode ;
        String image;
        String video;
        //String statusCode;
        //LocalDateTime utimestamp ;

        public Builder categoryCode(String categoryCode){
            this.categoryCode = categoryCode ;
            return this ;
        }

        public Builder title(String title){
            this.title = title ;
            return this ;
        }

        public Builder courseTypeCode(String courseTypeCode){
            this.courseTypeCode = courseTypeCode ;
            return this ;
        }

        public Builder duaration(int duaration){
            this.duaration = duaration ;
            return this ;
        }

        public Builder description(String description){
            this.description = description ;
            return this ;
        }

        public Builder courseLevelCode(String courseLevelCode){
            this.courseLevelCode = courseLevelCode ;
            return this ;
        }

        public Builder image(String image){
            this.image = image ;
            return this ;
        }

        public Builder video(String video){
            this.video = video;
            return this ;
        }




        public Course build() {
            return new Course(0, UUID.randomUUID().toString(),
                              categoryCode, title,
                              courseTypeCode, duaration,
                              description, courseLevelCode,
                              image, video,
                              com.cognifia.lms.common.enumtype.CourseStatus.TYPE.PENDING.name(), LocalDateTime.now());
        }
    }

    //static String APPROVE_ROLE = "COURSE_APPROVE_ROLE";
    public void approveCourse(){
        if (com.cognifia.lms.common.enumtype.CourseStatus.TYPE.valueOf(statusCode) != com.cognifia.lms.common.enumtype.CourseStatus.TYPE.PENDING)
            throw new com.cognifia.lms.common.exception.AppMessageException("STATUE_NO_PENDING");
        statusCode = com.cognifia.lms.common.enumtype.CourseStatus.TYPE.ACTIVE.name();
    }

    public void disableCourse(){
        statusCode = com.cognifia.lms.common.enumtype.CourseStatus.TYPE.DELETED.name();
    }

    public void updateCourseInfo(String categoryCode, String title,
                                 String courseTypeCode, int duaration,
                                 String description, String courseLevelCode,
                                 String image, String video){

        this.categoryCode = categoryCode ;
        this.courseTypeCode = courseTypeCode;
        this.title = title ;
        this.duaration = duaration;
        this.description  = description ;
        this.courseLevelCode = courseLevelCode ;
        this.image = image;
        this.video = video ;

    }
}
