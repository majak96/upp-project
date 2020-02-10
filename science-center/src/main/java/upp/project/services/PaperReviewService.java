package upp.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import upp.project.model.Paper;
import upp.project.model.PaperReview;
import upp.project.repositories.PaperReviewRepository;

@Service
public class PaperReviewService {
	
	@Autowired
	PaperReviewRepository paperReviewRepository;

	public PaperReview save(PaperReview paperReview) {
		
		return paperReviewRepository.save(paperReview);
	}
	
	public List<PaperReview> findAllByPaper(Paper paper) {
		
		return paperReviewRepository.findAllByPaper(paper);
	}
}
