import React from "react";
import { Modal, Button } from "react-bootstrap";
import RegistrationService from "../api/service/RegistrationService";

const DeleteAsset = ({ show, handleClose, assetId, refreshAssets }) => {
  const handleDelete = async () => {
    try {
      await RegistrationService.deleteAssetById(assetId);
      refreshAssets(); // Refresh list after deletion
      handleClose(); // Close modal
    } catch (error) {
      console.error("Error deleting asset:", error);
    }
  };

  return (
    <Modal show={show} onHide={handleClose} size="md" centered>
      <Modal.Header closeButton>
        <Modal.Title>Confirm Delete</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p>Are you sure you want to delete this asset?</p>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Cancel
        </Button>
        <Button variant="danger" onClick={handleDelete}>
          Delete
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default DeleteAsset;
