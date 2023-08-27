import { OneOrMany } from '../../type';

export interface AnnouncementListProps {
  children: OneOrMany<AnnouncementListItemProps>;
}

export interface AnnouncementListItemProps {
  id: number;
  title: string;
  detailType: string;
  detailTypeName: string;
  announcedDate: string;
  closedDate: string;
  applicationTerm: string;
}
