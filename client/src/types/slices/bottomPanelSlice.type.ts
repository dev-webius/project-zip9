export interface IPanelManager {
  open: boolean;
  onOpen: () => void;
  onClose: () => void;
}
