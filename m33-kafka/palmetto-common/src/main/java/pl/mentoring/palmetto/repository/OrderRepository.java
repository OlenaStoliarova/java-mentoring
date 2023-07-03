package pl.mentoring.palmetto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mentoring.palmetto.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
