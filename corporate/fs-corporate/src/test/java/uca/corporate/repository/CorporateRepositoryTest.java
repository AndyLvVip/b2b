package uca.corporate.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uca.corporate.domain.Corporate;
import uca.corporate.fs.repository.CorporateRepository;

/**
 * Created by Andy Lv on 2019/5/19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CorporateRepositoryTest {

    @Autowired
    CorporateRepository corporateRepository;

    @Test
    public void insert() {
        Corporate corporate = Corporate.newInstance("Unit Test");
        corporateRepository.insert(corporate, "UT");
    }
}
