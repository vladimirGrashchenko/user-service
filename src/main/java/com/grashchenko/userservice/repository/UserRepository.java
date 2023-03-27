package com.grashchenko.userservice.repository;

import com.grashchenko.userservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE (:lastName IS NULL OR u.lastName = :lastName) " +
            "AND (:firstName IS NULL OR u.firstName = :firstName) " +
            "AND (:middleName IS NULL OR u.middleName = :middleName) " +
            "AND (:mobilePhone IS NULL OR u.mobilePhone = :mobilePhone) " +
            "AND (:email IS NULL OR u.email = :email)")
    List<User> searchUsers(@Param("lastName") String lastName,
                           @Param("firstName") String firstName,
                           @Param("middleName") String middleName,
                           @Param("mobilePhone") String mobilePhone,
                           @Param("email") String email);

}
