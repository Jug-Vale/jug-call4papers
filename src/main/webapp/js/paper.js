$(function() {
	
	$( document ).ready(function() {
		
		var eventoId = readURLParam('evento');
		carregaComboEvento(eventoId);
		novoEvento();
		//Inicio Mascara Telefone
	    $(".form-group input[type=tel]")
	    .mask("(00) 00000-0000");
	    /*
	    .mask("(99) 9999-9999?9")
	    .ready(function(event) {
	        var target, phone, element;
	        target = (event.currentTarget) ? event.currentTarget : event.srcElement;
	        phone = target.value.replace(/\D/g, '');
	        element = $(target);
	        element.unmask();
	        if(phone.length > 10) {
	            element.mask("(99) 99999-999?9");
	        } else {
	            element.mask("(99) 9999-9999?9");
	        }
	    });*/
	    //Fim Mascara Telefone
	    
	});
	
	function haErrosNosForms() {
		var temErro = false;
		var funcaoVerificaCampos = function () {
			var el = $(this);
			console.log(el);
			el.find("input[type=text], textarea").each(function () {
				console.log(this.value)
				if(this.value == "") {
					el.addClass("has-error");
					temErro = true;
				} else {
					el.removeClass("has-error").addClass("has-success");
				}
			});
		};
		$(".form-group").each(funcaoVerificaCampos);
		return temErro;
	}
	
	function carregaComboEvento(eventoId) {
		var evento =  EventoResource.buscaPorId({id:eventoId} );
		// TODO: Se não tiver o ID do evento passado deve direcionar para a página de 404
		$("#id_select_evento").append(new Option(evento.nome, evento.id));
	}
	
	function vaiParaOTopo() {
		
	}
	
	function limpaCamposForm() {
		
	}
	
	function novoEvento() {
		$("#btn_salvar").click( function() {
			//Autor
			var nome = $('#input_nome').val();
			var email = $('#input_email').val();
			var telefone = $('#input_telefone').val();
			var site = $('#input_site').val();
			var miniCv = $('#mini_cv_area').val();
			//Evento
			var eventoSelect = $('#id_select_evento').val();
			var tituloPalestra = $('#input_palestra').val();
			var descricao = $('#input_descricao').val();
			var tipoPalestra = $('#id_tipo_palestra').val();
			
			// verificar todos campos aqui iterando sob o form
			if(haErrosNosForms()) {
				vaiParaOTopo();
				return false;
			}
			
			var evento = EventoResource.buscaPorId( {id:eventoSelect} );
			$.autor = {
					nome:nome,
					email:email,
					telefone:telefone,
					site:site,
					miniCurriculo:miniCv
			}
			$.paper = { titulo:tituloPalestra,
						descricao:descricao,
						nota:0,
						aceito:false,
						evento:evento,
						tipo:tipoPalestra,
						autores:[$.autor] };
			
			console.log($.paper);
			
			var r = new REST.Request();
			r.setURI("./rest/paper");
			r.setMethod("POST");
			r.setContentType("application/json");
			r.setEntity($.paper);
			
			r.execute(function(status, request, response, entity) {
				
				if(status == 201) {
					// Mandando para uma página de sucesso, assim ele limpa os campos.
					$("#status_inscricao")
						.addClass( "alert alert-success alert-dismissible" )
						.append("Parabéns, seu paper foi salvo. Entraremos em contato para maiore informações =D");
						limpaCamposForm();
				} else {
					console.log(status);
					$("#status_inscricao")
						.addClass( "alert alert-danger alert-dismissible" )
						.append("Outch =/ Aconteceu algum erro");
				}
				vaiParaOTopo();
			});
		});
		
	}
	
});