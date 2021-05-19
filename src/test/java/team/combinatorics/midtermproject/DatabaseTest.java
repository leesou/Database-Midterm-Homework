package team.combinatorics.midtermproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import team.combinatorics.midtermproject.dao.ProductDao;
import team.combinatorics.midtermproject.dao.UserDao;
import team.combinatorics.midtermproject.model.po.UserPO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MidtermprojectApplication.class)
public class DatabaseTest {

    @Autowired
    UserDao userDao;

    @Autowired
    ProductDao productDao;

    @Test
    public void userDaoTest() {
        UserPO userPO = UserPO.builder()
                            .userName("leesou")
                            .address("PKU")
                            .phoneNumber("114514")
                            .email("1919@810.com")
                            .build();
        userDao.insert(userPO);
    }


}
