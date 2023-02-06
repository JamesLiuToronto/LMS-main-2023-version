package com.cognifia.lms.account.model;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import com.cognifia.lms.common.domain.base.ValueObject;

@Value
@Builder
@Jacksonized
public class Person implements ValueObject {
    @NotNull String firstName;
    @NotNull String lastName;
}
