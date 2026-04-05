package com.finance.backend.service;

import java.util.List;

import com.finance.backend.entities.User;

public interface UserService {
  User createUser(User user);
  User updateUser(Integer id,User updatedUser);
  List<User> getAllUser();
}
