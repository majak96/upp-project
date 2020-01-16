package upp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import upp.project.model.Authority;
import upp.project.model.RegisteredUser;

public interface UserRepository extends JpaRepository<RegisteredUser, Long>, JpaSpecificationExecutor<RegisteredUser>{
	
	RegisteredUser findByUsername(String username);

	RegisteredUser findByEmail(String email);
	
	List<RegisteredUser> findByAuthority(Authority authority);
}
