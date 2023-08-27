import React from 'react';

import TopPanel from './panels/TopPanel';
import { AnnouncementList, AnnouncementListItem } from '../components/list/AnnouncementList';

function AnnouncementPage() {
  const list = [
    { key: 1, id: 1, title: 'test 1', detailType: 'public', detailTypeName: '공공임대', announcedDate: '2023-06-23', closedDate: '2023-08-01', applicationTerm: '' },
    { key: 2, id: 2, title: 'test 2', detailType: 'people', detailTypeName: '국민임대', announcedDate: '2023-06-23', closedDate: '2023-08-01', applicationTerm: '' },
    { key: 3, id: 3, title: 'test 3', detailType: 'test', detailTypeName: '테스트', announcedDate: '2023-06-23', closedDate: '2023-08-01', applicationTerm: '' },
    { key: 4, id: 4, title: 'test 4', detailType: 'test', detailTypeName: '테스트', announcedDate: '2023-06-23', closedDate: '2023-08-03', applicationTerm: '' },
    { key: 5, id: 5, title: 'test 5', detailType: 'test', detailTypeName: '테스트', announcedDate: '2023-06-23', closedDate: '2023-08-02', applicationTerm: '' },
  ];

  return (
    <>
      <TopPanel />
      <AnnouncementList>
        {list.map(({ key, ...props }) => <AnnouncementListItem key={key} {...props} />)}
      </AnnouncementList>
    </>
  );
}

export default React.memo(AnnouncementPage);
