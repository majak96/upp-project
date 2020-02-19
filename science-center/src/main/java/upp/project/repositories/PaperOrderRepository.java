package upp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import upp.project.model.OrderStatus;
import upp.project.model.Paper;
import upp.project.model.PaperOrder;
import upp.project.model.RegisteredUser;

@Repository
public interface PaperOrderRepository  extends JpaRepository<PaperOrder, Long> {

	@Query("SELECT distinct o from PaperOrder as o WHERE o.orderStatus = ?1 or o.orderStatus = ?2 ")
	  List<PaperOrder> findOrdersBasedOnOrderStatused(OrderStatus orderStatus1, OrderStatus orderStatus2);
	
	@Query("SELECT distinct mag from PaperOrder as o inner join o.buyer as buyer inner join o.papers as mag WHERE buyer = ?1")
	List<Paper> findAllMagazinesFromOrders(RegisteredUser user);
}
