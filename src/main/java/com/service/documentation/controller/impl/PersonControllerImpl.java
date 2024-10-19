package com.service.documentation.controller.impl;

import com.service.documentation.controller.PersonController;
import com.service.documentation.entity.User;
import com.service.documentation.enums.SortDirection;
import com.service.documentation.service.PersonService;
import com.service.documentation.util.PageableUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PersonControllerImpl implements PersonController {

    private final PersonService personService;

    @Override
    public User findById(Long id) {
        return personService.findById(id);
    }

    @Override
    public List<User> findAllByParams(Integer pageNum, Integer pageSize, String sortField, SortDirection sortDirection, String login) {
        return personService.findAllByParams(PageableUtil.generatePageable(pageNum, pageSize, sortField, sortDirection), login);
    }

    @Override
    public User findMySelf() {
        return personService.findMySelf();
    }

    @Override
    public User create(User user) {
        return personService.create(user);
    }

    @Override
    public User update(User user) {
        return personService.update(user);
    }

    @Override
    public void delete() {
        personService.delete();
    }

    @Override
    public void deleteById(Long id) {
        personService.deleteById(id);
    }
}