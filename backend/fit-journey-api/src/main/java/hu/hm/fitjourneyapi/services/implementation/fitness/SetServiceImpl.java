package hu.hm.fitjourneyapi.services.implementation.fitness;

import hu.hm.fitjourneyapi.dto.fitness.set.AbstractSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.CardioSetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.FlexibilitySetDTO;
import hu.hm.fitjourneyapi.dto.fitness.set.StrengthSetDTO;
import hu.hm.fitjourneyapi.exception.fitness.ExerciseNotFound;
import hu.hm.fitjourneyapi.exception.fitness.SetNotFound;
import hu.hm.fitjourneyapi.exception.fitness.setExceptions.InvalidSetType;
import hu.hm.fitjourneyapi.mapper.fitness.SetMapper;
import hu.hm.fitjourneyapi.model.fitness.*;
import hu.hm.fitjourneyapi.repository.UserRepository;
import hu.hm.fitjourneyapi.repository.fitness.*;
import hu.hm.fitjourneyapi.services.interfaces.fitness.ExerciseService;
import hu.hm.fitjourneyapi.services.interfaces.fitness.SetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
        return setMapper.toDto(set,set.getExercise());
    }

    @Transactional(readOnly = true)
    @Override
    public List<AbstractSetDTO> getSets() {
        log.debug("Fetching all sets");
        List<Set> sets = setRepository.findAll();
        log.debug("Fetched all sets");
        return sets.stream().map(
                set-> setMapper.toDto(set, set.getExercise())
        ).collect(Collectors.toList());
    }

    @Override
    public AbstractSetDTO createSet(AbstractSetDTO setDTO) {
        Exercise exercise = exerciseRepository.findById(setDTO.getExerciseId()).orElseThrow(
                ()-> new ExerciseNotFound("No exercise found with id " + setDTO.getExerciseId())
        );
        Set set = setMapper.toEntity(setDTO, exercise);
        set = setRepository.save(set);
        return setMapper.toDto(set,set.getExercise());
    }

    @Override
    public AbstractSetDTO updateSet(long id, AbstractSetDTO abstractSetDTO) {
        Set set =  setRepository.findById(id).orElseThrow(
                () -> new SetNotFound("Set with id " + id + " not found")
        );

        Set update = setMapper.toEntity(abstractSetDTO, set.getExercise());

        if(!set.getClass().equals(update.getClass())){
            throw new InvalidSetType("Unsupported set type: " + set.getClass().getName());
        }

        BeanUtils.copyProperties(set,update);

        set = setRepository.save(set);
        log.info("Updated set with id: {}", id);
        return setMapper.toDto(set,set.getExercise());
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
