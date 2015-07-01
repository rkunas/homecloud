package eu.kunas.homeclowd.backend.util;

import org.springframework.util.DigestUtils;

/**
 * Created by developer on 27.06.15.
 */
public class Crypt {

    public static String rehash(String rh){
        if(rh != null & !rh.isEmpty()){
            System.out.println("!!!!String is empty, that should not happen");
        }
       return new String(DigestUtils.md5DigestAsHex(rh.getBytes()));
    }
}
