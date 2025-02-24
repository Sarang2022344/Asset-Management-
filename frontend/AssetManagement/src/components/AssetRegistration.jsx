import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Table from "react-bootstrap/Table";
import AddAsset from "./AddAsset"; 
import "./AssetRegistration.css";
import AddAssetForm from "./AddAsset";
import Modal from "react-bootstrap/Modal"; 

const AddAssetRegistration = () => {

  const [showModal, setShowModal] = useState(false); 
  // Function to open the modal
  const handleShowModal = () => setShowModal(true);
   
    const handleCloseModal = () => setShowModal(false);

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between mb-3">
        <div className="d-flex">
          <Form.Control type="text" placeholder="Search..." className="me-2" />
          <Button variant="secondary">Filter</Button>
          <Button variant="secondary">Upload</Button>
        </div>
        <Button variant="primary" onClick={handleShowModal}>Add Asset</Button>
      </div>
<Table striped bordered hover>
  <thead>
    <tr>
      <th>Sr</th>
      <th>Name</th>
      <th>Category</th>
      <th>Quantity</th>
      <th>Status</th>
      <th>Vendor</th>
      <th>Action</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>1</td>
      <td>Laptop</td>
      <td>Electronics</td>
      <td>15</td>
      <td>Available</td>
      <td>Dell</td>
      <td>
        <button className="btn btn-sm btn-link">
          <i className="fas fa-eye"></i> 
        </button>
        <button className="btn btn-sm btn-link">
          <i className="fas fa-edit"></i> 
        </button>
        <button className="btn btn-sm btn-link">
          <i className="fas fa-trash text-danger"></i>
        </button>
      </td>
    </tr>
    <tr>
      <td>2</td>
      <td>Conference Table</td>
      <td>Furniture</td>
      <td>3</td>
      <td>In Use</td>
      <td>Furnica</td>
      <td>
        <button className="btn btn-sm btn-link">
          <i className="fas fa-eye text-primary"></i> 
        </button>
        <button className="btn btn-sm btn-link">
          <i className="fas fa-edit text-primary"></i> 
        </button>
        <button className="btn btn-sm btn-link">
          <i className="fas fa-trash text-danger"></i> 
        </button>
      </td>
    </tr>
    <tr>
      <td>3</td>
      <td>Printer</td>
      <td>Electronics</td>
      <td>4</td>
      <td>Under Maintenance</td>
      <td>LG</td>
      <td>
        <button className="btn btn-sm btn-link">
          <i className="fas fa-eye"></i> 
        </button>
        <button className="btn btn-sm btn-link">
          <i className="fas fa-edit"></i> 
        </button>
        <button className="btn btn-sm btn-link">
          <i className="fas fa-trash text-danger"></i> 
        </button>
      </td>
    </tr>
  </tbody>
</Table>

<Modal show={showModal} onHide={handleCloseModal} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>Add Asset</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <AddAssetForm /> 
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleCloseModal}>
            Close
          </Button>
          <Button variant="primary">
            Save Changes
          </Button>
        </Modal.Footer>
      </Modal>

    </div>
  );
};

export default AddAssetRegistration ;



