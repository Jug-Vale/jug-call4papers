
import * as React from 'react';
import {
  Text,
  TextVariants
} from '@patternfly/react-core';

const Carregando : React.FunctionComponent = () => {
  return (<Text component={TextVariants.h3}>Carregando...</Text> );
}

export { Carregando };