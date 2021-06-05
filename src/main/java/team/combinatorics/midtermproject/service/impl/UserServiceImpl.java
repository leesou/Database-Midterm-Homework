package team.combinatorics.midtermproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.combinatorics.midtermproject.dao.ProductDao;
import team.combinatorics.midtermproject.dao.ServiceDao;
import team.combinatorics.midtermproject.dao.UserDao;
import team.combinatorics.midtermproject.exception.ErrorInfoEnum;
import team.combinatorics.midtermproject.exception.KnownException;
import team.combinatorics.midtermproject.model.dto.UserDTO;
import team.combinatorics.midtermproject.model.po.UserPO;
import team.combinatorics.midtermproject.service.UserService;

import java.util.List;
import java.util.Vector;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final ProductDao productDao;
    private final ServiceDao serviceDao;

    // 添加用户
    @Override
    public Integer addNewUser(UserDTO userDTO) {
        System.out.println("[添加用户]用户昵称为："+userDTO.getUserName());
        UserPO userPO = UserPO.builder().
                            userName(userDTO.getUserName()).
                            address(userDTO.getAddress()).
                            phoneNumber(userDTO.getPhoneNumber()).
                            email(userDTO.getEmail()).
                            build();
        int num = userDao.insert(userPO);
        if(num<1 || userPO.getUid()==null)
            throw new KnownException(ErrorInfoEnum.USER_INSERT_ERROR);
        return userPO.getUid();
    }

    // 更新用户信息
    @Override
    public void updateUser(UserDTO userDTO) {
        System.out.println("[更新用户信息]用户uid为："+userDTO.getUid().toString());
        if(userDTO.getUid()==null)
            throw new KnownException(ErrorInfoEnum.USER_UPDATE_ERROR);

        UserPO userPO = UserPO.builder().
                uid(userDTO.getUid()).
                userName(userDTO.getUserName()).
                address(userDTO.getAddress()).
                phoneNumber(userDTO.getPhoneNumber()).
                email(userDTO.getEmail()).
                build();
        int num = userDao.update(userPO);
        if(num<1)
            throw new KnownException(ErrorInfoEnum.USER_UPDATE_ERROR);
    }

    // 删除用户
    // 同时删除用户的service和product
    @Override
    @Transactional
    public void deleteUserByUid(Integer uid) {
        System.out.println("[删除用户]用户id为："+uid.toString());

        int num_service = serviceDao.deleteByUid(uid);
        System.out.println("[删除用户]删除售后服务次数："+num_service);

        int num_product = productDao.deleteByUid(uid);
        System.out.println("[删除用户]删除购买产品数"+num_product);

        int num = userDao.deleteByPrimaryKey(uid);
        if(num<1)
            throw new KnownException(ErrorInfoEnum.USER_DELETE_ERROR);
    }

    // 根据uid查询用户信息
    @Override
    public UserDTO getUserByUid(Integer uid) {
        System.out.println("[查询用户信息]用户id为："+uid.toString());
        UserPO userPO = userDao.selectByPrimaryKey(uid);
        if(userPO==null)
            throw new KnownException(ErrorInfoEnum.USER_SELECT_ERROR);

        return UserDTO.builder().
                    uid(userPO.getUid()).
                    userName(userPO.getUserName()).
                    address(userPO.getAddress()).
                    phoneNumber(userPO.getPhoneNumber()).
                    email(userPO.getEmail()).
                    build();
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<UserPO> userPOList = userDao.selectAll();
        List<UserDTO> userDTOList = new Vector<>();
        for(UserPO userPO:userPOList) {
            UserDTO userDTO = UserDTO.builder().
                    uid(userPO.getUid()).
                    userName(userPO.getUserName()).
                    address(userPO.getAddress()).
                    phoneNumber(userPO.getPhoneNumber()).
                    email(userPO.getEmail()).
                    build();
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

}
