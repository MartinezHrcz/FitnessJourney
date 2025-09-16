package hu.hm.fitjourneyapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Set {
    private int id;
    private int reps;
    private double weight;
}
