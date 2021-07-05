package org.github.axon.tag.user.entity;

import org.github.axon.tag.user.entity.UserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserViewRepository extends JpaRepository<UserView, Long> {

}
