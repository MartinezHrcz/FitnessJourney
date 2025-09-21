package hu.hm.fitjourneyapi.repository.testutil;

import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.fitness.Excercise;
import hu.hm.fitjourneyapi.model.fitness.Set;
import hu.hm.fitjourneyapi.model.fitness.Workout;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.ExcerciseRepository;
import hu.hm.fitjourneyapi.repository.fitness.SetRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TestDataFactory {


    @Autowired
    UserRepository userRepository;

    @Autowired(required = false)
    WorkoutRepository workoutRepository;

    @Autowired(required = false)
    ExcerciseRepository excerciseRepository;

    @Autowired(required = false)
    SetRepository setRepository;

    public User createUser(){
        User user = User.builder()
                .name("Placeholder")
                .email("Placeholder@email.com")
                .birthday(LocalDate.of(2000, 1,1))
                .password("PlaceholderPassword")
                .weightInKg(100)
                .heightInCm(180)
                .build();
        userRepository.save(user);
        return user;
    }

    public Workout createWorkout(User user){
        if(workoutRepository == null){
            throw new RuntimeException("workoutRepository is null");
        }

        Workout workout = Workout.builder()
                .user(user)
                .name("Placeholder workout")
                .lengthInMins(10).build();
        workoutRepository.save(workout);
        return workout;
    }

    public Excercise createExcercise(Workout workout){
        if(excerciseRepository == null){
            throw new RuntimeException("excerciseRepository is null");
        }
        Excercise excercise = Excercise.builder()
                .name("Placeholder excercise")
                .description("Placeholder desc")
                .workout(workout).build();
        excerciseRepository.save(excercise);
        return excercise;
    }

    public Set createSet(Workout excercise){
        if(setRepository == null){
            throw new RuntimeException("setRepository is null");
        }
        Set set = Set.builder()
                .reps(10)
                .weight(100)
                .build();
        setRepository.save(set);
        return set;
    }

}
