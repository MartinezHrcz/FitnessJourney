package hu.hm.fitjourneyapi.repository.fitnessTest;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Excercise;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.repository.testutil.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@DataJpaTest
@ComponentScan(value = "hu.hm.fitjourneyapi.repository.testutil",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class))
public class WorkoutRepositoryTest {

    @Autowired
    private TestDataFactory factory;

    @Autowired
    private WorkoutRepository workoutRepository;

    private User user;
    private Workout workout;


    @BeforeEach
    void setup() {
        user = factory.createUser();
        workout = factory.createWorkout(user);
    }

    @Test
    void testSaveWorkout(){
        assertEquals("Placeholder workout",workoutRepository.findById(workout.getId()).get().getName());
    }

    @Test
    void testFindWorkoutByName() {
        Workout workout = workoutRepository.findByName("Placeholder workout");
        assertEquals("Placeholder workout",workout.getName());
    }

    @Test
    void testUpdateWorkout(){
        workout.setName("New Name");
        Excercise excercise = factory.createExcercise(workout);

        workout.getExcercises().add(excercise);

        workoutRepository.save(workout);

        Workout updatedWorkout = workoutRepository.findById(workout.getId()).get();

        assertEquals("New Name",updatedWorkout.getName());
        assertEquals("Placeholder excercise",updatedWorkout.getExcercises().getFirst().getName());
    }

    @Test
    void testDeleteWorkout(){
        Workout deletedWorkout = workout;
        workoutRepository.delete(workout);
        assertEquals(Optional.empty(),workoutRepository.findById(deletedWorkout.getId()));
    }

}
