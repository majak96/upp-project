package upp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import upp.project.model.Magazine;

public interface MagazineRepository extends JpaRepository<Magazine, Long>, JpaSpecificationExecutor<Magazine>{

}
