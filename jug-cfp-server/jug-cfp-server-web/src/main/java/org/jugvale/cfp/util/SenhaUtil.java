package org.jugvale.cfp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SenhaUtil {

	private SenhaUtil() { }

	/**
	 * 
	 * Gera uma senha "digest". <br>
	 * Fonte:
	 * http://howtodoinjava.com/2013/07/22/how-to-generate-secure-password
	 * -hash-md5-sha-pbkdf2-bcrypt-examples/
	 * 
	 * Possível melhorar o algoritmo usar aqui no futuro...
	 * 
	 * @param senhaOriginal
	 * @return
	 */
	public static String gerarHash(String senhaOriginal) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(senhaOriginal.getBytes());
			byte[] bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new Error(e);
		}
	}

}
