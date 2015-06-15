package eu.kunas.homeclowd.dao;

import eu.kunas.homeclowd.model.HCConfigEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by ramazan on 15.05.15.
 */
@Repository("configDao")
public class ConfigDaoImpl {

    @PersistenceContext
    private EntityManager em;

    public HCConfigEntity save(HCConfigEntity e) {
        if (this.em.contains(e)) {
            this.em.persist(e);
            return e;
        } else {
            HCConfigEntity saved = this.em.merge(e);
            this.em.persist(saved);
            return saved;
        }
    }

    public List<HCConfigEntity> getAll() {
        return em.createQuery("FROM " + HCConfigEntity.class.getSimpleName() + " ").getResultList();
    }
}
