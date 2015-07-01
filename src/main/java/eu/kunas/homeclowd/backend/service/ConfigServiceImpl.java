package eu.kunas.homeclowd.backend.service;

import eu.kunas.homeclowd.backend.dao.ConfigDaoImpl;
import eu.kunas.homeclowd.common.model.HCConfigEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ramazan on 15.05.15.
 */
@Service(value = "configService")
public class ConfigServiceImpl {

    @Inject
    @Named("configDao")
    private ConfigDaoImpl configDao;

    public List<HCConfigEntity> getAll() {
        return configDao.getAll();
    }

    public HashMap<String, HCConfigEntity> getAllHashMap() {
        HashMap<String, HCConfigEntity> map = new HashMap<>();
        for (HCConfigEntity e : configDao.getAll()) {
            map.put(e.getKey(), e);
        }
        return map;
    }

    @Transactional
    public HCConfigEntity save(HCConfigEntity save){
        return configDao.save(save);
    }


}
