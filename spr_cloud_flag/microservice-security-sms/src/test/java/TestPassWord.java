import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPassWord {
    @Test//调用工具类生成加密后的密码
    public void SecuritypassWord(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passWordEncoder =  bCryptPasswordEncoder.encode("root");
        System.out.println("加密的后的密码为" + passWordEncoder);
    }

}

