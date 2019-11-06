import { Autor } from "./Autor";

class Paper {

  id : number;

  eventoId : number;

  titulo: string;

  descricao: string;
  	
  dataSubmissao: Date;
	
  nota: number;

  aceito : boolean;

  tipo: String;

  autores: Autor[]; 

  constructor(id : number,
              eventoId : number,
              titulo: string,
              descricao: string,
              dataSubmissao: Date,
              nota: number,
              aceito : boolean,
              tipo: String,
              autores: Autor[]) {
    this.id  = id;
    this.eventoId = eventoId;
    this.titulo = titulo;
    this.descricao = descricao;
    this.dataSubmissao = dataSubmissao;
    this.nota = nota;
    this.aceito= aceito;
    this.tipo = tipo;
    this.autores = autores;
  }
}

export { Paper };