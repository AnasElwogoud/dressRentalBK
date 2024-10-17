package com.anaselwogoud.dressRental.Service.Interface;

import com.anaselwogoud.dressRental.DTO.LoginRequest;
import com.anaselwogoud.dressRental.DTO.Response;
import com.anaselwogoud.dressRental.Entity.Users;

public interface UserService {
    Response register(Users user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserBookingHistory(String userId);

    Response deleteUser(String userId);

    Response getUserById(Long userId);

    Response getMyInfo(String email);

}
