import * as React from 'react';
import { NavLink } from 'react-router-dom';
import { Alert, PageSection } from '@patternfly/react-core';

const NotFound: React.FunctionComponent = () => {
  return (
    <PageSection>
      <Alert variant="danger" title="404! Página Não existe" /><br />
      <NavLink to="/" className="pf-c-nav__link">Voltar para a página inicial</NavLink>
    </PageSection>
  );
}

export { NotFound };
