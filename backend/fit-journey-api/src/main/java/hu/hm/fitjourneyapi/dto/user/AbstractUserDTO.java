package hu.hm.fitjourneyapi.dto.user;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractUserDTO {
    @NotBlank(message = "Name cannot be blank!")
    @Size(min = 2, max= 75, message = "Name must be between 2 and 75 characters")
    private String name;

    @NotBlank(message = "Email cannot be blank!")
    @Email(message = "Email has to be valid")
    private String email;

    @Past(message = "Birthday must be in the past")
    private LocalDate birthday;

    @Positive(message = "Weight must be positive")
    @Max(value=500, message = "Weight must be below 500kg")
    private float weightInKg;

    @Positive(message = "Height must be positive")
    @Max(value= 300, message = "Height must be below 300cm")
    private float heightInCm;
}
