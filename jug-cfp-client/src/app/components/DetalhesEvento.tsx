import * as React from 'react';
import {
  PageSection,
  PageSectionVariants,
  TextContent,
  Text,
  Stack,
  TextVariants,
  Button,
  Toolbar,
  ToolbarGroup,
  ToolbarItem
} from '@patternfly/react-core';
import { Carregando } from './Carregando';
import { CFPService } from '@app/service/CFPService';

class DetalhesEvento extends React.Component {


  constructor(props) {
    super(props)
    this.state = {
      carregando: true,
      evento: null
    }
  }

  componentDidMount() {
    const eventoId = this.props.match.params.eventoId;
    console.log(eventoId)
    console.log(this.props.match)
    CFPService.get().evento(eventoId).then(evento => {
      this.setState({
        evento: evento,
        carregando : false
      });
    });
  }


  render() {
    
    const { carregando, evento } = this.state;
    return (
      <PageSection variant={PageSectionVariants.light}>
        {carregando ? <Carregando /> :
          evento ?
            <Stack className="detalhes-evento" gutter="sm" >
              <TextContent>
                <Text className="detalhes-evento_titulo" component={TextVariants.h1}>{evento.nome}</Text>
              </TextContent>
              <TextContent>
                <Text component={TextVariants.blockquote}>{evento.descricao}</Text>
              </TextContent>
              <TextContent>
                <Text component={TextVariants.h3}>Data e Local</Text>
              </TextContent>
              <TextContent>
                <Text component={TextVariants.small}>
                  <em>{evento.dataInicio.toLocaleString()} - {evento.dataFim.toLocaleString()} - {evento.local}</em></Text>
              </TextContent>
              <Toolbar>
                <ToolbarGroup>
                  <ToolbarItem><Button isDisabled={!evento.inscricoesAbertas}> {evento.inscricoesAbertas ? "Inscrever" : "Inscrições Fechadas"} </Button></ToolbarItem>
                </ToolbarGroup>
                <ToolbarGroup>
                  <ToolbarItem><Button isDisabled={!evento.aceitandoTrabalhos}> {evento.aceitandoTrabalhos ? "Enviar Palestra" : "Palestras Fechadas"} </Button></ToolbarItem>
                </ToolbarGroup>
              </Toolbar>
            </Stack>
            :
            <Text>Evento não encontrado!</Text>
        }
      </PageSection>
    );
  }
}

export { DetalhesEvento };