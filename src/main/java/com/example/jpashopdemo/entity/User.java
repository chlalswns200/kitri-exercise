package com.example.jpashopdemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="\"user\"")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;

}
