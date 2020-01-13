package upp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import upp.project.model.ScientificArea;

public interface ScientificAreaRepository extends JpaRepository<ScientificArea, Long>, JpaSpecificationExecutor<ScientificArea>{
	
	ScientificArea findByName(String name);
}
