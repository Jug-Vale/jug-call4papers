import * as React from 'react';
import {
  Badge,
  Card,
  CardHeader,
  CardBody,
  CardFooter,
  Gallery,
  GalleryItem,
  PageSection,
  PageSectionVariants,
  TextContent,
  Text,
  TextVariants,
} from '@patternfly/react-core';
import { CFPService } from '@app/service/CFPService';
import { Carregando } from './Carregando';

class PaginaEventos extends React.Component  {

  constructor(props) {
    super(props);
    this.state = {
      carregando : true,
      eventos: null
    };
  }

  componentDidMount() {
    CFPService.get().eventos().then(eventos => {
      this.setState({
        eventos: eventos,
        carregando : false
      });
    });
  }

  selecionaEvento = evento => {
    window.location.hash = "/evento/" + evento.id;
  }

  render() {
    const {carregando, eventos}  = this.state;
    return ( 
      <React.Fragment>
        <PageSection variant={PageSectionVariants.light} >
          <TextContent>
              <Text component={TextVariants.h2}>Eventos</Text>
            </TextContent>
        </PageSection>
        <PageSection>
          {carregando ? 
            <Carregando /> :
            <Gallery gutter="lg">
                {eventos.map((evento, i) => (
                  <GalleryItem key={i}>
                    <Card isCompact={true} onClick={() => this.selecionaEvento(evento)} className="pf-c-card-eventos"> 
                      <CardHeader>{evento.nome}</CardHeader>
                      <CardBody><Text className="cfp-card-body-text">{evento.descricao}</Text></CardBody>
                      <CardFooter>
                      {evento.inscricoesAbertas  ? 
                            <Badge>Inscrições Abertas</Badge> : 
                            <Badge isRead>Inscrições Fechadas</Badge>  
                      }
                      {evento.aceitandoTrabalhos  ? 
                            <Badge>Envie Palestras</Badge> : <Badge></Badge> 
                      }
                      </CardFooter>
                    </Card>
                  </GalleryItem>
                ))
            }
            </Gallery>}
          </PageSection>
        </React.Fragment>);
  }
}

export { PaginaEventos };