package org.Azgalor.framework.security.algorithm.asymmetric;

import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;

import org.Azgalor.framework.security.Algorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * RSA不对称算法
 * 
 * @author ming
 *
 */
public class Rsa implements Algorithm {
	private final static Logger LOG = LogManager.getLogger(Rsa.class);
	private String keyDir;
	private String privateKeyFile;
	private String publicKeyFile;
	private Path privateKeyPath;
	private Path publicKeyPath;
	private Encoder encoder;
	private Decoder decoder;
	private Cipher cipher;
	private int mode = 20171231;

	public Rsa(String keyDir, String privateKeyFile, String publicKeyFile) {
		super();
		FileSystem fs = FileSystems.getDefault();
		this.keyDir = keyDir;
		this.privateKeyFile = privateKeyFile;
		this.publicKeyFile = publicKeyFile;
		this.privateKeyPath = fs.getPath(keyDir, privateKeyFile);
		this.publicKeyPath = fs.getPath(keyDir, publicKeyFile);
		this.encoder = Base64.getEncoder();
		this.decoder = Base64.getDecoder();
	}

	public String getKeyDir() {
		return this.keyDir;
	}

	public boolean createKeys() {
		boolean isSuccess = false;
		try (OutputStream priStream = Files.newOutputStream(privateKeyPath);
				OutputStream pubStream = Files.newOutputStream(publicKeyPath)) {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(2048);
			KeyPair pair = keyGen.generateKeyPair();
			PrivateKey privatekey = pair.getPrivate();
			PublicKey publickey = pair.getPublic();
			priStream.write(privatekey.getEncoded());
			pubStream.write(publickey.getEncoded());
			isSuccess = true;
		} catch (Exception e) {
			LOG.error("RSA createKeys error : ", e);
		}
		return isSuccess;
	}

	public PrivateKey getPrivateKey() throws Exception {
		byte[] keyBytes = Files.readAllBytes(privateKeyPath);
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	public PublicKey getPublicKey() throws Exception {
		byte[] keyBytes = Files.readAllBytes(publicKeyPath);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	@Override
	public String encrypt(String str) {
		String eStr = null;
		try {
			if (mode != Cipher.ENCRYPT_MODE) {
				cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey());
				mode = Cipher.ENCRYPT_MODE;
			}
			eStr = encoder.encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
		} catch (Exception e) {
			LOG.error("RSA encrypt error : ", e);
		}
		return eStr;
	}

	@Override
	public String decrypt(String str) {
		String dStr = null;
		try {
			if (mode != Cipher.DECRYPT_MODE) {
				cipher = Cipher.getInstance("RSA");
				cipher.init(Cipher.DECRYPT_MODE, getPublicKey());
				mode = Cipher.DECRYPT_MODE;
			}
			dStr = new String(cipher.doFinal(decoder.decode(str)));
		} catch (Exception e) {
			LOG.error("RSA decrypt error : ", e);
		}
		return dStr;
	}

	@Override
	public boolean isArgsChange(String... args) {
		return !keyDir.equals(args[0]) && privateKeyFile.equals(args[1]) && publicKeyFile.equals(args[2]);
	}

}
