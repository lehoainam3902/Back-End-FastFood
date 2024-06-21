package nhom7.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nhom7.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
