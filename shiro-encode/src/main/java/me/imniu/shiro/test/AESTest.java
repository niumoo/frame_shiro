package me.imniu.shiro.test;

import java.security.Key;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.junit.Test;

/**
 * Description:
 *			AES加密解密算法的测试
 * @author NiuJinpeng
 * @date   2018年1月4日下午3:47:35
 */
public class AESTest {
	
	@Test
	public void testAES(){
		AesCipherService aesCipherService = new AesCipherService();
		// 设置key的长度
		aesCipherService.setKeySize(128);
		// 生成key
		Key key = aesCipherService.generateNewKey();
		
		// 加密
		String text = "hello";
		String hex = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
		System.out.println(hex);
		
		// 解密
		String string = new String(aesCipherService.decrypt(Hex.decode(hex), key.getEncoded()).getBytes());
		System.out.println(string);
		
		
	}

}
