package upp.project.services;

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
	
	public Issue findUnpublishedIssueInMagazine(Magazine magazine) {
		
		return issueRepository.findByPublishedAndMagazine(false, magazine);
	}

}
