package hu.hm.fitjourneyapi.repository.testutil;

import hu.hm.fitjourneyapi.exception.fitness.setExceptions.InvalidSetType;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.enums.WeightType;
import hu.hm.fitjourneyapi.model.enums.WorkoutStatus;
import hu.hm.fitjourneyapi.model.fitness.*;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.ExerciseRepository;
import hu.hm.fitjourneyapi.repository.fitness.SetRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Component
public class TestFitnessDataFactory {
    @Autowired
    UserRepository userRepository;

    @Autowired(required = false)
    WorkoutRepository workoutRepository;

    @Autowired(required = false)
    ExerciseRepository exerciseRepository;

    @Autowired(required = false)
    SetRepository setRepository;

    public User createUser(){
        User user = User.builder()
                .name("Placeholder")
                .email("Placeholder@email.com")
                .birthday(LocalDate.of(2000, 1,1))
                .password("PlaceholderPassword")
                .creation_date(LocalDateTime.now())
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
                .name("Test workout")
                .description("test")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .status(WorkoutStatus.FINISHED)
                .user(user)
                .exercises(new ArrayList<>())
                .build();

        workoutRepository.save(workout);
        return workout;
    }

    public Exercise createExercise(Workout workout, ExerciseTypes type){
        if(exerciseRepository == null){
            throw new RuntimeException("excerciseRepository is null");
        }
        Exercise excercise = Exercise.builder()
                .name("Placeholder excercise")
                .description("Placeholder desc")
                .workout(workout)
                .type(type)
                .weightType(WeightType.FREE_WEIGHT)
                .sets(new ArrayList<>())
                .build();
        exerciseRepository.save(excercise);
        return excercise;
    }

    public Set createSet(Exercise excercise){
        if(setRepository == null){
            throw new RuntimeException("setRepository is null");
        }
        Set set;
        switch (excercise.getType()){
            case RESISTANCE, NOT_GIVEN, BODY_WEIGHT -> set =  StrengthSet.builder().reps(10).weight(100).build();
            case CARDIO -> set =  CardioSet.builder().durationInSeconds(100).distanceInKm(0.5).build();
            case FLEXIBILITY -> set =  FlexibilitySet.builder().reps(10).build();
            default -> throw new InvalidSetType("Unkonwn type");
        }
        set.setExercise(excercise);
        setRepository.save(set);
        return set;
    }

}
