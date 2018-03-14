package org.jugvale.cfp.client.local.shared;

import java.util.Date;

import org.jboss.errai.databinding.client.api.Converter;
import org.jugvale.cfp.client.shared.AppUtils;

public class DateConverter implements Converter<Date, String> {

	@Override
	public Class<Date> getModelType() {
		return Date.class;
	}

	@Override
	public Date toModelValue(String widgetValue) {
		return AppUtils.converter(widgetValue);
	}

	@Override
	public Class<String> getComponentType() {
		return String.class;
	}

	@Override
	public String toWidgetValue(Date modelValue) {
		return AppUtils.converter(modelValue);
	}

}