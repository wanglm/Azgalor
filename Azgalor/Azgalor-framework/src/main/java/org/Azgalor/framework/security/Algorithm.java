package org.Azgalor.framework.security;

/**加密算法
 * @author ming
 *
 */
public interface Algorithm {
	/**加密
	 * @param str
	 * @return
	 */
	public String encrypt(String str);
	
	
	/**解密
	 * @param str
	 * @return
	 */
	public String decrypt(String str);
	
	/**参数是否改变
	 * @param args
	 * @return
	 */
	public boolean isArgsChange(String... args);
}
