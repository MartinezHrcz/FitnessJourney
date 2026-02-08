package hu.hm.fitjourneyapi.repository.diet;

import hu.hm.fitjourneyapi.model.diet.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FoodItemRepository extends JpaRepository<FoodItem, UUID> {
    List<FoodItem> findByNameContainingIgnoreCase(String name);
}
