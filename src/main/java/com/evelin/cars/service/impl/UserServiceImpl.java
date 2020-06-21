package com.evelin.cars.service.impl;

import com.evelin.cars.model.Role;
import com.evelin.cars.model.User;
import com.evelin.cars.repository.UserRepository;
import com.evelin.cars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id %d could not be found",id)));
    }

    @Override
    public User getUserByUsername(String username) {
        User userNull = new User();
//        return userRepository.findByUsername(username).orElseThrow(() ->
//                new EntityNotFoundException(String.format("User with name %s could not be found",username)));
        return userRepository.findByUsername(username).orElse(userNull);
    }

    @Override
    public User createUser(User user) {

        userRepository.findByUsername(user.getUsername()).ifPresent(u ->{
            throw new EntityExistsException(String.format("User with username '%s' already exists.", user.getUsername()));
        });
        user.setCreated(new Date());
        user.setModified(new Date());
        if(user.getRoles() == null || user.getRoles().size() == 0) {
            user.setRoles(Set.of(Role.SELLER));
        }
//        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(user.getPassword());
        user.setActive(true);
       return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {

        User updateUser = userRepository.findById(user.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id %d could not be found",user.getId())));
        return null;
    }

    @Override
    public User deleteUser(Long id) {
        User deleteUser = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id %d could not be found",id)));
        userRepository.deleteById(id);
        return deleteUser;
    }

    @Override
    public long getUsersCount() {
        return userRepository.count();
    }

    @Override
    @Transactional
    public List<User> createUsersBatch(List<User> users) {
        List<User> created = users.stream()
                .map(user -> createUser(user))
                .collect(Collectors.toList());
        return  created;
    }
}
