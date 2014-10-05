$(function() {
	
	$( document ).ready(function() {
		carregaComboEvento();
		novoEvento()
	});
	
	function carregaComboEvento() {
		var eventos = EventoResource.listarTodos();
		var select = $("#id_select_evento");
		
		$.each(eventos, function(key, value) {
			select.append(new Option(value.nome, value.id));
		});
	}
	
	function verificaSePodeProsseguir( eventoSelect, tituloPalestra, descricao, tipoPalestra ) {
		if (eventoSelect === "") {
			$('#evento_form').addClass("has-error");
		} else {
			$('#evento_form').removeClass("has-error").addClass("has-success");
		}
		
		if (tituloPalestra === "") {
			$('#titulo_form').addClass("has-error");
		} else {
			$('#titulo_form').removeClass("has-error").addClass("has-success");
		}
		
		if (descricao === "") {
			$('#descricao_form').addClass("has-error");
		} else {
			$('#descricao_form').removeClass("has-error").addClass("has-success");
		}
		
		if (tipoPalestra === "") {
			$('#tipo_form').addClass("has-error");
		} else {
			$('#tipo_form').removeClass("has-error").addClass("has-success");
		}
		
		return ( (eventoSelect !== "") && (tituloPalestra !== "") && (descricao !== "") && (tipoPalestra !== "") )
	}
	
	function novoEvento() {
		
		$("#btn_salvar").click( function() {
			
			var eventoSelect = $('#id_select_evento').val();
			var tituloPalestra = $('#input_palestra').val();
			var descricao = $('#input_descricao').val();
			var tipoPalestra = $('#id_tipo_palestra').val();
			
			if ( verificaSePodeProsseguir(eventoSelect, tituloPalestra, descricao, tipoPalestra) ) {
				
				var evento = EventoResource.buscaPorId( {id:eventoSelect} );
				var autores = AutorResource.listarTodos();
	
				$.paper = { titulo:tituloPalestra,
							descricao:descricao,
							nota:0,
							aceito:false,
							evento:evento,
							tipo:tipoPalestra,
							autores:autores };
				
				
				var r = new REST.Request();
				r.setURI("http://localhost:8080/jug-call4papers/rest/paper");
				r.setMethod("POST");
				r.setContentType("application/json");
				r.setEntity($.paper);
				
				r.execute(function(status, request, response, entity) {
					
					if(status == 201) {
						$("#status_inscricao")
							.addClass( "alert alert-success alert-dismissible" )
							.append("Parabéns, seu papaer foi salvo. Entraremos em contato para maiore informações =D");
					} else {
						console.log(status);
						$("#status_inscricao")
							.addClass( "alert alert-danger alert-dismissible" )
							.append("Outch =/ Aconteceu algum erro");
					}
				});
			}
			
		});
		
	}
	
});