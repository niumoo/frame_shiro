package me.imniu.shiro.util;

import org.apache.log4j.chainsaw.Main;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

import me.imniu.shiro.po.User;

/**
 * Description:
 *
 * @author NiuJinpeng
 * @date   2018年1月8日上午9:04:37
 */
public class PasswordHelper {
	private RandomNumberGenerator randomNumberGenerator = 
	new SecureRandomNumberGenerator();
	
	private String algorithmName = "md5";
	
	private final int hashIterations = 2;
	
	public void encryPassword(User user){
		user.setSalt(randomNumberGenerator.nextBytes().toHex());
		String newPassword = new SimpleHash(algorithmName,user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()),
				hashIterations).toHex();
		user.setPassword(newPassword);
	}
	
	@Test
	public void test(){
		System.out.println(randomNumberGenerator.nextBytes().toHex());
	}
	
}
