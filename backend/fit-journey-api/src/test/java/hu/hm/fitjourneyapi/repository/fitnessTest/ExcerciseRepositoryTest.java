package hu.hm.fitjourneyapi.repository.fitnessTest;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Excercise;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.fitness.ExcerciseRepository;
import hu.hm.fitjourneyapi.repository.testutil.TestFitnessDataFactory;
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
public class ExcerciseRepositoryTest {

    @Autowired
    private ExcerciseRepository excerciseRepository;
    @Autowired
    private TestFitnessDataFactory factory;

    private User user;
    private Workout workout;
    private Excercise excercise;


    @BeforeEach
    void setup() {
        user = factory.createUser();
        workout = factory.createWorkout(user);
        excercise = factory.createExcercise(workout);
    }

    @Test
    void testSaveExcercise() {
        assertEquals(excercise, excerciseRepository.getReferenceById(excercise.getId()));
    }

    @Test
    void testUpdateExcercise() {
        excercise.setName("New Excercise Name");
        excercise.setDescription("New Excercise Description");
        excerciseRepository.save(excercise);
        assertEquals(excercise, excerciseRepository.getReferenceById(excercise.getId()));
    }

    @Test
    void testFindExcerciseByName() {
        assertEquals(excercise, excerciseRepository.findByName(excercise.getName()).get());
    }

    @Test
    void testDeleteExcercise() {
        excerciseRepository.deleteById(excercise.getId());
        assertEquals(Optional.empty(),excerciseRepository.findByName(excercise.getName()));
    }


}
