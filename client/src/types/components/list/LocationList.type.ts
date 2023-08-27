import { OneOrMany } from '../../type';

export interface LocationListProps {
  children: OneOrMany<LocationListItemProps>;
}

export interface LocationListItemProps {
  children?: string;
  id: string;
  count: number;
}
