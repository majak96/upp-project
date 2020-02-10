package upp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import upp.project.model.CoAuthor;

public interface CoAuthorRepository extends JpaRepository<CoAuthor, Long>, JpaSpecificationExecutor<CoAuthor>{

}
