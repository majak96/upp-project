package upp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import upp.project.model.Magazine;

public interface MagazineRepository extends JpaRepository<Magazine, Long>, JpaSpecificationExecutor<Magazine>{

	List<Magazine> findByActive(Boolean active);
	
	Magazine findByISSN(String ISSN);
}
