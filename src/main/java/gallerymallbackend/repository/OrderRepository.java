package gallerymallbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gallerymallbackend.entity.Order;


public interface OrderRepository extends JpaRepository<Order, Integer> {

}

