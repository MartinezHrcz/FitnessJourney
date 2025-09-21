package hu.hm.fitjourneyapi.repository.fitnessTest;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.SetRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
public class SetRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private SetRepository setRepository;


}
