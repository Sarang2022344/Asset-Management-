import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

const AddAssetForm = () => {
  const [category, setCategory] = useState("");
  const [formData, setFormData] = useState({ date: "", images: [] });

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleImageChange = (e) => {
    setFormData({ ...formData, images: Array.from(e.target.files) });
  };

  return (
    <Form>
      <Form.Group className="mb-3">
        <Form.Label>Name</Form.Label>
        <Form.Control type="text" placeholder="Enter asset name" required />
      </Form.Group>
      
      <Form.Group className="mb-3">
        <Form.Label>Vendor</Form.Label>
        <Form.Control type="text" placeholder="Enter vendor" required />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Invoice</Form.Label>
        <Form.Control type="file" required />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Price</Form.Label>
        <Form.Control type="number" placeholder="Enter price" required />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Category</Form.Label>
        <Form.Select onChange={(e) => setCategory(e.target.value)} required>
          <option value="">Select Category</option>
          <option value="hardware">Hardware</option>
          <option value="software">Software</option>
        </Form.Select>
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Date</Form.Label>
        <Form.Control 
          type="date" 
          name="date" 
          value={formData.date} 
          onChange={handleInputChange} 
          required 
        />
      </Form.Group>

      {category === "hardware" && (
        <>
          <Form.Group className="mb-3">
            <Form.Label>Serial Number</Form.Label>
            <Form.Control type="text" placeholder="Enter serial number" required />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Specification</Form.Label>
            <Form.Control type="text" placeholder="Enter specification" required />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Brand Type</Form.Label>
            <Form.Control type="text" placeholder="Enter brand type" required />
          </Form.Group>
        </>
      )}

      {category === "software" && (
        <>
          <Form.Group className="mb-3">
            <Form.Label>Licenses</Form.Label>
            <Form.Control type="text" placeholder="Enter license details" required />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Expiry License</Form.Label>
            <Form.Control type="date" required />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Version</Form.Label>
            <Form.Control type="text" placeholder="Enter Version details" required />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Support OS</Form.Label>
            <Form.Control type="text" placeholder="Enter Supported OS" required />
          </Form.Group>
        </>
      )}

      <Form.Group className="mb-3">
        <Form.Label>Upload Images</Form.Label>
        <Form.Control type="file" multiple onChange={handleImageChange} required />
      </Form.Group>

      <Button variant="primary" type="submit">
        Submit
      </Button>
    </Form>
  );
};

export default AddAssetForm;
