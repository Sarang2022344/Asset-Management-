import React, { useState, useEffect } from "react";
import { Modal, Button, Form, Image } from "react-bootstrap";
import RegistrationService from "../api/service/RegistrationService";

const ViewAsset = ({ show, handleClose, assetId }) => {
  const [asset, setAsset] = useState(null);

  useEffect(() => {
    if (assetId) {
      fetchAssetDetails();
    }
  }, [assetId]);

  const fetchAssetDetails = async () => {
    try {
        
    console.log("here in fetch ");
      const response = await RegistrationService.getAssetById(assetId);
      console.log("Fetched Asset Data:", response.data); 
      setAsset(response.data);
    } catch (error) {
      console.error("Error fetching asset details:", error);
    }
  };
  

  return (
    <Modal show={show} onHide={handleClose} size="lg">
      <Modal.Header closeButton>
        <Modal.Title>View Asset Details</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {asset ? (
          <Form>
            {/* Common Fields */}
            <Form.Group className="mb-3">
              <Form.Label>Name</Form.Label>
              <Form.Control type="text" value={asset.name} readOnly />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Category</Form.Label>
              <Form.Control type="text" value={asset.categoryType} readOnly />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Vendor</Form.Label>
              <Form.Control type="text" value={asset.vendor} readOnly />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Price</Form.Label>
              <Form.Control type="number" value={asset.price} readOnly />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Status</Form.Label>
              <Form.Control type="text" value={asset.status} readOnly />
            </Form.Group>

            {/* Hardware Fields */}
            {asset.hardwareDetails && (
              <>
                <h5 className="mt-3">Hardware Details</h5>
                <Form.Group className="mb-3">
                  <Form.Label>Serial Number</Form.Label>
                  <Form.Control type="text" value={asset.hardwareDetails.serialNumber} readOnly />
                </Form.Group>
                <Form.Group className="mb-3">
                  <Form.Label>Specifications</Form.Label>
                  <Form.Control type="text" value={asset.hardwareDetails.specifications} readOnly />
                </Form.Group>
                <Form.Group className="mb-3">
                  <Form.Label>Brand</Form.Label>
                  <Form.Control type="text" value={asset.hardwareDetails.brand} readOnly />
                </Form.Group>
                <Form.Group className="mb-3">
                  <Form.Label>Type</Form.Label>
                  <Form.Control type="text" value={asset.hardwareDetails.type} readOnly />
                </Form.Group>
              </>
            )}

            {/* Software Fields */}
            {asset.softwareDetails && (
              <>
                <h5 className="mt-3">Software Details</h5>
                <Form.Group className="mb-3">
                  <Form.Label>Licenses</Form.Label>
                  <Form.Control type="text" value={asset.softwareDetails.licenses.join(", ")} readOnly />
                </Form.Group>
                <Form.Group className="mb-3">
                  <Form.Label>License Expiry Date</Form.Label>
                  <Form.Control type="text" value={asset.softwareDetails.licenseExpiryDate} readOnly />
                </Form.Group>
                <Form.Group className="mb-3">
                  <Form.Label>Version</Form.Label>
                  <Form.Control type="text" value={asset.softwareDetails.version} readOnly />
                </Form.Group>
                <Form.Group className="mb-3">
                  <Form.Label>Supported OS</Form.Label>
                  <Form.Control type="text" value={asset.softwareDetails.supportedOs} readOnly />
                </Form.Group>
              </>
            )}

            {/* Invoice Image */}
            {asset.invoiceImage && (
              <>
                <h5 className="mt-3">Invoice</h5>
                <Image src={`${import.meta.env.VITE_APP_API_URL}/${asset.invoiceImage}`} alt="Invoice" fluid />
              </>
            )}

            {/* Asset Images */}
            {asset.imageFiles && asset.imageFiles.length > 0 && (
              <>
                <h5 className="mt-3">Asset Images</h5>
                <div className="d-flex flex-wrap">
                  {asset.imageFiles.map((image, index) => (
                    <Image
                      key={index}
                      src={`${import.meta.env.VITE_APP_API_URL}/${image}`}
                      alt={`Asset ${index + 1}`}
                      className="m-2"
                      style={{ width: "100px", height: "100px", objectFit: "cover" }}
                    />
                  ))}
                </div>
              </>
            )}
          </Form>
        ) : (
          <p>Loading asset details...</p>
        )}
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>Close</Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ViewAsset;
