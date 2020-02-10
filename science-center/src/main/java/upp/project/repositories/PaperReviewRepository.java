package upp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import upp.project.model.Paper;
import upp.project.model.PaperReview;

public interface PaperReviewRepository extends JpaRepository<PaperReview, Long>, JpaSpecificationExecutor<PaperReview>{

	List<PaperReview> findAllByPaper(Paper paper);
}
