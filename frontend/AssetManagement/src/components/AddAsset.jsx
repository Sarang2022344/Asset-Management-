import React from "react";
import Form from "react-bootstrap/Form";

const AddAssetForm = () => {
  return (
    <Form>
      <Form.Group className="mb-3">
        <Form.Label>Name</Form.Label>
        <Form.Control type="text" placeholder="Enter asset name" required />
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>Category</Form.Label>
        <Form.Control type="text" placeholder="Enter category" required />
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>Quantity</Form.Label>
        <Form.Control type="number" placeholder="Enter quantity" required />
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>Status</Form.Label>
        <Form.Control type="text" placeholder="Enter status" required />
      </Form.Group>
      <Form.Group className="mb-3">
        <Form.Label>Vendor</Form.Label>
        <Form.Control type="text" placeholder="Enter vendor" required />
      </Form.Group>
    </Form>
  );
};

export default AddAssetForm;