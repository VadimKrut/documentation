package com.service.documentation.repository;

import com.service.documentation.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {

    boolean existsByOrderId(String orderId);

    Optional<Deposit> findByOrderId(String orderId);

    @Query(nativeQuery = true, value = """
                SELECT DISTINCT d.*
                FROM documentation.deposit d
                WHERE d.status = 'NEW'
                  AND (now() AT TIME ZONE 'UTC' - d.date_create) > interval '2 hours'
            """)
    List<Deposit> findAllForUpdateStatus();
}