package com.evelin.cars.service.impl;

import com.evelin.cars.model.Role;
import com.evelin.cars.model.User;
import com.evelin.cars.repository.RoleRepository;
import com.evelin.cars.repository.UserRepository;
import com.evelin.cars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id %d could not be found", id)));
    }

    @Override
    public User getUserByUsername(String username) {
        User userNull = new User();
        return userRepository.findByUsername(username).orElse(userNull);
    }

    @Override
    public User createUser(User user) {

//        Role dbRole = roleRepository.findByRole(user.getRole().getRole());
//        user.setRole(dbRole);
        userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new EntityExistsException(String.format("User with username '%s' already exists.", user.getUsername()));
        });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {

        User updateUser = userRepository.findById(user.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id %d could not be found", user.getId())));
        return userRepository.save(user);
    }

    @Override
    public User deleteUser(Long id) {
        User deleteUser = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id %d could not be found", id)));
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
        return created;
    }
}