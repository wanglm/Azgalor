package org.Azgalor.framework.security.algorithm.symmetric;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.Azgalor.framework.security.Algorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * aes对称算法
 * 
 * @author ming
 *
 */
public class Aes implements Algorithm {
	private final static Logger LOG = LogManager.getLogger(Aes.class);
	private String password;
	private String code;
	private byte[] key;
	private byte[] iv = new byte[16];
	private Encoder encoder;
	private Decoder decoder;
	private Cipher cipher;
	private int mode = 20171231;

	public Aes(String password) {
		super();
		MessageDigest sha256 = null;
		try {
			sha256 = MessageDigest.getInstance("SHA-256");
			key = sha256.digest(password.getBytes("UTF-8"));
			SecureRandom sr = new SecureRandom();
			sr.nextBytes(iv);
			this.encoder = Base64.getEncoder();
			this.decoder = Base64.getDecoder();
			this.password=password;
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			LOG.error("AES init error:", e);
		}
	}

	public Aes(String password, String code) {
		super();
		MessageDigest sha256 = null;
		try {
			sha256 = MessageDigest.getInstance("SHA-256");
			key = sha256.digest(password.getBytes("UTF-8"));
			iv = Arrays.copyOf(code.getBytes("UTF-8"), 16);
			this.encoder = Base64.getEncoder();
			this.decoder = Base64.getDecoder();
			this.password=password;
			this.code=code;
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			LOG.error("AES init error:", e);
		}
	}

	@Override
	public String encrypt(String str) {
		String eStr = null;
		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		try {
			if (mode != Cipher.ENCRYPT_MODE) {
				cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
				mode = Cipher.ENCRYPT_MODE;
			}
			eStr = encoder.encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
		} catch (Exception e) {
			LOG.error("AES encrypt error:", e);
		}
		return eStr;
	}

	@Override
	public String decrypt(String str) {
		String eStr = null;
		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		try {
			if (mode != Cipher.DECRYPT_MODE) {
				cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
				cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
				mode = Cipher.DECRYPT_MODE;
			}
			eStr = new String(cipher.doFinal(decoder.decode(str)),"UTF-8");
		} catch (Exception e) {
			LOG.error("AES Decrypt error:", e);
		}
		return eStr;
	}

	@Override
	public boolean isArgsChange(String... args) {
		int len=args.length;
		boolean ischange=false;
		if(len==1){
			ischange=!password.equals(args[0]);
		}else if(len==2){
			ischange=!password.equals(args[0])||!code.equals(args[1]);
		}
		return ischange;
	}

}
