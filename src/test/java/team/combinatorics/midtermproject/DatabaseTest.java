package team.combinatorics.midtermproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import team.combinatorics.midtermproject.dao.DepartmentDao;
import team.combinatorics.midtermproject.dao.ProductDao;
import team.combinatorics.midtermproject.dao.UserDao;
import team.combinatorics.midtermproject.dao.WorkerDao;
import team.combinatorics.midtermproject.model.po.DepartmentPO;
import team.combinatorics.midtermproject.model.po.UserPO;
import team.combinatorics.midtermproject.model.po.WorkerPO;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MidtermprojectApplication.class)
public class DatabaseTest {

    @Autowired
    UserDao userDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    WorkerDao workerDao;

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

    @Test
    public void departmentDaoTest() {
        DepartmentPO departmentPO = DepartmentPO.builder()
                                        .departmentName("testDep")
                                        .build();
        departmentDao.insert(departmentPO);

        departmentPO.setDid(1);
        departmentPO.setDepartmentName("testDep123");
        departmentDao.update(departmentPO);

        List<DepartmentPO> departmentPOList;
        departmentPOList = departmentDao.selectAll();
        for(DepartmentPO dep:departmentPOList)
            System.out.println(dep.getDepartmentName());

        for(DepartmentPO dep:departmentPOList)
            departmentDao.deleteByPrimaryKey(dep.getDid());
    }


    @Test
    public void workerDaoTest() {
        DepartmentPO departmentPO = DepartmentPO.builder()
                .departmentName("testDep")
                .build();
        departmentDao.insert(departmentPO);

        List<DepartmentPO> departmentPOList;
        departmentPOList = departmentDao.selectAll();
        Integer did = null;
        for(DepartmentPO dep:departmentPOList)
            if(dep.getDepartmentName().equals(departmentPO.getDepartmentName()))
                did = dep.getDid();

        WorkerPO workerPO = WorkerPO.builder()
                                .workerName("leesou")
                                .salary(114.514)
                                .did(did)
                                .email("1919@8.10")
                                .phoneNumber("123456")
                                .build();
        workerDao.insert(workerPO);
        workerPO.setWid(1);
        System.out.println(workerPO.getWid());

        workerPO.setWorkerName("notmad");
        workerDao.update(workerPO);

        WorkerPO answer = workerDao.selectByPrimaryKey(workerPO.getWid());
        System.out.println(answer.getWorkerName());

        // used for test correctness
        answer.setDid(2);
        workerDao.update(answer);
    }
}
