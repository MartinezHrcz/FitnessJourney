package hu.hm.fitjourneyapi.dto.user;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordUpdateDTO {
    private UUID id;
    private String passwordOld;
    @NotBlank(message = "Password cannot be blank!")
    @Size(min = 8, max=40, message = "Password must be between ")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
            message = "Password must have atleast 8 characters, including uppercase, lowercase, a number and a special character."
    )
    private String passwordNew;
}
