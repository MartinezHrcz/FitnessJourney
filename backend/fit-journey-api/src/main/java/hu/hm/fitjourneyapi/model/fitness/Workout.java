package hu.hm.fitjourneyapi.model.fitness;

import hu.hm.fitjourneyapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
@AllArgsConstructor
public class Workout {
    private int id;
    private String name;
    private Date date;
    private int lengthInMins;

    private User user;
    private ArrayList<Excercise> excercises = new ArrayList<>();
}
