package upp.project.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import upp.project.model.Magazine;
import upp.project.model.Membership;
import upp.project.model.RegisteredUser;

public interface MembershipRepository extends JpaRepository<Membership, Long>, JpaSpecificationExecutor<Membership>{

	List<Membership> findByUserAndMagazineAndValidUntilAfter(RegisteredUser user, Magazine magazine, Date validUntil);
}
