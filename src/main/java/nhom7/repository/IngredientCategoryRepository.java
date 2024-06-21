package nhom7.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nhom7.model.IngredientCategory;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long>{

	List<IngredientCategory> findByRestaurantId(Long id);
}

