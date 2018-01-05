package me.imniu.shiro.test;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

/**
 * Description:
 *
 * @author NiuJinpeng
 * @date   2018年1月4日下午4:17:38
 */
public class PasswordTest extends BaseTest {
	
	
	/**
	 * 验证加密
	 */
	@Test
    public void testPasswordServiceWithMyRealm() {
        login("classpath:shiro-passwordservice.ini", "wu", "123");
    }
	
	/**
	 * 使用数据库验证
	 */
    @Test
    public void testPasswordServiceWithJdbcRealm() {
        login("classpath:shiro-jdbc-passwordservice.ini", "wu", "123");
        System.out.println(new DefaultPasswordService().encryptPassword("123"));
    }
	
    @Test
    public void testGeneratePassword() {
        String algorithmName = "md5";
        String username = "liu";
        String password = "123";
        String salt1 = username;
        String salt2 = new SecureRandomNumberGenerator().nextBytes().toHex();
        int hashIterations = 2;

        /**
         * 此处我们使用 MD5 算法， “密码+盐（用户名 +随机数） ” 的方式生成散列值
         */
        SimpleHash hash = new SimpleHash(algorithmName, password, salt1 + salt2, hashIterations);
        String encodedPassword = hash.toHex();
        System.out.println(salt2);
        System.out.println(encodedPassword);
    }
	

}	
