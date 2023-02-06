package com.cognifia.lms.common.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CourseStatus {
    @Getter
    @AllArgsConstructor
    public enum TYPE {
        ACTIVE('A'), DELETED('D'), PENDING('P');

        private final char value;
    }
}
