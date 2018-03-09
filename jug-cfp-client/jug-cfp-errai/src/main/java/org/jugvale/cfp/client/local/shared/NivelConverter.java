package org.jugvale.cfp.client.local.shared;

import org.jboss.errai.databinding.client.api.Converter;
import org.jugvale.cfp.model.enums.Nivel;

public class NivelConverter implements Converter<Nivel, String> {

	@Override
	public Class<Nivel> getModelType() {
		return Nivel.class;
	}

	@Override
	public Class<String> getComponentType() {
		// TODO Auto-generated method stub
		return String.class;
	}

	@Override
	public Nivel toModelValue(String componentValue) {
		return Nivel.valueOf(componentValue);
	}

	@Override
	public String toWidgetValue(Nivel modelValue) {
		return modelValue.toString();
	}

}
