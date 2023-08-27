import React from 'react';

import { Button } from '@mui/material';
import { AnnouncementListItemProps, AnnouncementListProps } from '../../types/components/list/AnnounceList.type';

function List({ children }: AnnouncementListProps) {
  return <>{children}</>;
}

export const AnnouncementList = React.memo(List);

function Item({ id, title, detailType, detailTypeName, announcedDate, closedDate, applicationTerm }: AnnouncementListItemProps) {
  const closedDateTimestamp = new Date(`${closedDate} 00:00:00`).getTime();
  const todayDateTimestamp = new Date().getTime();
  const ddayTimestamp = (closedDateTimestamp - todayDateTimestamp);
  const dday = Math.floor(ddayTimestamp / 24 / 60 / 60 / 1000);

  return (
    <Button className="announcement__list__button" color="info">
      <div className="announcement__list__item">
        <p className={`announcement__list__item__thumb announcement__list__item__thumb--${detailType}`}>{detailTypeName.substring(0, 1)}</p>
        <div className="announcement__list__item__text">
          <strong className="announcement__list__item__location">
            서울 관악구
            <span className={`announcement__list__item__mark announcement__list__item__mark--${detailType}`}>{detailTypeName}</span>
          </strong>
          <p className="announcement__list__item__description">
            {title}
          </p>
          <p className="announcement__list__item__metadata">
            공고일 {announcedDate}
            <span className="announcement__list__item__mark announcement__list__item__mark--info">
              D{dday === 0 ? `DAY` : dday > 0 ? `-${dday}` : `+${dday * -1}`}
            </span>
          </p>
        </div>
      </div>
    </Button>
  );
}

export const AnnouncementListItem = React.memo(Item);
