package hu.hm.fitjourneyapi.repository.fitnessTest;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.ExcerciseTypes;
import hu.hm.fitjourneyapi.model.fitness.Exercise;
import hu.hm.fitjourneyapi.model.fitness.Set;
import hu.hm.fitjourneyapi.model.fitness.StrengthSet;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.fitness.SetRepository;
import hu.hm.fitjourneyapi.repository.testutil.TestFitnessDataFactory;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import java.util.Optional;

@DataJpaTest
@ComponentScan(value = "hu.hm.fitjourneyapi.repository.testutil",
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Component.class))
public class SetRepositoryTest {

    @Autowired
    private TestFitnessDataFactory factory;
    @Autowired
    private SetRepository setRepository;

    private User user;
    private Workout workout;
    private Exercise exercise;
    private Set set;

    @BeforeEach
    void setup() {
        user = factory.createUser();
        workout = factory.createWorkout(user);
        exercise = factory.createExercise(workout, ExcerciseTypes.RESISTANCE);
        set = factory.createSet(exercise);

    }

    @Test
    void testSaveSet(){
        assertEquals(set, setRepository.getReferenceById(set.getId()));
    }

    @Test
    void testFindSetById(){
        assertEquals(set, setRepository.findById(set.getId()).get());
    }

    @Test
    void testUpdateSet(){
        setRepository.save(set);
        assertEquals(set, setRepository.getReferenceById(set.getId()));
    }

    @Test
    void testDeleteSet(){
        setRepository.deleteById(set.getId());
        assertEquals(Optional.empty(),setRepository.findById(set.getId()));
    }

}
