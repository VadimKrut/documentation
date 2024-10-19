package com.service.documentation.repository;

import com.service.documentation.entity.Appeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppealRepository extends JpaRepository<Appeal, Long> {

    boolean existsByOrderId(String orderId);

    Optional<Appeal> findByOrderId(String orderId);
}