package org.github.axon.tag.user.service;

import org.github.axon.tag.user.entity.UserView;

import java.util.List;

public interface UserViewService {

    UserView save(UserView userView);

    UserView findById(Long id);

    List<UserView> findAll();

    void deleteById(Long id);

    void replay();
}
