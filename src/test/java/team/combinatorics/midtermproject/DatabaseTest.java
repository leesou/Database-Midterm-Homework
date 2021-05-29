package team.combinatorics.midtermproject;

import io.swagger.models.auth.In;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import team.combinatorics.midtermproject.dao.*;
import team.combinatorics.midtermproject.model.po.*;

import java.util.List;
import java.sql.Date;

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

    @Autowired
    ServiceDao serviceDao;

    @Test
    public void userDaoTest() {
        UserPO userPO = UserPO.builder()
                            .userName("leesou")
                            .address("PKU")
                            .phoneNumber("114514")
                            .email("1919@810.com")
                            .build();
        userDao.insert(userPO);

        UserPO userPO1 = UserPO.builder().userName("gz").address("THU").phoneNumber("1919810").email("lts@xbz.com").build();
        userDao.insert(userPO1);

        List<UserPO> userPOList = userDao.selectAll();
        for(UserPO u:userPOList){
            System.out.println(u.getUid());
            System.out.println(u.getUserName());
            System.out.println(u.getAddress());
            System.out.println(u.getEmail());
            System.out.println(u.getPhoneNumber());
        }


    }

    @Test
    public void productTest(){
        /*
        Date d = Date.valueOf("2021-11-11");
        ProductPO productPO = ProductPO.builder().productName("ipad").price(3000.0).sellTime(d).duration(2).uid(3).build();
        productDao.insert(productPO);

         */

        List<ProductPO> pro = productDao.selectByUid(4);
        for(ProductPO p:pro){
            System.out.println(p);
        }
        Double sum = productDao.sumMoneyByUid(4);
        System.out.println(sum);
    }

    @Test
    public void serviceTest(){
        /*
        List<ServicePO> l = serviceDao.selectAll();
        for(ServicePO ser:l){
            serviceDao.deleteByPrimaryKey(ser.getSid());
        }

        ServicePO servicePO = ServicePO.builder().serviceType("repair").state("OK").description("hard").wid(1).pid(5).build();
        ServicePO servicePO2 = ServicePO.builder().serviceType("repair").state("OK").description("hard").wid(2).pid(5).build();
        ServicePO servicePO3 = ServicePO.builder().serviceType("repair").state("handling").description("soft").wid(1).pid(4).build();

        serviceDao.insert(servicePO);
        serviceDao.insert(servicePO2);
        serviceDao.insert(servicePO3);

         */

        List<ServicePO> user3 = serviceDao.selectByUid(3);
        if(user3.isEmpty()){
            System.out.println("3 bought nothing");
        }
        else{
            for(ServicePO ser:user3){
                System.out.println(ser);
            }
        }
        List<ServicePO> user4 = serviceDao.selectByUid(4);
        if(user4.isEmpty()){
            System.out.println("3 bought nothing");
        }
        else{
            for(ServicePO ser:user4){
                System.out.println(ser);
            }
        }

        for(int i=0;i<3;i++){
            int count_w = serviceDao.countByWid(i);
            System.out.println(count_w);
            List<ServicePO> temp = serviceDao.selectByWid(i);
            for(ServicePO ser:temp){
                System.out.println(ser);
            }
        }

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
