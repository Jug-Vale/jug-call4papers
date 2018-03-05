package org.jugvale.cfp.client.local;

import java.util.Date;

import org.jboss.errai.databinding.client.api.Converter;

public class DateConverter implements Converter<Date, String> {

	@Override
	public Date toModelValue(final String widgetValue) {
		if (widgetValue == null || widgetValue.equals("")) {
			return null;
		}

		final elemental2.core.Date jsDate = new elemental2.core.Date(widgetValue);
		return new Date((long) jsDate.getTime());
	}

	@Override
	public String toWidgetValue(final Date modelValue) {
		if (modelValue == null) {
			return "...";
		} else {
			final elemental2.core.Date jsDate = new elemental2.core.Date(((Long) modelValue.getTime()).doubleValue());
			return jsDate.toISOString().substring(0, 10);
		}
	}

	@Override
	public Class<Date> getModelType() {
		return Date.class;
	}

	@Override
	public Class<String> getComponentType() {
		return String.class;
	}

}