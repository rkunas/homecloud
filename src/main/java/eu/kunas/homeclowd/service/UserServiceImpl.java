package eu.kunas.homeclowd.service;

import eu.kunas.homeclowd.dao.UserDaoImpl;
import eu.kunas.homeclowd.dto.UserDto;
import eu.kunas.homeclowd.model.HCUserEntity;
import eu.kunas.homeclowd.utils.Crypt;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramazan on 27.04.15.
 */
@Service(value = "userService")
public class UserServiceImpl {

    @Inject
    @Named("userDao")
    private UserDaoImpl userDao;

    public boolean isAvailable(String u, String p) {
        return userDao.exists(u,p);
    }

    public HCUserEntity getUser(String username, String password){
        return userDao.getUser(username,password);
    }

    public HCUserEntity saveNewUser(HCUserEntity newUser){
        newUser.setPassword(Crypt.rehash(newUser.getPassword()));
        return userDao.save(newUser);
    }
}
