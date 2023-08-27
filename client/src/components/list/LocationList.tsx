import React from 'react';

import { LocationListProps, LocationListItemProps } from '../../types/components/list/LocationList.type';
import { Button } from '@mui/material';

function List({ children }: LocationListProps) {
  return <>{children}</>;
}

export const LocationList = React.memo(List);

function Item({ id, children, count }: LocationListItemProps) {
  return (
    <Button className="location__list__button" color="info">
      <div className="location__list__item">
        <span className="location__list__item__label">{children}</span>
        <span className="location__list__item__count">{count}개</span>
      </div>
    </Button>
  );
}

export const LocationListItem = React.memo(Item);
