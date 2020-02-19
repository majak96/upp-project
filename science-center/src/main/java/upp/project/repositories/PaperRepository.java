package upp.project.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import upp.project.model.Issue;
import upp.project.model.Paper;

public interface PaperRepository extends JpaRepository<Paper, Long>, JpaSpecificationExecutor<Paper>{
	
	List<Paper> findByIssueAndActive(Issue issue, Boolean active);

}
