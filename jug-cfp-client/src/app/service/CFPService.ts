import {Evento} from '../model/Evento'
import { Paper } from '@app/model/Paper';

class CFPService {

  papers : Paper[] = [];

  evts : Evento[] = [
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
  
  public static get() : CFPService {
    return CFPService.instance;
  }
  
  private constructor(){}

  public eventos() : Promise<Evento[]>  {
    return new Promise(resolve => {
      setTimeout(() => resolve(this.evts), 1000)
    });
  }
  public evento(id: number) : Promise<Evento> {
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