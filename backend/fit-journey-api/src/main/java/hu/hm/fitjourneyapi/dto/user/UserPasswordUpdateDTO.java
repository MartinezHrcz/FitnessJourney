package hu.hm.fitjourneyapi.dto.user;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordUpdateDTO {
    private long id;
    private String passwordOld;
    private String passwordNew;
}
