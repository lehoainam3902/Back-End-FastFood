package nhom7.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nhom7.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmail(String username);
	
}
