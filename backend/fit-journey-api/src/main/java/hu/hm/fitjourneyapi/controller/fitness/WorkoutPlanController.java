package hu.hm.fitjourneyapi.controller.fitness;

import hu.hm.fitjourneyapi.dto.fitness.workoutPlan.WorkoutPlanCreateDTO;
import hu.hm.fitjourneyapi.dto.fitness.workoutPlan.WorkoutPlanDTO;
import hu.hm.fitjourneyapi.exception.common.UnauthorizedException;
import hu.hm.fitjourneyapi.exception.fitness.WorkoutNotFound;
import hu.hm.fitjourneyapi.exception.userExceptions.UserNotFound;
import hu.hm.fitjourneyapi.services.interfaces.fitness.WorkoutPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workout-plan")
@RequiredArgsConstructor
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    @PostMapping
    public ResponseEntity<WorkoutPlanDTO> createPlan(@RequestBody WorkoutPlanCreateDTO dto, Authentication authentication) {
        UUID currentUserId = UUID.fromString(authentication.getName());
        try {
            return new ResponseEntity<>(workoutPlanService.createPlan(dto, currentUserId), HttpStatus.CREATED);
        } catch (UserNotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlanDTO> getPlanById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(workoutPlanService.getPlanById(id));
        } catch (WorkoutNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<WorkoutPlanDTO>> getPlanByName(@RequestParam String name) {
        return ResponseEntity.ok(workoutPlanService.getPlanByName(name));
    }

    @GetMapping("/available")
    public ResponseEntity<List<WorkoutPlanDTO>> getAvailablePlans(Authentication authentication) {
        UUID currentUserId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(workoutPlanService.getAvailablePlans(currentUserId));
    }

    @DeleteMapping("/{id}/user/{userId}")
    public ResponseEntity<Void> deletePlan(@PathVariable UUID id, Authentication authentication) {
        UUID currentUserId = UUID.fromString(authentication.getName());
        try {
            workoutPlanService.deletePlan(id, currentUserId);
            return ResponseEntity.noContent().build();
        } catch (WorkoutNotFound ex) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/{planId}/start/{userId}")
    public ResponseEntity<UUID> startWorkoutFromPlan(@PathVariable UUID planId, Authentication authentication) {
        UUID currentUserId = UUID.fromString(authentication.getName());
        try {
            return ResponseEntity.ok(workoutPlanService.startWorkoutFromPlan(planId, currentUserId));
        } catch (WorkoutNotFound | UserNotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }
}