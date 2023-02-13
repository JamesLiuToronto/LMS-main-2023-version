package com.cognifia.lms.common.dto;

import lombok.Builder;
import lombok.Value;

/**
 * @author James Liu
 * @date 02/12/2023 -- 6:53 PM
 */

@Value
@Builder
public class SimpleResultDTO {

    String status ; //SUCCESS. FAIL
    String note;
}
