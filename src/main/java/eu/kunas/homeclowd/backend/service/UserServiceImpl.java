package eu.kunas.homeclowd.backend.service;

import eu.kunas.homeclowd.backend.dao.UserDaoImpl;
import eu.kunas.homeclowd.backend.util.Crypt;
import eu.kunas.homeclowd.common.model.HCUserEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by ramazan on 27.04.15.
 */
@Service(value = "userService")
public class UserServiceImpl {

    @Inject
    @Named("userDao")
    private UserDaoImpl userDao;

    public boolean isAvailable(String u, String p) {
        return userDao.exists(u, p);
    }

    public HCUserEntity getUser(String username, String password) {
        return userDao.getUser(username, password);
    }

    public HCUserEntity saveNewUser(HCUserEntity newUser) {
        newUser.setPassword(Crypt.rehash(newUser.getPassword()));
        return userDao.save(newUser);
    }

    public Boolean changePassword(String username,String enteredOldPassword, String newPassword) {

        HCUserEntity user =  getUser(username,Crypt.rehash(enteredOldPassword));

        if(user == null){
            return null;
        }

        return true;
    }
}
