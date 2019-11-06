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

class ListaPapers extends React.Component  {

  constructor(props) {
    super(props);
    this.state = {
      carregando : true,
      papers: null,
      evento: props.evento
    };
  }

  componentDidMount() {
    CFPService.get().papersPorEvento(this.state.evento.id).then(papers => {
      this.setState({
        papers: papers,
        carregando : false
      });
    });
  }

  selecionaEvento = evento => {
    window.location.hash = "/evento/" + evento.id;
  }

  render() {
    const {carregando, papers}  = this.state;
    return ( 
      <React.Fragment>
        <PageSection variant={PageSectionVariants.light} >
          <TextContent>
              <Text component={TextVariants.h2}>Papers</Text>
            </TextContent>
        </PageSection>
        <PageSection>
          {carregando ? 
            <Carregando /> :
            <Gallery gutter="lg">
                {papers.map((paper, i) => ({
                  // TODO: implementar
                })
            </Gallery>
            }
            </Gallery>}
          </PageSection>
        </React.Fragment>);
  }
}

export { PaginaEventos };