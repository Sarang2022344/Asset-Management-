import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import RegistrationService from "../api/service/RegistrationService";
import CategoryService from "../api/service/CategoryService";
import {useEffect } from "react";

const AddAssetForm = ({ onClose, refreshAssets }) => {
  const [categories, setCategories] = useState([]); 
  const [formData, setFormData] = useState({
    name: "",
    companyId: "", 
    categoryId: "",
    vendor: "",
    invoiceFile: null,
    price: "",
    status: "",
    categoryType: "Hardware",
    serialNumber: "",
    specifications: "",
    brand: "",
    type: "",
    licenses: "",
    licenseExpiryDate: "",
    version: "",
    supportedOs: "",
    imageFiles: [],
  });


   // ✅ Fetch categories when component loads
   useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      const data = await CategoryService.getAllCategories();
      setCategories(data); // ✅ Store categories
    } catch (error) {
      console.error("Error fetching categories:", error);
    }
  };

  // Handle input changes
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // Handle file uploads
  const handleFileChange = (e) => {
    const { name } = e.target;
    const files = e.target.files;
    setFormData({ ...formData, [name]: files });
  };


  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const data = new FormData();
    Object.keys(formData).forEach((key) => {
      if (key === "imageFiles" || key === "licenses") {
        Array.from(formData[key]).forEach((file) => data.append(key, file));
      } else {
        data.append(key, formData[key]);
      }
    });

    try {
      await RegistrationService.createAsset(data);
      alert("Asset added successfully!");
      refreshAssets(); 
      onClose(); 
    } catch (error) {
      console.error("Error adding asset:", error);
    }
  };

  return (
    <Form onSubmit={handleSubmit}>
      <Form.Group className="mb-3">
        <Form.Label>Name</Form.Label>
        <Form.Control type="text" name="name" value={formData.name} onChange={handleInputChange} required />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Company ID</Form.Label>
        <Form.Control type="text" name="companyId" value={formData.companyId} onChange={handleInputChange} required />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Vendor</Form.Label>
        <Form.Control type="text" name="vendor" value={formData.vendor} onChange={handleInputChange} required />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Invoice</Form.Label>
        <Form.Control type="file" name="invoiceFile" onChange={handleFileChange} />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Price</Form.Label>
        <Form.Control type="number" name="price" value={formData.price} onChange={handleInputChange} required />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Status</Form.Label>
        <Form.Control type="text" name="status" value={formData.status} onChange={handleInputChange} required />
      </Form.Group>

      <Form.Group className="mb-3">
      <Form.Label>Category</Form.Label>
        <Form.Select name="categoryId" value={formData.categoryId} onChange={handleInputChange} required>
          <option value="">Select Category</option>
          {categories.length > 0 ? (
            categories.map((category) => (
              <option key={category.categoryId} value={category.categoryId}>
                {category.categoryName}
              </option>
            ))
          ) : (
            <option disabled>Loading categories...</option>
          )}
        </Form.Select>
      </Form.Group>

      {formData.categoryType === "Hardware" && (
        <>
          <Form.Group className="mb-3">
            <Form.Label>Serial Number</Form.Label>
            <Form.Control type="text" name="serialNumber" value={formData.serialNumber} onChange={handleInputChange} />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Specifications</Form.Label>
            <Form.Control type="text" name="specifications" value={formData.specifications} onChange={handleInputChange} />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Brand</Form.Label>
            <Form.Control type="text" name="brand" value={formData.brand} onChange={handleInputChange} />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Type</Form.Label>
            <Form.Control type="text" name="type" value={formData.type} onChange={handleInputChange} />
          </Form.Group>
        </>
      )}

      {formData.categoryType === "Software" && (
        <>
          <Form.Group className="mb-3">
            <Form.Label>Licenses</Form.Label>
            <Form.Control type="text" name="licenses" value={formData.licenses} onChange={handleInputChange} />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>License Expiry Date</Form.Label>
            <Form.Control type="date" name="licenseExpiryDate" value={formData.licenseExpiryDate} onChange={handleInputChange} />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Version</Form.Label>
            <Form.Control type="text" name="version" value={formData.version} onChange={handleInputChange} />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Supported OS</Form.Label>
            <Form.Control type="text" name="supportedOs" value={formData.supportedOs} onChange={handleInputChange} />
          </Form.Group>
        </>
      )}

      <Form.Group className="mb-3">
        <Form.Label>Upload Images</Form.Label>
        <Form.Control type="file" name="imageFiles" multiple onChange={handleFileChange} />
      </Form.Group>

      <Button variant="primary" type="submit">
        Submit
      </Button>
    </Form>
  );
};

export default AddAssetForm;
