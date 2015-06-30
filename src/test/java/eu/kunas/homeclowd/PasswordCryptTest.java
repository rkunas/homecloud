package eu.kunas.homeclowd;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.DigestUtils;

/**
 * Created by Kunas on 25.06.2015.
 */
public class PasswordCryptTest {

    @Test
    public void cryptIt() {

        String pass = "TEST_USER";
        String s = new String(DigestUtils.md5DigestAsHex(pass.getBytes()));
        System.out.println(s);

    }
}
