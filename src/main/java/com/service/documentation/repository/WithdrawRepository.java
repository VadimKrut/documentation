package com.service.documentation.repository;

import com.service.documentation.entity.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {

    Optional<Withdraw> findByOrderId(String orderId);

    @Query(nativeQuery = true, value = """
                SELECT DISTINCT d.*
                FROM documentation.withdraw d
                WHERE d.status = 'NEW'
                  AND (now() AT TIME ZONE 'UTC' - d.date_create) > interval '2 hours'
            """)
    List<Withdraw> findAllForUpdateStatus();
}