package hu.hm.fitjourneyapi.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordUpdateDTO {
    private long id;
    private String passwordOld;
    private String passwordNew;
}
