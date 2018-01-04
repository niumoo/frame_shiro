package me.imniu.shiro.test;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;

/**
 * Description:
 *		Shiro的编码  解码
 *
 * @author NiuJinpeng
 * @date   2018年1月4日上午10:58:37
 */
public class EncodeTest {
	
	/**
	 * base64
	 */
	@Test
	public void testBase64(){
		String str = "hello";
		//编码：
		String base64Str = Base64.encodeToString(str.getBytes());
		System.out.println(str+"进行base64编码:"+base64Str);
		// 解码
		String decodeStr = Base64.decodeToString(base64Str.getBytes());
		System.out.println(decodeStr+"进行base64解码 :"+decodeStr);
	}
	
	/**
	 * hex 16
	 */
	@Test
	public void testHex(){
		String str = "hello";
		//编码：
		String base64Str = Hex.encodeToString(str.getBytes());
		System.out.println(str+"进行hex编码:"+base64Str);
		// 解码
		String decodeStr = new String(Hex.decode(base64Str.getBytes()));
		System.out.println(decodeStr+"进行hex解码 :"+decodeStr);
	}
	
	/**
	 * md5
	 */
	@Test
	public void testMD5(){
		String str = "hello";
		String salt = "123";
		String md5 = new Md5Hash(str, salt).toString();//还可以转换为 toBase64()/toHex()
		System.out.println(str+"进行MD5:"+md5);
	}
	
	/**
	 * md5 *2
	 */
	@Test
	public void testMD52(){
		String str = "hello";
		String salt = "123";
		String md5 = new Md5Hash(str, salt,2).toString();//还可以转换为 toBase64()/toHex()
		System.out.println(str+"进行MD5:"+md5);
	}
	
	/**
	 * SHA256,还有如 SHA1、 SHA512 算法。
	 */
	@Test
	public void testsha1(){
		String str = "hello";
		String salt = "123";
		String sha1 = new Sha256Hash(str, salt).toString();
		System.out.println(str+"进行SHA256:"+sha1);
	}
	
	/**
	 * 通用的散列支持
	 */
	@Test
	public void testSimpleHash(){
		String str = "hello";
		String salt = "123";
		//内部使用 MessageDigest
		String simpleHash = new SimpleHash("SHA-1", str, salt).toString();
		System.out.println(str+"进行SimpleHash:"+simpleHash);
	}

}
