import React, { useState, useEffect } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import RegistrationService from "../api/service/RegistrationService";

const EditAssetForm = ({ assetId, onClose, refreshAssets }) => {
  const [formData, setFormData] = useState({
    name: "",
    vendor: "",
    price: "",
    status: ""
  });

  useEffect(() => {
    console.log("Received Asset ID in Edit Form:", assetId); // Debugging
    if (assetId) {
      fetchAssetDetails(assetId);
    }
  }, [assetId]);

  const fetchAssetDetails = async (id) => {
    try {
      const response = await RegistrationService.getAssetById(id);
      console.log("reposne: ",response);
      setFormData(response.data);
    } catch (error) {
      console.error("Error fetching asset details:", error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await RegistrationService.updateAsset(assetId, formData);
      alert("Asset updated successfully!");
      refreshAssets();
      onClose();
    } catch (error) {
      console.error("Error updating asset:", error);
    }
  };

  return (
    <Form onSubmit={handleSubmit}>
      <Form.Group className="mb-3">
        <Form.Label>Name</Form.Label>
        <Form.Control type="text" name="name" value={formData.name} onChange={handleInputChange} required />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Vendor</Form.Label>
        <Form.Control type="text" name="vendor" value={formData.vendor} onChange={handleInputChange} required />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Price</Form.Label>
        <Form.Control type="number" name="price" value={formData.price} onChange={handleInputChange} required />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Status</Form.Label>
        <Form.Control type="text" name="status" value={formData.status} onChange={handleInputChange} required />
      </Form.Group>

      <Button variant="primary" type="submit">Update Asset</Button>
    </Form>
  );
};

export default EditAssetForm;
