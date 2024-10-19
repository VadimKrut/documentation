package com.service.documentation.service;

import com.service.documentation.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {

    User findById(Long id);

    List<User> findAllByParams(Pageable pageable, String login);

    User findMySelf();

    User create(User user);

    User update(User user);

    void delete();

    void deleteById(Long id);
}