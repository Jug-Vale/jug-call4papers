package org.jugvale.cfp.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MessageUtil {
	
	private static ResourceBundle bundle = ResourceBundle.getBundle("messages");

	public static String getMessage(String key) {
		return bundle.getString(key);
	}
	
	public static String getMessage(String key, long id) {
		return MessageFormat.format(getMessage(key), id);
	}
}
