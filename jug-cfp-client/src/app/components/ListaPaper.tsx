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
  Stack,
  StackItem,
} from '@patternfly/react-core';
import { CFPService } from '@app/service/CFPService';
import { Carregando } from './Carregando';

export class ListaPaper extends React.Component {

  private readonly TAMANHO_DESCRICAO = 220;

  constructor(props) {
    super(props);
    this.state = {
      carregando: true,
      papers: null,
      evento: props.evento
    };
  }

  componentDidMount() {
    CFPService.get().papersPorEvento(this.state.evento.id).then(papers => {
      this.setState({
        papers: papers,
        carregando: false
      });
    });
  }

  render() {
    const { carregando, papers } = this.state;
    return (
      <React.Fragment>
        <PageSection>
          {carregando ?
            <Carregando /> :
            <Stack>

              {papers.length > 0 ?
                <StackItem>
                  <TextContent>
                    <Text component={TextVariants.h2}>Papers Enviados</Text>
                  </TextContent>
                  <Gallery gutter="lg">
                    {papers.map((paper, i) => (
                      <GalleryItem>
                        <Card isCompact={true} onClick={() => { }} className="cfp-paper-card">
                          <CardHeader>{paper.titulo}</CardHeader>
                          <CardBody>
                            <Text className="cfp-paper-descricao" component={TextVariants.small}>
                              {paper.descricao.length > this.TAMANHO_DESCRICAO ?
                                paper.descricao.substring(0, this.TAMANHO_DESCRICAO) + "..." :
                                paper.descricao}
                            </Text>
                          </CardBody>
                          <CardFooter>
                            <a href="http://google.com">detalhes</a>
                          </CardFooter>
                        </Card>
                      </GalleryItem>
                    ))}
                  </Gallery>
                </StackItem>
                : <StackItem>
                  <Text component={TextVariants.h3}><em>Não há papers enviados...</em></Text>
                </StackItem>}
            </Stack>
          }
        </PageSection>
      </React.Fragment >);
  }
}