package com.example.jpashopdemo.repository;

import com.example.jpashopdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
