package eu.kunas.homeclowd.backend.rest;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Kunas on 07.08.2015.
 */
@RestController
@Component
public class InstanceFacadeImpl {
    @RequestMapping("/greeting")
    public String getHello(){
        return "Hallo";
    }
}
