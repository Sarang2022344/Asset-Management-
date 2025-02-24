import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Table from "react-bootstrap/Table";
import "./AssetRegistration.css";

const AddAsset = () => {
  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between mb-3">
        <div className="d-flex">
          <Form.Control type="text" placeholder="Search..." className="me-2" />
          <Button variant="secondary">Filter</Button>
        </div>
        <Button variant="primary">Add Asset</Button>
      </div>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Category</th>
            <th>Quantity</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>1</td>
            <td>Laptop</td>
            <td>Electronics</td>
            <td>15</td>
            <td>Available</td>
          </tr>
          <tr>
            <td>2</td>
            <td>Conference Table</td>
            <td>Furniture</td>
            <td>3</td>
            <td>In Use</td>
          </tr>
          <tr>
            <td>3</td>
            <td>Printer</td>
            <td>Electronics</td>
            <td>4</td>
            <td>Under Maintenance</td>
          </tr>
        </tbody>
      </Table>
    </div>
  );
};

export default AddAsset;
