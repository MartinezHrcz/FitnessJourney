package hu.hm.fitjourneyapi.repository.fitnessTest;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.ExcerciseTypes;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.repository.testutil.TestFitnessDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.Optional;

@DataJpaTest
//@ComponentScan(value = "hu.hm.fitjourneyapi.repository.testutil",
//        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class))
@Import({TestFitnessDataFactory.class})
public class WorkoutRepositoryTest {

    @Autowired
    private TestFitnessDataFactory factory;

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
        assertEquals(workout,workoutRepository.findById(workout.getId()).get());
    }

    @Test
    void testFindWorkoutByName() {
        Workout workout = workoutRepository.findByName("Placeholder workout");
        assertEquals("Placeholder workout",workout.getName());
    }

    @Test
    void testUpdateWorkout(){
        workout.setName("New Name");
        Exercise excercise = factory.createExercise(workout, ExcerciseTypes.RESISTANCE);

        workout.getExercises().add(excercise);

        workoutRepository.save(workout);

        Workout updatedWorkout = workoutRepository.findById(workout.getId()).get();

        assertEquals(workout, updatedWorkout);
    }

    @Test
    void testDeleteWorkout(){
        Workout deletedWorkout = workout;
        workoutRepository.delete(workout);
        assertEquals(Optional.empty(),workoutRepository.findById(deletedWorkout.getId()));
    }

}
