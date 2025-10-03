package hu.hm.fitjourneyapi.utils;

import hu.hm.fitjourneyapi.exception.setExceptions.InvalidSetType;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.fitness.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FitnessModelDataFactory {

    public User createUser(){
        User user = User.builder()
                .name("Placeholder")
                .email("Placeholder@email.com")
                .birthday(LocalDate.of(2000, 1,1))
                .password("PlaceholderPassword")
                .weightInKg(100)
                .heightInCm(180)
                .build();
        return user;
    }

    public Workout createWorkout(User user){
        Workout workout = Workout.builder()
                .user(user)
                .name("Placeholder workout")
                .lengthInMins(10).build();
        return workout;
    }

    public Exercise createExercise(Workout workout, ExerciseTypes type){
        Exercise excercise = Exercise.builder()
                .name("Placeholder excercise")
                .description("Placeholder desc")
                .workout(workout)
                .type(type).build();
        return excercise;
    }

    public Set createSet(Exercise excercise){
        Set set = null;
        switch (excercise.getType()){
            case RESISTANCE -> set =  StrengthSet.builder().reps(10).weight(100).build();
            case NOT_GIVEN -> set = StrengthSet.builder().reps(10).weight(100).build();
            case BODYWEIGHT -> set = StrengthSet.builder().reps(10).weight(100).build();
            case CARDIO -> set =  CardioSet.builder().durationInSeconds(100).distanceInKm(0.5).build();
            case FLEXIBILITY -> set =  FlexibilitySet.builder().reps(10).build();
            default -> throw new InvalidSetType("Unkonwn type");
        }
        set.setExercise(excercise);
        return set;
    }
}
