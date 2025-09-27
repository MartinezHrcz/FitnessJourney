package hu.hm.fitjourneyapi.repository.fitnessTest;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.model.fitness.Set;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.fitness.ExerciseRepository;
import hu.hm.fitjourneyapi.repository.testutil.TestFitnessDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@DataJpaTest
@Import({TestFitnessDataFactory.class})
public class ExcerciseRepositoryTest {

    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private TestFitnessDataFactory factory;

    private User user;
    private Workout workout;
    private Exercise exercise;
    private Set set;


    @BeforeEach
    void setup() {
        user = factory.createUser();
        workout = factory.createWorkout(user);
        exercise = factory.createExercise(workout, ExerciseTypes.RESISTANCE);
    }

    @Test
    void testSaveExercise() {
        assertEquals(exercise, exerciseRepository.getReferenceById(exercise.getId()));
    }

    @Test
    void testUpdateExercise() {
        exercise.setName("New Excercise Name");
        exercise.setDescription("New Excercise Description");
        set = factory.createSet(exercise);
        exercise.addSet(set);
        exerciseRepository.save(exercise);
        assertEquals(exercise, exerciseRepository.getReferenceById(exercise.getId()));
    }

    @Test
    void testFindExcerciseByName() {
        assertEquals(exercise, exerciseRepository.findByName(exercise.getName()).get());
    }

    @Test
    void testDeleteExcercise() {
        exerciseRepository.deleteById(exercise.getId());
        assertEquals(Optional.empty(),exerciseRepository.findByName(exercise.getName()));
    }


}
