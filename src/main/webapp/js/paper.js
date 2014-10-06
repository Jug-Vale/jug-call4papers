$(function() {
	
	$( document ).ready(function() {
		
		//Inicio Mascara Telefone
		    jQuery('input[type=tel]').mask("(99) 9999-9999?9").ready(function(event) {
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
		    });
	    //Fim Mascara Telefone
	    
		carregaComboEvento();
		novoEvento()
	});
	
	function carregaComboEvento() {
		var eventos = EventoResource.listarTodos();
		var select = $("#id_select_evento");
		
		if(eventos.length === 1) {
			select.append(new Option("Selecione um Evento", ""));
		} 
			
		$.each(eventos, function(key, value) {
			select.append(new Option(value.nome, value.id));
		});
		
	}
	
	function varificaCamposPaper( eventoSelect, tituloPalestra, descricao, tipoPalestra ) {
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
	
	function verificaCamposAutor(login, senha, nome, email, miniCv) {
		
		if (login === "") {
			$('#login_form').addClass("has-error");
		} else {
			$('#login_form').removeClass("has-error").addClass("has-success");
		}
		
		if (senha === "") {
			$('#senha_form').addClass("has-error");
		} else {
			$('#senha_form').removeClass("has-error").addClass("has-success");
		}
		
		if (nome === "") {
			$('#nome_form').addClass("has-error");
		} else {
			$('#nome_form').removeClass("has-error").addClass("has-success");
		}
		
		if (email === "") {
			$('#email_form').addClass("has-error");
		} else {
			$('#email_form').removeClass("has-error").addClass("has-success");
		}
		
		if (miniCv === "") {
			$('#mini_cv_form').addClass("has-error");
		} else {
			$('#mini_cv_form').removeClass("has-error").addClass("has-success");
		}
		
		return ( (login !== "") && (senha !== "") && (nome !== "") && (email !== "") && (miniCv !== "") )
		
	}
	
	function novoEvento() {
		
		var url = (window.location.href).replace("paper.html", "");
		
		$("#btn_salvar").click( function() {
			
			//Autor
			var login = $('#input_login').val();
			var senha = $('#input_senha').val();
			var nome = $('#input_nome').val();
			var email = $('#input_email').val();
			var telefone = $('#input_telefone').val();
			var site = $('#input_site').val();
			var miniCv = $('#mini_cv_area').val();
			
			var podeProsseguir = verificaCamposAutor(login, senha, nome, email, miniCv);
			
			if (podeProsseguir) {
				
				$.autor = {
						usuario:{
							login:login,
							senha:senha,
							role:"AUTOR"
						},
						nome:nome,
						email:email,
						telefone:telefone,
						site:site,
						miniCurriculo:miniCv
				}
				
			}
			
			//Evento
			var eventoSelect = $('#id_select_evento').val();
			var tituloPalestra = $('#input_palestra').val();
			var descricao = $('#input_descricao').val();
			var tipoPalestra = $('#id_tipo_palestra').val();
			
			if ( varificaCamposPaper(eventoSelect, tituloPalestra, descricao, tipoPalestra) ) {
				
				var evento = EventoResource.buscaPorId( {id:eventoSelect} );
				var autores = AutorResource.listarTodos();
	
				$.paper = { titulo:tituloPalestra,
							descricao:descricao,
							nota:0,
							aceito:false,
							evento:evento,
							tipo:tipoPalestra,
							autores:[$.autor] };
				
				console.log($.paper);
				
				var r = new REST.Request();
				r.setURI(url + "rest/paper");
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