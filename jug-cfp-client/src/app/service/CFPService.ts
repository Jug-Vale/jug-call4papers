import { Evento } from '../model/Evento'
import { Paper } from '@app/model/Paper';

class CFPService {

  papers: Paper[] = [
    new Paper(
      1, 1,
      "Comunicação assíncrona entre micro-serviços.",
      "Em um ambiente o qual se faz uso da arquitetura de micro-servicos, requisitos não funcionais como troca de dados e mensagens se faz necessários em muitos casos para promover integrações entre os mesmos para que fluxos de negócio se completem de forma satisfatória. Integrações entre serviços, podem ser feitas de forma assíncrona onde essa abordagem promove o baixo acoplamento entre os serviços, por outro lado, integrações assíncronas para troca de dados pode promover um atraso na replicação dos dados e fluxos de negócio conhecidos como consistência eventual. Nessa palestra vamos entender melhor sobre essa característica da consistência eventual em replicação de dados entre micro-servicos, com exemplos e as vantagens e desvantagens dessa característica que sem bem aplicada, traz benefícios para a arquitetura de aplicações de grande escala",
      new Date(),
      10,
      true,
      "PALESTRA",
      []
    ),
    new Paper(
      2, 1,
      "Documentação viva numa arquitetura de Microservicos",
      "Ao optarmos por uma arquitetura com microservicos, nos deparamos com uma realidade espantosa em documentação dos endpoints de cada servico. Nesta palestra, mostrarei uma documentação de Microservicos de forma centralizada, atualizada e definida em granularidade fina. Como reunir todos os milhares documentos de endpoints de Microservicos sem gerar múltiplas URLs e de forma que potenciais usuários não se percam ao consultar e consumir nosso ecossistema. Stack: Kotlin + Spring",
      new Date(),
      10,
      true,
      "PALESTRA",
      []
    ),
  ];

  evts: Evento[] = [
    new Evento(1,
      "JUG Vale 13",
      "Encontro de usuários Java do Vale do Paraíba edição número 14! Mais uma vez vamos falar de novidades da plataforma Java como o lançamento da versão 13 (que será lançada 3 dias após o evento), execução de aplicações Java em nuvens usando compilação nativa e recheado de conteúdo para iniciantes na programação. Além de Java, também contaremos com a partipação de empresas da região falando sobre tecnologias que utilizam e indicando como participar de seus processos seletivos, vale a pena conferir para profissionais procurando por novas oportunidades e os que desejam seu primeiro emprego.",
      new Date(),
      new Date(),
      "Rua feliz",
      "http://jugvale.com",
      true,
      false),
    new Evento(2,
      "JUG Vale 20",
      "O grande jug vale 20vem aiii chjeio de java e alegria hue huehuehuehu",
      new Date(),
      new Date(),
      "Rua triste e as vezes feliz",
      "http://jugvale.com",
      false,
      true),
  ]

  private static instance: CFPService = new CFPService();

  public static get(): CFPService {
    return CFPService.instance;
  }

  private constructor() { }

  public eventos(): Promise<Evento[]> {
    return new Promise(resolve => {
      setTimeout(() => resolve(this.evts), 1000)
    });
  }
  public evento(id: number): Promise<Evento> {
    return new Promise(resolve => {
      setTimeout(() => resolve(this.evts.filter(e => e.id == id)[0]), 1000)
    });
  }

  public papersPorEvento(id: number): Promise<Paper[]> {
    return new Promise(resolve => {
      setTimeout(() => resolve(this.papers.filter(p => p.eventoId == id)), 1000)
    });
  }

}

export { CFPService };