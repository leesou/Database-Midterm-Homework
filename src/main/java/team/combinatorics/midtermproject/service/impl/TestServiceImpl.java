package team.combinatorics.midtermproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import team.combinatorics.midtermproject.dao.TestDao;
import team.combinatorics.midtermproject.model.dto.TestDTO;
import team.combinatorics.midtermproject.model.po.TestPO;
import team.combinatorics.midtermproject.service.TestService;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestDao testDao;

    @Override
    public TestDTO getUserByID(int id) {
        TestPO testPO = testDao.selectByPrimaryKey(id);
        return TestDTO.builder()
                            .userName(testPO.getUserName())
                            .password(testPO.getPassword())
                            .build();
    }
}
