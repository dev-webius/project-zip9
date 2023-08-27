import React from 'react';

export type OneOrMany<P = {}> = React.ReactElement<P> | React.ReactElement<P>[];
