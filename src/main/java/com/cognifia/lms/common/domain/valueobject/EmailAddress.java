package com.cognifia.lms.common.domain.valueobject;

import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import com.cognifia.lms.common.domain.base.ValueObject;

@Builder
@Jacksonized
@EqualsAndHashCode
public class EmailAddress implements ValueObject {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                                                                            Pattern.CASE_INSENSITIVE);
    @Getter
    private final String email;

    public EmailAddress(@NotNull String email) {
        this.email = validate(email); // <3>
    }

    public static @NotNull String validate(@NotNull String email) {
        if (!isValid(email)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
        return email;
    }

    public static boolean isValid(@NotNull String email) {
        return VALID_EMAIL_ADDRESS_REGEX.matcher(email).find();
    }

    @Override
    public @NotNull String toString() { // <4>
        return email;
    }

}
