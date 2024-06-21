package nhom7.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nhom7.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
