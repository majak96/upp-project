package upp.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Issue;
import upp.project.model.Magazine;
import upp.project.repositories.IssueRepository;

@Service
public class IssueService {
	
	@Autowired
	IssueRepository issueRepository;
	
	/*public Issue findIssueByNumberInMagazine(Magazine magazine, Integer number) {
		
		return  issueRepository.findIssueByNumberInMagazine(magazine, number);
	}*/
	
	public Issue save(Issue issue) {
		
		return issueRepository.save(issue);
	}
	
	public Issue findById(Long id) {
		
		return issueRepository.getOne(id);
	}
	
	public Issue findUnpublishedIssueInMagazine(Magazine magazine) {
		
		return issueRepository.findByPublishedAndMagazine(false, magazine);
	}
	
	public List<Issue> findByMagazine(Magazine magazine){
		
		return issueRepository.findByMagazineAndPublished(magazine, true);
	}

}
