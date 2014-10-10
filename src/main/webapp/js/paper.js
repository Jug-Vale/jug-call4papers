$(function() {
	
	$( document ).ready(function() {
		carregaEvento();
		registraListeners();
		criaMascaras();
	});
	
	function registraListeners() {
		$("#btn_salvar").click(salvaEvento);	
	}
	
	function criaMascaras() {
		$(".form-group input[type=tel]").mask("(00) 00000-0000");
	}
	
	function haErrosNosForms() {
		var temErro = false;
		var funcaoVerificaCampos = function () {
			var el = $(this);
			el.find("input[type=text], textarea, input[type=tel]").each(function () {
				if(validar(this)) {
					el.removeClass(CLASSE_CSS_ERRO_FORM).addClass(CLASSE_CSS_SUCESSO_FORM);
				} else {
					el.addClass(CLASSE_CSS_ERRO_FORM);
					temErro = true;
				}
			});
		};
		$(".form-group").each(funcaoVerificaCampos);
		return temErro;
	}
	
	function validar (el) {
		var validador = /.+/;
		if(el.id == 'input_email') {
			validador = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		}
		if (el.id == 'input_site') {
			validador = /^(ht|f)tps?:\/\/[a-z0-9-\.]+\.[a-z]{2,4}\/?([^\s<>\#%"\,\{\}\\|\\\^\[\]`]+)?$/;
		}
		return validador.test(el.value);
	}
	
	function carregaEvento() {
		var eventoId = readURLParam('evento');
		if(!eventoId) naoEncontrado();
		EventoResource.buscaPorId({
			id: eventoId, 
			$callback: function(httpCode, xmlHttpRequest, evento) {
				if(httpCode == 200){
					$("#id_select_evento").append(new Option(evento.nome, evento.id));
					$("#span_nome_evento").html(evento.nome);
					$.evento = evento;
				} else if (httpCode == 404){
					naoEncontrado();
				}
			}
		});
	}
	
	function vaiParaOTopo() {
		window.scrollTo(0,0);
	}
	
	function limpaCamposForm() {
		$(".form-group").each(function (){
			$(this).removeClass(CLASSE_CSS_SUCESSO_FORM)
				.removeClass(CLASSE_CSS_ERRO_FORM)
		    	.find("input[type=text], textarea, input[type=tel]").each(function () {
		    	this.value = "";
		    })
		});
	}
	
	function salvaEvento() {
		// Angular ia bem aqui viu
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
		
		if(haErrosNosForms()) {
			vaiParaOTopo();
			$("#status_inscricao")
				.addClass( "alert alert-danger alert-dismissible" )
				.html("Há problemas no formulário. Por favor, verifique os campos com erro.");
			return false;
		}
		
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
					evento:$.evento,
					tipo:tipoPalestra,
					autores:[$.autor] };		
		console.log($.paper);
		// TODO: Mudar request para usar API do RESTEasy. Assim não temos hardcode de URL.
		var r = new REST.Request();
		r.setURI("./rest/paper");
		r.setMethod("POST");
		r.setContentType("application/json");
		r.setEntity($.paper);
		r.execute(function(status, request, response, entity) {
			console.log("Servidor respondeu com " + status);
			if(status == 201) {
				$("#status_inscricao")
					.removeClass(CLASSE_CSS_SUBMISSAO_PROBLEMA)
					.addClass(CLASSE_CSS_SUBMISSAO_SUCESSO)
					.html("Parabéns, seu paper foi salvo. Entraremos em contato para maiores informações =D");
					limpaCamposForm();
			}
			else {
				$("#status_inscricao")
					.removeClass(CLASSE_CSS_SUBMISSAO_SUCESSO)
					.addClass( "alert alert-danger alert-dismissible" )
					.html("Outch =/ Aconteceu algum erro. Tente novamente mais tarde e/ou envie um e-mail para jugvale@gmail.com");
			}
			vaiParaOTopo();
		});
	}
});