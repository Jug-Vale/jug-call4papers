export default class Evento {
  
	nome:String;

	descricao:String;

	dataInicio:Date;

	dataFim:Date;

	local:String;

	url: String;

	aceitandoTrabalhos: Boolean;

  inscricoesAbertas:Boolean;
  
  constructor(nome:String, 
              descricao:String, 
              dataInicio:Date, 
              dataFim:Date, 
              local:String, 
              url:String, 
              aceitandoTrabalhos:Boolean,
              inscricoesAbertas:Boolean) {
    this.nome = nome;
    this.descricao = descricao;
    this.dataInicio = dataInicio;
    this.dataFim = dataFim;
    this.local = local;
    this.url = url;
    this.aceitandoTrabalhos = aceitandoTrabalhos;
    this.inscricoesAbertas = inscricoesAbertas;
  }
}