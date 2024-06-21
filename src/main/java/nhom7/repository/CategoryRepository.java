package nhom7.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nhom7.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	public List<Category>findByRestaurantId(Long id);
}
