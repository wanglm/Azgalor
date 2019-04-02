package org.Azgalor.framework.security;

public interface SecurityService {

	/**强制刷新算法，否则同名算法实例，参数改变的时候会报错
	 * @param name
	 * @param opts
	 */
	public void reload(AlgorithmName name, String... opts);
	
	public String encrypt(String str, AlgorithmName name, String... opts) throws Exception;

	public String decrypt(String str, AlgorithmName name, String... opts) throws Exception;

}
