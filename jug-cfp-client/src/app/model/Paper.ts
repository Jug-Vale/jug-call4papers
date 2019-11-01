class Paper {

  id : number;

  titulo: string;

  descricao: string;
  	
  dataSubmissao: Date;
	
  nota: number;

  aceito : boolean;

  tipo: String;

  constructor(id : number,
              titulo: string,
              descricao: string,
              dataSubmissao: Date,
              nota: number,
              aceito : boolean,
              tipo: String) {
    this.id  = id;
    this.titulo = titulo;
    this.descricao = descricao;
    this.dataSubmissao = dataSubmissao;
    this.nota = nota;
    this.aceito= aceito;
    this.tipo = tipo;
  }
}