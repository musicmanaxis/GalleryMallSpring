package gallerymallbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gallerymallbackend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByMemberIdOrderByIdDesc(int memberId);
}

