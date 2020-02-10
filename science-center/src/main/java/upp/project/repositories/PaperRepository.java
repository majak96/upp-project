package upp.project.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import upp.project.model.Paper;

public interface PaperRepository extends JpaRepository<Paper, Long>, JpaSpecificationExecutor<Paper>{

}
