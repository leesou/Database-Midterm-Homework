package team.combinatorics.midtermproject.service;

import org.apache.catalina.User;
import team.combinatorics.midtermproject.model.dto.UserDTO;

public interface UserService {

    Integer addNewUser(UserDTO userDTO);

    void updateUser(UserDTO userDTO);

    void deleteUserByUid(Integer uid);

    UserDTO getUserByUid(Integer uid);

}
