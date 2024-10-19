package com.service.documentation.service.impl;

import com.service.documentation.entity.User;
import com.service.documentation.exception.ApiException;
import com.service.documentation.repository.PersonRepository;
import com.service.documentation.repository.RoleRepository;
import com.service.documentation.service.PersonService;
import com.service.documentation.util.CurrentUser;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        if (id == null) throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "id user is null");
        return personRepository.findById(id).orElseThrow(
                () -> new ApiException(HttpServletResponse.SC_NOT_FOUND, "User not found")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllByParams(Pageable pageable, String login) {
        return (pageable != null) ? personRepository.findAllByParams(login, pageable).getContent() : personRepository.findAllByParams(login);
    }

    @Override
    @Transactional(readOnly = true)
    public User findMySelf() {
        return personRepository.findById(CurrentUser.getCurrentUser()).orElseThrow();
    }

    @Override
    @Transactional
    public User create(User user) {
        checkUser(user);
        if (personRepository.existsByLogin(user.getLogin()))
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Поле login должно быть уникальным");
        user.setId(null);
        user.setRole(roleRepository.findById(2L).orElseThrow());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return personRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        if (user.getId() == null) throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "id user is null");
        User oldUser = personRepository.findById(user.getId()).orElseThrow(
                () -> new ApiException(HttpServletResponse.SC_BAD_REQUEST, "User not found")
        );
        if (oldUser.getRole().getId() == 1L) {
            throw new ApiException(HttpServletResponse.SC_CONFLICT, "Нельзя обновить админа");
        }
        checkUser(user);
        if (!oldUser.getLogin().equals(user.getLogin())) {
            if (personRepository.existsByLogin(user.getLogin()))
                throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Поле login должно быть уникальным");
        }
        user.setRole(roleRepository.findById(2L).orElseThrow());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return personRepository.save(user);
    }

    @Override
    @Transactional
    public void delete() {
        personRepository.deleteById(CurrentUser.getCurrentUser());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null) throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "id user is null");
        User user = personRepository.findById(id).orElseThrow(() -> new ApiException(HttpServletResponse.SC_NOT_FOUND, "User not found"));
        if (user.getRole().getId() == 1L) {
            throw new ApiException(HttpServletResponse.SC_CONFLICT, "Нельзя удалить админа");
        }
        personRepository.deleteById(id);
    }

    private void checkUser(User user) {
        if (user.getLogin() == null || user.getLogin().isEmpty())
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Поле login не должно быть пустым");
        if (user.getLogin().length() < 5 || user.getLogin().length() > 50)
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Поле login имеет диапозон от 5 до 50");
        if (user.getPassword() == null || user.getPassword().isEmpty())
            throw new ApiException(HttpServletResponse.SC_BAD_REQUEST, "Поле password не должно быть пустым");
    }
}