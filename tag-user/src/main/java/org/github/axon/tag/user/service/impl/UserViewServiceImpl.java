package org.github.axon.tag.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.EventProcessingConfiguration;
import org.github.axon.tag.user.entity.UserView;
import org.github.axon.tag.user.entity.UserViewRepository;
import org.github.axon.tag.user.service.UserViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
public class UserViewServiceImpl implements UserViewService {

    @Autowired
    UserViewRepository repository;

    @Autowired
    private EventProcessingConfiguration epc;

    @Override
    public UserView save(UserView userView) {
        repository.save(userView);
        return userView;
    }

    @Override
    public UserView findById(Long id) {
        return repository.findById(id).orElse(null);
                         //.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<UserView> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void replay() {
        repository.deleteAll();
    }
}
