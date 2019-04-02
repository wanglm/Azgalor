package org.Azgalor.framework.security.algorithm.irreversible;



import org.Azgalor.framework.security.Algorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.jcajce.provider.digest.SHA3.DigestSHA3;
import org.bouncycastle.util.encoders.Hex;

/**SHA-3不可逆算法
 * @author ming
 *
 */
public class Sha3 implements Algorithm {
	private final static Logger LOG = LogManager.getLogger(Sha3.class);
	private DigestSHA3 digest;
	public Sha3(){
		try {
			digest=new DigestSHA3(256);
		} catch (Exception e) {
			LOG.error("SHA3 init error:", e);
		}
	}
	

	@Override
	public String encrypt(String str) {
		String eStr = null;
		try {
			byte[] b=str.getBytes("UTF-8");
			digest.update(b);
			eStr=Hex.toHexString(digest.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return eStr;
	}

	@Override
	public String decrypt(String str) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isArgsChange(String... args) {
		return false;
	}

}
