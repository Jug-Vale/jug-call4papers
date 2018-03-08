package org.jugvale.cfp.client.shared;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class AppUtils {

	private static final String DATE_FORMAT = "dd/MM/yyyy hh:mm";

	public static String converter(Date data) {
		return DateTimeFormat.getFormat(DATE_FORMAT).format(data);
	}

	public static Date converter(String str) {
		return DateTimeFormat.getFormat(DATE_FORMAT).parseStrict(str);
	}

}
