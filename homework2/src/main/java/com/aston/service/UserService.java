package com.aston.service;

import com.aston.dto.UserRequest;
import com.aston.dto.UserResponse;
import java.util.List;

public interface UserService {

    UserResponse create(UserRequest request);

    UserResponse getById(Long id);

    List<UserResponse> getAll();

    UserResponse update(Long id, UserRequest request);

    void delete(Long id);
}