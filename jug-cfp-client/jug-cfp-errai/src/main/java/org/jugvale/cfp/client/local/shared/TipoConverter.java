package org.jugvale.cfp.client.local.shared;

import org.jboss.errai.databinding.client.api.Converter;
import org.jugvale.cfp.model.enums.Tipo;

public class TipoConverter implements Converter<Tipo, String> {

	@Override
	public Class<Tipo> getModelType() {
		return Tipo.class;
	}

	@Override
	public Class<String> getComponentType() {
		return String.class;
	}

	@Override
	public Tipo toModelValue(String componentValue) {
		return Tipo.valueOf(componentValue);
	}

	@Override
	public String toWidgetValue(Tipo modelValue) {
		return modelValue.toString();
	}

}
