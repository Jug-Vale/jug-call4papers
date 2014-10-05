$(function() {
	
	$( document ).ready(function() {
		carregaComboEvento();
	});
	
	function carregaComboEvento() {
		var eventos = EventoResource.listarTodos();
		
		
		var texto = "<label>Evento</label>" +
					"<select class='combobox input-large form-control' name='normal'>" +
						"<option value='null' selected='selected'>Selecione o evento</option>";
		
		$.each(eventos, function(key, value) {
			
			texto += "<option value='" +value.id+ "'>" + value.nome + "</option>";

		});
		
		texto+= "</select>"
		$('#paper_id').append(texto);

	}
	
});