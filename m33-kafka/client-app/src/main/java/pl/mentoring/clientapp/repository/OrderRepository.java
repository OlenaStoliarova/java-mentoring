package pl.mentoring.clientapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mentoring.clientapp.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
