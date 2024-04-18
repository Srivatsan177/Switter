package com.srivatsan177.switter.users.Users.repositories;

import com.srivatsan177.switter.users.Users.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Query("{username:?0}")
    Optional<User> findOneByUsername(String username);
}
