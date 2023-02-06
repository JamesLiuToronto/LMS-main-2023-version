package com.cognifia.lms.course.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.cognifia.lms.common.domain.base.BaseEntity;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Course extends BaseEntity {

    Integer courseId;
    String categoryCode;
    String title;
    String courseTypeCode;
    int duration;
    String description;
    String courseLevelCode;
    String image;
    String video;
    String statusCode;
}
