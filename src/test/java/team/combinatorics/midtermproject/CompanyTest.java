package team.combinatorics.midtermproject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import team.combinatorics.midtermproject.dao.DepartmentDao;
import team.combinatorics.midtermproject.dao.EmployDao;
import team.combinatorics.midtermproject.dao.ManageDao;
import team.combinatorics.midtermproject.dao.WorkerDao;
import team.combinatorics.midtermproject.model.po.DepartmentPO;
import team.combinatorics.midtermproject.model.po.EmployPO;
import team.combinatorics.midtermproject.model.po.ManagePO;
import team.combinatorics.midtermproject.model.po.WorkerPO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MidtermprojectApplication.class)
public class CompanyTest {
    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    WorkerDao workerDao;

    @Autowired
    ManageDao manageDao;

    @Autowired
    EmployDao employDao;

    @Test
    public void departmentTest() {
        // 测试添加department
        // did由数据库自动维护
        DepartmentPO departmentPO = DepartmentPO.builder().
                                        departmentName("dep1").
                                        build();
        int num1 = departmentDao.insert(departmentPO);
        // 检查主码填充情况
        System.out.println(departmentPO.getDid());
        System.out.println(num1);

        // 测试不会插入同名department
        int num2 = departmentDao.insert(departmentPO);
        System.out.println(num2);

        // 测试修改department名称
        departmentPO.setDepartmentName("changeddep1");
        departmentDao.update(departmentPO);

        // 测试删除单个department
        departmentDao.deleteByPrimaryKey(departmentPO.getDid());

    }

    @Test
    public void workerTest() {
        // 先添加一个部门
        DepartmentPO departmentPO = DepartmentPO.builder().
                departmentName("dep1").
                build();
        departmentDao.insert(departmentPO);

        // 测试添加worker
        WorkerPO workerPO1 = WorkerPO.builder().
                                workerName("worker1").
                                salary(114.514).
                                did(departmentPO.getDid()).
                                phoneNumber("1919810").
                                email("1919@810.com").
                                build();
        workerDao.insert(workerPO1);

        // 如果did不存在，worker添加不进去
        WorkerPO workerPO2 = WorkerPO.builder().
                                workerName("worker2").
                                salary(114.514).
                                did(233).
                                phoneNumber("1919811").
                                email("1919@811.com").
                                build();
        workerDao.insert(workerPO2);

        // 更新非did的信息
        // 测试动态sql
        WorkerPO newWorker1 = WorkerPO.builder().
                                wid(workerPO1.getWid()).
                                workerName("changed_worker1").
                                salary(124.514).
                                build();
        workerDao.update(newWorker1);

        // 如果更新一个不存在的did，会失败
        WorkerPO new1Worker1 = WorkerPO.builder().
                                    wid(workerPO1.getWid()).
                                    did(2).
                                    build();
        workerDao.update(new1Worker1);

        // did存在，会成功
        DepartmentPO departmentPO2 = DepartmentPO.builder().
                did(2).
                departmentName("dep2").
                build();
        departmentDao.insert(departmentPO2);
        workerDao.update(new1Worker1);

        // 测试根据wid删除worker
        workerDao.deleteByPrimaryKey(workerPO1.getWid());

        // 测试根据did删除worker
        // 情景：解散一个部门时，员工信息随之删除
        // 根据前边的测试，此时worker表应该为空
        WorkerPO workerPO3 = WorkerPO.builder().
                workerName("worker3").
                salary(114.514).
                did(1).
                phoneNumber("1919812").
                email("1919@812.com").
                build();
        workerDao.insert(workerPO3);
        WorkerPO workerPO4 = WorkerPO.builder().
                workerName("worker4").
                salary(114.514).
                did(1).
                phoneNumber("1919813").
                email("1919@813.com").
                build();
        workerDao.insert(workerPO4);
        WorkerPO workerPO5 = WorkerPO.builder().
                workerName("worker5").
                salary(114.514).
                did(2).
                phoneNumber("1919814").
                email("1919@814.com").
                build();
        workerDao.insert(workerPO5);
        Integer del_did = 1;
        workerDao.deleteByDid(del_did);
        departmentDao.deleteByPrimaryKey(del_did);


    }

    // 对manager的测试要考虑多个表的联动：department，worker & manage，employ
    @Test
    public void managerTest() {
        // 先添加三个部门
        DepartmentPO departmentPO1 = DepartmentPO.builder().
                departmentName("dep1").
                build();
        departmentDao.insert(departmentPO1);
        DepartmentPO departmentPO2 = DepartmentPO.builder().
                departmentName("dep2").
                build();
        departmentDao.insert(departmentPO2);
        DepartmentPO departmentPO3 = DepartmentPO.builder().
                departmentName("dep3").
                build();
        departmentDao.insert(departmentPO3);

        // 添加两个经理
        // 先添加worker表，再添加manage表
        WorkerPO workerPO1 = WorkerPO.builder().
                workerName("manager1").
                salary(114.514).
                did(departmentPO1.getDid()).
                phoneNumber("1919811").
                email("1919@811.com").
                build();
        workerDao.insert(workerPO1);
        ManagePO managePO1 = ManagePO.builder().
                did(workerPO1.getDid()).
                managerWid(workerPO1.getWid()).
                build();
        manageDao.insertManager(managePO1);
        WorkerPO workerPO2 = WorkerPO.builder().
                workerName("manager2").
                salary(114.514).
                did(departmentPO2.getDid()).
                phoneNumber("1919812").
                email("1919@812.com").
                build();
        workerDao.insert(workerPO2);
        ManagePO managePO2 = ManagePO.builder().
                did(workerPO2.getDid()).
                managerWid(workerPO2.getWid()).
                build();
        manageDao.insertManager(managePO2);

        // 测试有经理时，添加worker的触发器
        WorkerPO workerPO3 = WorkerPO.builder().
                workerName("worker1").
                salary(114.514).
                did(departmentPO1.getDid()).
                phoneNumber("1919813").
                email("1919@813.com").
                build();
        workerDao.insert(workerPO3);

        // 测试修改did后修改employ
        WorkerPO changeWorker1 = WorkerPO.builder().
                wid(workerPO3.getWid()).
                did(departmentPO2.getDid()).
                build();
        workerDao.update(changeWorker1);
        ManagePO newManager = manageDao.selectByDid(changeWorker1.getDid());
        EmployPO newEmploy = EmployPO.builder().
                employeeWid(changeWorker1.getWid()).
                employerWid(newManager.getManagerWid()).
                build();
        employDao.updateByEmployee(newEmploy);

        // 测试删除worker后删除employ的触发器
        workerDao.deleteByPrimaryKey(workerPO3.getWid());

        // 插入3个dep1员工，3个dep2员工，3个dep3员工

        // manager1调到dep2
        // manage insert

        // manager2调到dep3

        // 删除manager1：要删employ，manage，然后再删worker

        // 删除dep3

    }

    // 测试查询正确性
    // 以上三个Test用于测试增删改的正确性
    @Test
    public void selectTest() {

    }

}
