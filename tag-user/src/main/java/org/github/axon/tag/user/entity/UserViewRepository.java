package org.github.axon.tag.user.entity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserViewRepository extends MongoRepository<UserView, Long> {

}
