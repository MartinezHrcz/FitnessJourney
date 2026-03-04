package hu.hm.fitjourneyapi.services.initialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.hm.fitjourneyapi.model.fitness.DefaultExercise;
import hu.hm.fitjourneyapi.model.fitness.PlanExercise;
import hu.hm.fitjourneyapi.model.fitness.WorkoutPlan;
import hu.hm.fitjourneyapi.repository.fitness.DefaultExercisesRepository;
import hu.hm.fitjourneyapi.repository.fitness.WorkoutPlanRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@DependsOn("exerciseInitializer")
public class WorkoutPlanInitializer {

    private final WorkoutPlanRepository planRepository;
    private final DefaultExercisesRepository exerciseRepository;
    private final ObjectMapper objectMapper;

    @PostConstruct
    @Transactional
    public void init() {
        if (planRepository.count() > 0)
        {
            return;
        }

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("defaultWorkoutPlans.json")) {
            if (is == null) return;

            JsonNode plansNode = objectMapper.readTree(is).get("plans");

            Map<String, DefaultExercise> exerciseLookup = exerciseRepository.findAll()
                    .stream()
                    .collect(Collectors.toMap(
                            ex -> ex.getName().toLowerCase(),
                            ex -> ex
                    ));

            List<WorkoutPlan> plansToSave = new ArrayList<>();

            for (JsonNode planNode : plansNode) {
                WorkoutPlan plan = WorkoutPlan.builder()
                        .name(planNode.get("name").asText())
                        .description(planNode.get("description").asText())
                        .creator(null)
                        .exercises(new ArrayList<>())
                        .build();

                for (JsonNode exNode : planNode.get("exercises")) {
                    String searchName = exNode.get("name").asText().toLowerCase();
                    DefaultExercise defaultEx = exerciseLookup.get(searchName);

                    if (defaultEx != null) {
                        plan.getExercises().add(PlanExercise.builder()
                                .exerciseTemplateId(defaultEx.getId())
                                .name(defaultEx.getName())
                                .type(defaultEx.getType())
                                .targetSets(exNode.get("targetSets").asInt())
                                .workoutPlan(plan)
                                .build());
                    } else {
                        log.warn("Exercise '{}' not found in cache. Skipping in plan '{}'", searchName, plan.getName());
                    }
                }
                plansToSave.add(plan);
            }

            planRepository.saveAll(plansToSave);

            log.info("Successfully seeded {} workout plans with batch processing.", plansToSave.size());

        } catch (IOException e) {
            log.error("Workout Plan initialization failed", e);
        }
    }
}