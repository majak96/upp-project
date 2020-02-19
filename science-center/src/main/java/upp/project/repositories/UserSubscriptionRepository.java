package upp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import upp.project.model.OrderStatus;
import upp.project.model.RegisteredUser;
import upp.project.model.UserSubscription;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long>, JpaSpecificationExecutor<UserSubscription>{

	List<UserSubscription> findBySubscriptionStatusIn(List<OrderStatus> statuses);
	
	List<UserSubscription> findByUser(RegisteredUser user);
}
