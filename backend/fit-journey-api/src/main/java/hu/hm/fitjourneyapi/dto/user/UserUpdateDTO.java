package hu.hm.fitjourneyapi.dto.user;

import lombok.*;
import lombok.Builder;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
	private String name;
	private String email;
	private LocalDate birthday;
	private Float weightInKg;
	private Float heightInCm;
	private String profilePictureUrl;
	private Float preferredCalories;
}
