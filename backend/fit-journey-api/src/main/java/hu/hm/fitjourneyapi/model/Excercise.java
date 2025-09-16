package hu.hm.fitjourneyapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Excercise {
    private int id;
    private String name;
    private String description;
    private String type;
    private ArrayList<Set> sets = new ArrayList<>();
}
