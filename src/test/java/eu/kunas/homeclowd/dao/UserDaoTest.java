package eu.kunas.homeclowd.dao;

import eu.kunas.homeclowd.model.HCConfigEntity;
import eu.kunas.homeclowd.model.HCUserEntity;
import eu.kunas.homeclowd.utils.SpringContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by ramazan on 11.06.15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringContext.class})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class UserDaoTest {

    private static final String TEST_PASSWORD = "TEST_PASS";
    private static final String TEST_USERNAME = "6a179ee3da3af9fc20afbc56df9e4053";

    @Inject
    @Named("userDao")
    private UserDaoImpl userDao;

    @Before
    public void prepare(){

        HCUserEntity userEntity = new HCUserEntity();
        userEntity.setUsername(TEST_USERNAME);
        userEntity.setPassword(TEST_PASSWORD);

        userDao.save(userEntity);
    }

    @Test
    public void testUserExists(){
        Boolean exists = userDao.exists(TEST_USERNAME,TEST_PASSWORD);

        Assert.assertEquals(Boolean.TRUE,exists);
    }

}
