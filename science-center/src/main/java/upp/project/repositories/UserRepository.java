package upp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import upp.project.model.Authority;
import upp.project.model.Magazine;
import upp.project.model.RegisteredUser;
import upp.project.model.Role;
import upp.project.model.ScientificArea;

public interface UserRepository extends JpaRepository<RegisteredUser, Long>, JpaSpecificationExecutor<RegisteredUser>{
	
	RegisteredUser findByUsername(String username);

	RegisteredUser findByEmail(String email);
	
	List<RegisteredUser> findByAuthority(Authority authority);
	
	List<RegisteredUser> findByConfirmed(Boolean confirmed);
	
	@Query("select distinct us from RegisteredUser as us inner join us.scientificAreas as scia"
	     + " inner join us.authority as au where scia in ?1 and au.role = ?2 and us.confirmed = ?3 and us != ?4")
	List<RegisteredUser> findByScientificAreas(List<ScientificArea> scientificAreas, Role role, Boolean confirmed, RegisteredUser user);
	
	@Query("select usr from Magazine as mag inner join mag.editors as usr inner join usr.scientificAreas as scia"
			+ " inner join usr.authority as au where mag = ?1 and scia = ?2 and au.role = ?3")
	List<RegisteredUser> findMagazineEditorsForScientificArea(Magazine magazine, ScientificArea scientificArea, Role role);
	
	@Query("select usr from Magazine as mag inner join mag.reviewers as usr inner join usr.scientificAreas as scia"
			+ " inner join usr.authority as au where mag = ?1 and scia = ?2 and au.role = ?3")
	List<RegisteredUser> findMagazineReviewersForScientificArea(Magazine magazine, ScientificArea scientificArea, Role role);

}
