package hu.hm.fitjourneyapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private Date birthday;
    private String password;
    private String weight;
    private String height;
    private String role;
    private ArrayList<Workout> workouts = new ArrayList<>();
}
