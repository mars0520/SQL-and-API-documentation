package com.mars.demo.util.encrypt;

import com.mars.demo.base.enums.ReturnMessageType;
import com.mars.demo.base.exception.ParamExceptionHandling;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @description AES加密
 * @author xzp
 * @date 2019/10/9 4:53 下午
 **/
public class AesEncryptUtil {

	public static String KEY = "marsxiaobuasekey";

	public static String IV = "marsxiaobuxzpxzp";

	/**
	 * 加密方法
	 *
	 * @param data 要加密的数据
	 * @param key  加密key
	 * @param iv   加密iv
	 * @return 加密的结果
	 * @throws Exception
	 */
	public static String encrypt(String data, String key, String iv) {
		try {
			// "算法/模式/补码方式"NoPadding PkcsPadding
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();

			byte[] dataBytes = data.trim().getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}

			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

			SecretKeySpec keyspace = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec inspect = new IvParameterSpec(iv.getBytes());

			cipher.init(Cipher.ENCRYPT_MODE, keyspace, inspect);
			byte[] encrypted = cipher.doFinal(plaintext);

			return new Base64().encodeToString(encrypted).trim();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50007);
		}
	}

	/**
	 * 解密方法
	 *
	 * @param data 要解密的数据
	 * @param key  解密key
	 * @param iv   解密iv
	 * @return 解密的结果
	 * @throws Exception
	 */
	public static String desEncrypt(String data, String key, String iv) {
		try {
			byte[] encrypted1 = new Base64().decode(data.trim());

			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original);
			return originalString.trim();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ParamExceptionHandling(ReturnMessageType.MSG_FAIL_50007);
		}
	}

	/**
	 * 使用默认的key和iv加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data) {
		return encrypt(data, KEY, IV);
	}

	/**
	 * 使用默认的key和iv解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String desEncrypt(String data) {
		return desEncrypt(data, KEY, IV);
	}

	/**
	 * 测试
	 */
	public static void main(String args[]) throws Exception {
		String test1 = "admin@emit";
		String test = new String(test1.getBytes(), "UTF-8");
		String data = null;
		String key = KEY;
		String iv = IV;
		data = encrypt(test, key, iv);
		System.out.println("数据：" + test);
		System.out.println("加密：" + data);
		System.out.println("解密：" + desEncrypt(data, key, iv));
	}
}
