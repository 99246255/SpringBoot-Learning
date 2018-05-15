package com.example.service;


import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {


    @Autowired
    UserRepository userRepository;

    public List<User> findByName(String name){
        return userRepository.findByName(name);
    }

    public Page<User> findAll(PageRequest pageable){
        return userRepository.findAll(pageable);
    }
}
