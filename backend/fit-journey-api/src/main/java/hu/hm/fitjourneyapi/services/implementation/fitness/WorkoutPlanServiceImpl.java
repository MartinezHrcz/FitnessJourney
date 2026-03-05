package hu.hm.fitjourneyapi.services.implementation.fitness;

import hu.hm.fitjourneyapi.dto.fitness.workoutPlan.WorkoutPlanCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workoutPlan.WorkoutPlanDTO;
import hu.hm.fitjourneyapi.exception.common.UnauthorizedException;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.exception.fitness.WorkoutNotFound;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.mapper.fitness.WorkoutPlanMapper;
import hu.hm.fitjourneyapi.model.User;
import hu.hm.fitjourneyapi.model.enums.ExerciseTypes;
import hu.hm.fitjourneyapi.model.enums.WorkoutStatus;
import hu.hm.fitjourneyapi.model.fitness.*;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.DefaultExercisesRepository;
import hu.hm.fitjourneyapi.repository.fitness.UserMadeTemplateRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutPlanRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutRepository;
import hu.hm.fitjourneyapi.services.interfaces.fitness.WorkoutPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

    private final WorkoutPlanRepository planRepository;
    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;
    private final DefaultExercisesRepository defaultRepo;
    private final UserMadeTemplateRepository userTemplateRepo;
    private final WorkoutPlanMapper planMapper;

    @Override
    @Transactional
    public WorkoutPlanDTO createPlan(WorkoutPlanCreateDTO dto, UUID userId) {
        log.debug("Creating workout plan: {}", dto.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound("User not found: " + userId));

        WorkoutPlan plan = WorkoutPlan.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .creator(user)
                .exercises(new ArrayList<>())
                .build();

        for (var exDto : dto.getExercises()) {
            var info = findTemplateInfo(exDto.getExerciseTemplateId());

            PlanExercise planEx = PlanExercise.builder()
                    .exerciseTemplateId(exDto.getExerciseTemplateId())
                    .name(info.name())
                    .type(info.type())
                    .targetSets(exDto.getTargetSets())
                    .workoutPlan(plan)
                    .build();

            plan.getExercises().add(planEx);
        }

        WorkoutPlan saved = planRepository.save(plan);
        return planMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutPlanDTO getPlanById(UUID planId) {
        return planRepository.findById(planId)
                .map(planMapper::toDTO)
                .orElseThrow(() -> new WorkoutNotFound("Plan not found: " + planId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutPlanDTO> getPlanByName(String planName) {
         List<WorkoutPlan> plans = planRepository.findWorkoutPlansByNameContainingIgnoreCase(planName);
         return planMapper.toDTOList(plans);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutPlanDTO> getAvailablePlans(UUID userId) {
        List<WorkoutPlan> plans = planRepository.findWorkoutPlansByCreator_IdOrCreator_IdIsNull(userId);
        return planMapper.toDTOList(plans);
    }

    @Override
    @Transactional
    public void deletePlan(UUID planId, UUID userId) {
        WorkoutPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new WorkoutNotFound("Plan not found"));

        if (plan.getCreator() == null || !plan.getCreator().getId().equals(userId)) {
            throw new UnauthorizedException("You do not have permission to delete this plan.");
        }

        planRepository.delete(plan);
        log.info("Deleted workout plan: {}", planId);
    }

    @Override
    @Transactional
    public UUID startWorkoutFromPlan(UUID planId, UUID userId) {
        WorkoutPlan plan = planRepository.findById(planId)
                .orElseThrow(() -> new WorkoutNotFound("Plan not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound("User not found"));

        Workout workout = Workout.builder()
                .name(plan.getName())
                .description(plan.getDescription())
                .user(user)
                .status(WorkoutStatus.ONGOING)
                .startDate(LocalDateTime.now())
                .build();

        for (PlanExercise planEx : plan.getExercises()) {
            Exercise exercise = Exercise.builder()
                    .name(planEx.getName())
                    .type(planEx.getType())
                    .workout(workout)
                    .build();

            for (int i = 0; i < planEx.getTargetSets(); i++) {
                exercise.addSet(createDefaultSetForType(planEx.getType()));
            }
            workout.getExercises().add(exercise);
        }

        return workoutRepository.save(workout).getId();
    }


    private Set createDefaultSetForType(ExerciseTypes type) {
        return switch (type) {
            case RESISTANCE, BODY_WEIGHT -> new StrengthSet();
            case CARDIO -> new CardioSet();
            case FLEXIBILITY -> new FlexibilitySet();
            default -> new StrengthSet();
        };
    }

    private TemplateInfo findTemplateInfo(UUID id) {
        log.info("Attempting to find template with ID: {}", id);
        return defaultRepo.findById(id)
                .map(e -> new TemplateInfo(e.getName(), e.getType()))
                .orElseGet(() -> userTemplateRepo.findById(id)
                        .map(e -> new TemplateInfo(e.getName(), e.getType()))
                        .orElseThrow(() -> new ExerciseNotFound("Template ID not found: " + id)));
    }

    private record TemplateInfo(String name, ExerciseTypes type) {}
}