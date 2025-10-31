package hu.hm.fitjourneyapi.services.implementation.fitness;

import hu.hm.fitjourneyapi.dto.fitness.set.AbstractSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.exception.fitness.SetNotFound;
import hu.hm.fitjourneyapi.mapper.fitness.SetMapper;
import hu.hm.fitjourneyapi.model.fitness.*;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.*;
import hu.hm.fitjourneyapi.services.interfaces.fitness.SetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SetServiceImpl implements SetService {

    private final SetRepository setRepository;
    private final StrengthSetRepository strengthSetRepository;
    private final CardioSetRepository cardioSetRepository;
    private final FlexibilitySetRepository flexibilitySetRepository;
    private final ExerciseRepository exerciseRepository;
    private final SetMapper setMapper;

    public SetServiceImpl(SetRepository setRepository, StrengthSetRepository strengthSetRepository, CardioSetRepository cardioSetRepository, FlexibilitySetRepository flexibilitySetRepository, ExerciseRepository exerciseRepository, SetMapper setMapper, UserRepository userRepository) {
        this.setRepository = setRepository;
        this.strengthSetRepository = strengthSetRepository;
        this.cardioSetRepository = cardioSetRepository;
        this.flexibilitySetRepository = flexibilitySetRepository;
        this.exerciseRepository = exerciseRepository;
        this.setMapper = setMapper;
    }


    @Transactional(readOnly = true)
    @Override
    public AbstractSetDTO getSetBySetId(long id) {
        log.debug("Fetching set by id {}", id);
        Set set = setRepository.findById(id).orElseThrow(
                () -> new SetNotFound("Set with id " + id + " not found")
        );
        log.debug("Fetched exercise by id {}", id);
        return switch (set.getExercise().getType()){
            case RESISTANCE, NOT_GIVEN, BODY_WEIGHT -> setMapper.toStrengthSetDTO((StrengthSet) set) ;
            case CARDIO -> setMapper.toCardioSetDTO((CardioSet) set);
            case FLEXIBILITY -> setMapper.toFlexibilitySetDTO((FlexibilitySet) set);
        };
    }

    @Transactional(readOnly = true)
    @Override
    public List<AbstractSetDTO> getSets() {
        log.debug("Fetching all sets");
        List<Set> sets = setRepository.findAll();
        log.debug("Fetched all sets");
        return sets.stream().map(
                set-> {
                    switch (set.getExercise().getType()){
                        case RESISTANCE, NOT_GIVEN, BODY_WEIGHT -> {
                            return setMapper.toStrengthSetDTO((StrengthSet) set) ;
                        }
                        case CARDIO -> {
                            return setMapper.toCardioSetDTO((CardioSet) set);
                        }
                        case FLEXIBILITY -> {
                            return setMapper.toFlexibilitySetDTO((FlexibilitySet) set);
                        }
                        default -> throw new SetNotFound("Set with type " + set.getExercise().getType() + " not found");
                    }
                }
        ).collect(Collectors.toList());
    }


    @Transactional
    @Override
    public StrengthSetDTO createStrengthSet(StrengthSetDTO strengthSetDTO) {
        log.debug("Creating strength set with id: {}", strengthSetDTO.getId());
        Exercise exercise = exerciseRepository.findById(strengthSetDTO.getExerciseId()).orElseThrow(
                ()-> new ExerciseNotFound("Exercise with id " + strengthSetDTO.getExerciseId() + " not found")
        );
        StrengthSet set = setMapper.toStrengthSet(strengthSetDTO, exercise);
        setRepository.save(set);
        log.info("Created strength set with id: {}", strengthSetDTO.getId());
        return setMapper.toStrengthSetDTO(set);
    }

    @Transactional
    @Override
    public FlexibilitySetDTO createFlexibilitySet(FlexibilitySetDTO flexibilitySet) {
        log.debug("Creating strength set with id: {}", flexibilitySet.getId());
        Exercise exercise = exerciseRepository.findById(flexibilitySet.getExerciseId()).orElseThrow(
                ()-> new ExerciseNotFound("Exercise with id " + flexibilitySet.getExerciseId() + " not found")
        );
        FlexibilitySet set = setMapper.toFlexibilitySet(flexibilitySet, exercise);
        setRepository.save(set);
        log.info("Created strength set with id: {}", flexibilitySet.getId());
        return setMapper.toFlexibilitySetDTO(set);
    }

    @Transactional
    @Override
    public CardioSetDTO createCardioSet(CardioSetDTO cardioSet) {
        log.debug("Creating cardio set");
        Exercise exercise = exerciseRepository.findById(cardioSet.getExerciseId()).orElseThrow(
                ()-> new ExerciseNotFound("Exercise with id " + cardioSet.getExerciseId() + " not found")
        );
        CardioSet set = setMapper.toCardioSet(cardioSet, exercise);
        setRepository.save(set);
        log.info("Created strength set with id: {}", set.getId());
        return setMapper.toCardioSetDTO(set);
    }

    @Transactional
    @Override
    public StrengthSetDTO updateStrengthSet(StrengthSetDTO strengthSetDTO) {
        log.debug("Updating strength set with id: {}", strengthSetDTO.getId());
        StrengthSet set = strengthSetRepository.findById(strengthSetDTO.getId()).orElseThrow(
                () -> new SetNotFound("Set with id " + strengthSetDTO.getId() + " not found")
        );
        set.setReps(strengthSetDTO.getReps());
        set.setWeight(strengthSetDTO.getWeight());
        strengthSetRepository.save(set);
        log.info("Updated strength set with id: {}", set.getId());
        return setMapper.toStrengthSetDTO(set);
    }

    @Transactional
    @Override
    public FlexibilitySetDTO updateFlexibilitySet(FlexibilitySetDTO flexibilitySet) {
        log.debug("Updating flexibility set with id: {}", flexibilitySet.getId());
        FlexibilitySet set = flexibilitySetRepository.findById(flexibilitySet.getId()).orElseThrow(
                () -> new SetNotFound("Set with id " + flexibilitySet.getId() + " not found")
        );
        set.setReps(flexibilitySet.getReps());
        flexibilitySetRepository.save(set);
        log.info("Updated flexibility set with id: {}", set.getId());
        return setMapper.toFlexibilitySetDTO(set);
    }

    @Transactional
    @Override
    public CardioSetDTO updateCardioSet(CardioSetDTO cardioSet) {
        log.debug("Updating cardio set with id: {}", cardioSet.getId());
        CardioSet set = cardioSetRepository.findById(cardioSet.getId()).orElseThrow(
                () -> new SetNotFound("Set with id " + cardioSet.getId() + " not found")
        );

        set.setDistanceInKm(cardioSet.getDistanceInKilometers());
        set.setDurationInSeconds(cardioSet.getDurationInSeconds());
        cardioSetRepository.save(set);
        log.info("Updated cardio set with id: {}", set.getId());
        return setMapper.toCardioSetDTO(set);
    }

    @Transactional
    @Override
    public void deleteSetById(long id) {
        log.debug("Deleting set with id: {}", id);
        Set set = setRepository.findById(id).orElseThrow(
                () -> new SetNotFound("Set with id " + id + " not found")
        );
        log.info("Deleted set with id: {}", set.getId());
        setRepository.delete(set);
    }
}
