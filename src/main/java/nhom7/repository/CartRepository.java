package nhom7.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nhom7.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	public Cart findByCustomerId(Long userId);
}
