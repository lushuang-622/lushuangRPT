package com.ssd.common.utils;


import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lus E-mail:***@163.com
 * @date 2020年8月18日 下午2:36:11
 * @Description: TODO 类说明
 * @version V1.0
 * @since JDK 1.8
 */
public class RSAUtils {

	private static final String PUBLICKEY = "RSAPublicKey";
	private static final String PRIVATEKEY = "RSAPrivateKey";

	/**
	 * 密钥长度(bit)
	 */
	public static final int KEY_LENGTH = 1024;

	public static final int MAX_ENCRYPT_BLOCK = 117;
	/**
	 * <p>
	 * 单次解密最大密文长度，这里仅仅指1024bit 长度密钥
	 * </p>
	 *
	 * @see #MAX_ENCRYPT_BLOCK
	 */
	public static final int MAX_DECRYPT_BLOCK = 128;

	/**
	 * 加密
	 */
	// public static final String SIGN_TYPE_RSA = "RSA";

	/**
	 * 加密算法
	 */
	public static final String ALGORITHM_RSA = "RSA";

	/**
	 * 算法/模式/填充
	 */
	public static final String CIPHER_TRANSFORMATION_RSA = "RSA/ECB/PKCS1Padding";

	/**
	 * 签名算法
	 */
	//public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	public static final String SIGN_ALGORITHMS = "MD5WithRSA";

	/** UTF-8字符集 **/
	public static final String CHARSET_UTF8 = "UTF-8";

	/** GBK字符集 **/
	public static final String CHARSET_GBK = "GBK";

	public static final String CHARSET = CHARSET_UTF8;

	/**
	 * 得到公钥
	 *
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @param charset
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String key, String charset) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(key.getBytes(charset));

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 得到私钥
	 *
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @param charset
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String key, String charset) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decodeBase64(key.getBytes(charset));

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 得到密钥字符串（经过base64编码）
	 *
	 * @return
	 */
	public static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		String s = new String(Base64.encodeBase64(keyBytes), CHARSET);
		return s;
	}

	/**
	 *
	 * 公钥加密
	 *
	 * @param content
	 *            待加密内容
	 * @param publicKey
	 *            公钥
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * @return 密文内容
	 * @throws Exception
	 */
	public static String rsaEncrypt(String content, String publicKey, String charset) throws Exception {
		try {
			PublicKey pubKey = getPublicKey(publicKey, charset);
			Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] data = StringUtils.isEmpty(charset) ? content.getBytes() : content.getBytes(charset);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = Base64.encodeBase64(out.toByteArray());
			out.close();

			return StringUtils.isEmpty(charset) ? new String(encryptedData) : new String(encryptedData, charset);
		} catch (Exception e) {
			throw new Exception("error occured in rsaEncrypt: EncryptContent = " + content + ",charset = " + charset,
					e);
		}
	}

	/**
	 *
	 * 公钥加密
	 *
	 * @param content
	 *            待加密内容
	 * @param publicKey
	 *            公钥
	 * @return 密文内容
	 * @throws Exception
	 */
	public static String rsaEncrypt(String content, String publicKey) throws Exception {
		return rsaEncrypt(content, publicKey, CHARSET);
	}

	/**
	 * 私钥解密
	 *
	 * @param content
	 *            待解密内容
	 * @param privateKey
	 *            私钥
	 * @param charset
	 *            字符集，如UTF-8, GBK, GB2312
	 * @return 明文内容
	 * @throws Exception
	 */
	public static String rsaDecrypt(String content, String privateKey, String charset) throws Exception {
		try {
			PrivateKey priKey = getPrivateKey(privateKey, charset);
			Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION_RSA);
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			byte[] encryptedData = StringUtils.isEmpty(charset) ? Base64.decodeBase64(content.getBytes())
					: Base64.decodeBase64(content.getBytes(charset));
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();

			return StringUtils.isEmpty(charset) ? new String(decryptedData) : new String(decryptedData, charset);
		} catch (Exception e) {
			throw new Exception("error occured in rsaDecrypt: EncodeContent = " + content + ",charset = " + charset, e);
		}
	}

	/**
	 * 私钥解密
	 *
	 * @param content
	 *            待解密内容
	 * @param privateKey
	 *            私钥
	 * @return 明文内容
	 * @throws Exception
	 */
	public static String rsaDecrypt(String content, String privateKey) throws Exception {
		return rsaDecrypt(content, privateKey, CHARSET);
	}

	/**
	 * 获得密钥对
	 *
	 * @Title creatKeyPair
	 * @Description TODO
	 * @return
	 * @throws NoSuchAlgorithmException
	 *             KeyPair
	 */
	public static KeyPair creatKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
		// 密钥位数
		keyPairGen.initialize(KEY_LENGTH);
		// 密钥对
		KeyPair keyPair = keyPairGen.generateKeyPair();
		return keyPair;
	}
	
	
	/**
     * @param content:签名的参数内容
     * @param privateKey：私钥
     * @return
     */
    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM_RSA);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(CHARSET));
            byte[] signed = signature.sign();
            return new BASE64Encoder().encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @param content：验证参数的内容
     * @param sign：签名
     * @param publicKey：公钥
     * @return
     */
    public static boolean verifySign(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            byte[] encodedKey = new BASE64Decoder().decodeBuffer(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes(CHARSET));
            return signature.verify(new BASE64Decoder().decodeBuffer(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

	public static Map<String, Key> initKey() {
		try {
			KeyPair keyPair = creatKeyPair();
			PublicKey publicKey = getPublicKey(new BASE64Encoder().encode(keyPair.getPublic().getEncoded()), CHARSET);
			PrivateKey privateKey = getPrivateKey(new BASE64Encoder().encode(keyPair.getPrivate().getEncoded()),
					CHARSET);
			Map<String, Key> keyMap = new HashMap<String, Key>(2);
			keyMap.put(PUBLICKEY, publicKey);
			keyMap.put(PRIVATEKEY, privateKey);
			return keyMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Key> keyMap) {
		if (keyMap != null) {
			Key key = keyMap.get(PRIVATEKEY);
			return new BASE64Encoder().encode(key.getEncoded());
		} else {
			return "";
		}
	}

	/**
	 *
	 * @param keyMap
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Key> keyMap) {
		if (keyMap != null) {
			Key key = keyMap.get(PUBLICKEY);
			return new BASE64Encoder().encode(key.getEncoded());
		} else {
			return "";
		}
	}

	public static void main(String[] args) throws Exception {
		Map<String, Key> initKey = initKey();
		String publicKeyStr = getPublicKey(initKey);
		String privateKeyStr = getPrivateKey(initKey);
		String strEncrpt = rsaEncrypt("abcd123456", publicKeyStr, "utf-8");
		System.out.println("密文===" + strEncrpt);
		String content = rsaDecrypt(strEncrpt, privateKeyStr, "utf-8");
		System.out.println("解密===" + content);
		//签名验证
		String sign = sign("a=3&c=7&h=19", privateKeyStr);
		System.out.println("签名===" + sign);
		boolean doCheckSign = verifySign("a=3&c=7&h=19", sign, publicKeyStr);
		System.out.println("验证签名结果===" + doCheckSign);
	}
}
