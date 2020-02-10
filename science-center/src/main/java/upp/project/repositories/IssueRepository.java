package upp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import upp.project.model.Issue;
import upp.project.model.Magazine;

public interface IssueRepository extends JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {

	/*@Query("select distinct iss from Magazine as mag inner join mag.issues as iss"
		     + " where mag = ?1 and iss.number = ?2")
	Issue findIssueByNumberInMagazine(Magazine magazine, Integer number);*/
	
	Issue findByPublishedAndMagazine(Boolean published, Magazine magazine);
}
