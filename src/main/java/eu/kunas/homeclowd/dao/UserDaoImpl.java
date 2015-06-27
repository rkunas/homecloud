package eu.kunas.homeclowd.dao;

import eu.kunas.homeclowd.model.HCUserEntity;
import eu.kunas.homeclowd.utils.Crypt;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by ramazan on 06.05.15.
 */
@Repository("userDao")
public class UserDaoImpl {

    @PersistenceContext
    public EntityManager em;

    @Transactional
    public HCUserEntity save(HCUserEntity e) {
        if (this.em.contains(e)) {
            this.em.persist(e);
            return e;
        } else {
            HCUserEntity saved = this.em.merge(e);
            this.em.persist(saved);
            return saved;
        }
    }

    public HCUserEntity getUser(String username, String password ){

        HCUserEntity userEntity = null;
        try {

            userEntity = (HCUserEntity) em.createQuery("FROM " + HCUserEntity.class.getSimpleName() + " hcuser " +
                    " WHERE hcuser.username = :uName AND hcuser.password = :uPass")

                    .setParameter("uName", username)
                    .setParameter("uPass", Crypt.rehash(password)).getSingleResult();

            return userEntity;

        } catch (NoResultException exc) {
            exc.printStackTrace();
            return userEntity;
        }

    }

    public Boolean exists(String username, String password) {

        HCUserEntity userEntity = null;

        try {

            userEntity = (HCUserEntity) em.createQuery("FROM " + HCUserEntity.class.getSimpleName() + " hcuser " +
                    " WHERE hcuser.username = :uName AND hcuser.password = :uPass")

            .setParameter("uName", username)
            .setParameter("uPass", Crypt.rehash(password)).getSingleResult();

            if (userEntity != null) {
                return Boolean.TRUE;
            }

        } catch (NoResultException exc) {
            exc.printStackTrace();
        }

        return Boolean.FALSE;
    }
}
