import * as React from 'react';
import '@patternfly/react-core/dist/styles/base.css';
import {CFPService} from './service/CFPService';
import '@app/app.css';
import {
  Brand,
  Nav,
  NavItem,
  NavList,
  NavVariants,
  Page,
  PageHeader,
  TextContent,
  Text,
  PageSection,
  PageSectionVariants

} from '@patternfly/react-core';
import imgBrand from './imgBrand.png';
import { PaginaEventos } from './components/PaginaEventos';
import { HashRouter as Router, Route, Switch, Link } from 'react-router-dom';
import { DetalhesEvento } from './components/DetalhesEvento';

class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      activeItem: 0,
      eventos: []
    };
  }

  onNavSelect = result => {
    this.setState({
      activeItem: result.itemId
    });
  }

  goHome = () => {
    this.setState({
      activeItem: 0
    });
    window.location.href = "/";
  }

  render() {
    const { activeItem } = this.state;

    const PageNav = (
      <Nav onSelect={this.onNavSelect} aria-label="Nav">
        <NavList variant={NavVariants.horizontal}>
        <NavItem itemId={0} isActive={activeItem === 0}>
            <Link to="/"> Home </Link>
          </NavItem>
          <NavItem itemId={1} isActive={activeItem === 1}>
            <Link to="/evento/"> Eventos </Link>
          </NavItem>
          <NavItem itemId={2} isActive={activeItem === 2}>
            <Link to="/sobre"> Sobre </Link>
          </NavItem>
        </NavList>
      </Nav>    
    );

    const Header = (
      <PageHeader
        className="pageHeader"
        logo={<Brand src={imgBrand} href="/" alt="JUG Vale Logo" onClick={this.goHome}/>}
        topNav={PageNav}
      />
    );

    return (
      <Router basename="/">
        <Page header={Header}>
          <Switch>
            <Route path="/" component={Home} exact/>
            <Route path="/evento/" component={PaginaEventos} exact/>
            <Route path="/evento/:eventoId(\d+)?" component={DetalhesEvento} />
            <Route path="/sobre" component={Sobre} exact/>
            <Route component={NotFound} />
            </Switch>
        </Page>
      </Router>
    );
  }
}


const Home : React.FunctionComponent = () => {
  return (
    <React.Fragment>
    <PageSection variant={PageSectionVariants.light}>
      <TextContent>
        <Text component="h1">HOME PAGE</Text>
      </TextContent>
    </PageSection>
    </React.Fragment>
  );
}


const Sobre : React.FunctionComponent = () => {
  return (
    <React.Fragment>
    <PageSection variant={PageSectionVariants.light}>
      <TextContent>
        <Text component="h1">SOBRE</Text>
      </TextContent>
    </PageSection>
    </React.Fragment>
  );
}

const NotFound : React.FunctionComponent = () => {
  return (
    <React.Fragment>
    <PageSection variant={PageSectionVariants.light}>
      <TextContent>
        <Text component="h1">PÁGINA NÃO ENCONTRADA!</Text>
      </TextContent>
    </PageSection>
    </React.Fragment>
  );
}

export { App };