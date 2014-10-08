$(function() {
	
	$( document ).ready(function() {
		carregaEvento(readURLParam('evento'));
		$("#btn_salvar").click(salvaEvento);
	    $(".form-group input[type=tel]").mask("(00) 00000-0000");
	    
	});
	
	function haErrosNosForms() {
		var temErro = false;
		var funcaoVerificaCampos = function () {
			var el = $(this);
			console.log(el);
			el.find("input[type=text], textarea, input[type=tel]").each(function () {
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
	
	function carregaEvento(eventoId) {
		EventoResource.buscaPorId({
			id: eventoId, 
			$callback: function(httpCode, xmlHttpRequest, evento) {
				if(httpCode == 200){
					$("#id_select_evento").append(new Option(evento.nome, evento.id));
					$("#span_nome_evento").html(evento.nome);
					$.evento = evento;
				} else {
					// Aqui: Direcionar para página de evento não encontrado...
				}
			}
		});
		
	}
	
	function vaiParaOTopo() {
		window.scrollTo(0,0);
	}
	
	function limpaCamposForm() {
		$(".form-group").find("input[type=text], textarea, input[type=tel]").each(function () {
			this.value = "";
		});
	}
	
	function salvaEvento() {
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
		PaperResource.criar({
			$entity: $.paper,
			$callback: function(status, request, response, entity) {
				if(status == 201) {
					$("#status_inscricao")
						.addClass( "alert alert-success alert-dismissible" )
						.append("Parabéns, seu paper foi salvo. Entraremos em contato para maiores informações =D");
						limpaCamposForm();
				} else {
					console.log(status);
					$("#status_inscricao")
						.addClass( "alert alert-danger alert-dismissible" )
						.append("Outch =/ Aconteceu algum erro. Tente novamente mais tarde e/ou envie um e-mail para jugvale@gmail.com");
				}
				vaiParaOTopo();
			}
		});
	}
	
});