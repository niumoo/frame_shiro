package me.imniu.shiro.test;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Test;

/**
 * Description:
 * 		测试Shiro的hashService
 * @author NiuJinpeng
 * @date   2018年1月4日下午3:11:56
 */
public class HashServiceTest {
	
	@Test 
	public void testHashService(){
		// 默认算法SHA-512
		DefaultHashService hashService = new DefaultHashService();
		// setHashAlgorithmName用于指定算法
		hashService.setHashAlgorithmName("SHA-512");
		 //私盐，默认没有
		hashService.setPrivateSalt(new SimpleByteSource("123"));
		// 是否生成公盐，默认false
		hashService.setGeneratePublicSalt(true); 
		// 用于生成公盐，默认就是这个
		hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());
		// 设置生成Hash值的迭代次数
		hashService.setHashIterations(1);
		

		HashRequest request = new HashRequest.Builder()
			.setAlgorithmName("MD5")
			.setSource(ByteSource.Util.bytes("hello"))
			.setSalt(ByteSource.Util.bytes("123"))
			.setIterations(2)
			.build();
		
		String hex = hashService.computeHash(request).toHex();
		System.out.println(hex);
		System.out.println("------------------------------------");
		
		// SecureRandomNumberGenerator用于生成一个随机数
		SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
		randomNumberGenerator.setSeed("1234".getBytes());
		System.out.println(randomNumberGenerator.nextBytes());
		String hex2 = randomNumberGenerator.nextBytes().toHex();
		System.out.println(hex2);
	}
}
