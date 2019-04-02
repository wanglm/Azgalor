package org.Azgalor.framework.security.algorithm;

import org.Azgalor.framework.security.Algorithm;
import org.Azgalor.framework.security.AlgorithmName;
import org.Azgalor.framework.security.SecurityService;
import org.Azgalor.framework.security.algorithm.asymmetric.Rsa;
import org.Azgalor.framework.security.algorithm.irreversible.Md5;
import org.Azgalor.framework.security.algorithm.irreversible.Sha3;
import org.Azgalor.framework.security.algorithm.symmetric.Aes;

public class BaseSecurityService implements SecurityService {
	private Algorithm algorithm;
	private AlgorithmName name=AlgorithmName.NONE;

	@Override
	public String encrypt(String str, AlgorithmName name, String... opts) throws Exception {
		reload(name, opts);
		String eStr = algorithm.encrypt(str);
		return eStr;
	}

	@Override
	public String decrypt(String str, AlgorithmName name, String... opts) throws Exception {
		reload(name, opts);
		String dStr = algorithm.decrypt(str);
		return dStr;
	}

	@Override
	public void reload(AlgorithmName name, String... opts) {
		int len = opts.length;
		if (this.name.equals(name)) {
			switch (name) {
			case RSA: {
				if (len == 3) {
					if (algorithm.isArgsChange(opts[0], opts[1], opts[2])) {
						algorithm = new Rsa(opts[0], opts[1], opts[2]);
					}
				} else {
					throw new IllegalArgumentException("RSA need 3 parameters! Now the number of parameter is " + len);
				}
				this.name = name;
				break;
			}
			case AES: {
				if (len == 1) {
					if (algorithm.isArgsChange(opts[0])) {
						algorithm = new Aes(opts[0]);
					}
				} else if (len == 2) {
					if (algorithm.isArgsChange(opts[0], opts[1])) {
						algorithm = new Aes(opts[0], opts[1]);
					}
				} else {
					throw new IllegalArgumentException(
							"AES need 1 or 2 parameters! Now the number of parameter is " + len);
				}
				this.name = name;
				break;
			}
			default:
				break;
			}
		} else {
			switch (name) {
			case RSA: {
				if (len == 3) {
					algorithm = new Rsa(opts[0], opts[1], opts[2]);
				} else {
					throw new IllegalArgumentException("RSA need 3 parameters! Now the number of parameter is " + len);
				}
				this.name = name;
				break;
			}
			case MD5: {
				algorithm = new Md5();
				this.name = name;
				break;
			}
			case SHA3: {
				algorithm = new Sha3();
				this.name = name;
				break;
			}
			case AES: {
				if (len == 1) {
					algorithm = new Aes(opts[0]);
				} else if (len == 2) {
					algorithm = new Aes(opts[0], opts[1]);
				} else {
					throw new IllegalArgumentException(
							"AES need 1 or 2 parameters! Now the number of parameter is " + len);
				}
				this.name = name;
				break;
			}
			default:
				break;
			}
		}
	}

}
