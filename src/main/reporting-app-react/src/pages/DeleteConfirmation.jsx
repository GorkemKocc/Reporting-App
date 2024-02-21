import React from 'react';
import { Confirm } from 'semantic-ui-react';

const DeleteConfirmation = ({ open, setOpen, onConfirm }) => (
  <Confirm
    open={open}
    onCancel={() => setOpen(false)}
    onConfirm={() => {
      setOpen(false);
      onConfirm();
    }}
    content='Are you sure you want to delete this report?'
    cancelButton='Cancel'
    confirmButton='Delete'
    size='mini'
  />
);

export default DeleteConfirmation;
