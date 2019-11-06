class Autor {

  public readonly id: number;

	public readonly nome: string;

	public readonly email: string;

	public readonly site : string;

  public readonly miniCurriculo: string;
  
  constructor(id: number,
              nome: string,
              email: string,
              site : string,
              miniCurriculo: string) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.site = site;
    this.miniCurriculo = miniCurriculo;
  }
}

export { Autor };