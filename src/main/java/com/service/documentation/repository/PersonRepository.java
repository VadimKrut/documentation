package com.service.documentation.repository;

import com.service.documentation.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    boolean existsByLogin(String login);

    @Query(nativeQuery = true, value = """
            select distinct b.* from documentation.user b where (b.del = false) and ((:login IS NULL) OR (LOWER(b.login) LIKE CONCAT('%', LOWER(:login), '%')))
            """)
    List<User> findAllByParams(String login);

    @Query(nativeQuery = true, value = """
            select distinct b.* from documentation.user b where (b.del = false) and ((:login IS NULL) OR (LOWER(b.login) LIKE CONCAT('%', LOWER(:login), '%')))
            """)
    Page<User> findAllByParams(String login, Pageable pageable);
}