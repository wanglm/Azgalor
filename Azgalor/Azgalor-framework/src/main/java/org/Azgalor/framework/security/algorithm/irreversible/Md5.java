package org.Azgalor.framework.security.algorithm.irreversible;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

import org.Azgalor.framework.security.Algorithm;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Md5 implements Algorithm {
	private final static Logger LOG = LogManager.getLogger(Md5.class);
	private MessageDigest md5;

	public Md5() {
		try {
			this.md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			LOG.error("MD5 init error : ", e);
		}
	}

	@Override
	public String encrypt(String str) {
		String eStr=null;
		Path file= Paths.get(str);
		if(!Files.exists(file)){
			LOG.error("MD5 encrypt parameter error : it's not a name of file!!");
			return null;
		}
		try {
			md5.update(Files.readAllBytes(file));
			eStr=Hex.encodeHexString(md5.digest());
		} catch (Exception e) {
			LOG.error("MD5 encrypt error : ", e);
		}
		return eStr;
	}

	@Override
	public String decrypt(String str) {
		return null;
	}

	@Override
	public boolean isArgsChange(String... args) {
		return false;
	}

}
