package team.combinatorics.midtermproject.service;

import team.combinatorics.midtermproject.model.dto.UserDTO;

import java.util.List;

public interface UserService {

    Integer addNewUser(UserDTO userDTO);

    void updateUser(UserDTO userDTO);

    void deleteUserByUid(Integer uid);

    UserDTO getUserByUid(Integer uid);

    List<UserDTO> getAllUser();

}
