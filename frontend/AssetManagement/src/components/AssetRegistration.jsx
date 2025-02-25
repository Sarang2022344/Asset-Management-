import React, { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Table from "react-bootstrap/Table";
import AddAsset from "./AddAsset"; 
import "./AssetRegistration.css";
import AddAssetForm from "./AddAsset";
import Modal from "react-bootstrap/Modal"; 
import RegistrationService from "../api/service/RegistrationService";
import ViewAsset from "../components/ViewAsset";
import DeleteAsset from "../components/deleteAsset";

const AddAssetRegistration = () => {
  const [showModal, setShowModal] = useState(false); 
  const [assets, setAssets] = useState([]); 
  const [showViewModal, setShowViewModal] = useState(false);
  const [selectedAssetId, setSelectedAssetId] = useState(null);
  const handleShowModal = () => setShowModal(true);
  const [showDeleteModal, setShowDeleteModal] = useState(false);

    const handleCloseModal = () => setShowModal(false);


useEffect(() => {
  fetchAssets();
}, []);

const fetchAssets = async () => {
  try {
    console.log("here in fetch ");
    const response = await RegistrationService.getAllAssets();
    setAssets(response.data); 
  } catch (error) {
    console.error("Error fetching assets:", error);
  }
};

const handleView = (id) => {
  setSelectedAssetId(id);
  setShowViewModal(true);
};

const handleDeleteClick = (id) => {
  setSelectedAssetId(id);
  setShowDeleteModal(true);
};


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
            <th>Status</th>
            <th>Vendor</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {assets.length > 0 ? (
            assets.map((asset, index) => (
              <tr key={asset.assetId}>
                <td>{index + 1}</td>
                <td>{asset.name}</td>
                <td>{asset.categoryType || "N/A"}</td>
                <td>{asset.status}</td>
                <td>{asset.vendor}</td>
                <td>
                  <button className="btn btn-sm btn-link"onClick={() => handleView(index+1)}>
                    <i className="fas fa-eye text-primary"></i>
                  </button>
                  <button className="btn btn-sm btn-link">
                    <i className="fas fa-edit text-primary"></i>
                  </button>
                  <button className="btn btn-sm btn-link">
                    <i className="fas fa-trash text-danger" onClick={() => handleDeleteClick(index+1)}>
                    </i>
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="7" className="text-center">No assets available</td>
            </tr>
          )}
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

      <ViewAsset show={showViewModal} handleClose={() => setShowViewModal(false)} assetId={selectedAssetId} />

      <DeleteAsset show={showDeleteModal} handleClose={() => setShowDeleteModal(false)} assetId={selectedAssetId} refreshAssets={fetchAssets} />

    </div>
  );
};

export default AddAssetRegistration ;






// import React, { useState, useEffect } from "react";
// import Button from "react-bootstrap/Button";
// import Form from "react-bootstrap/Form";
// import Table from "react-bootstrap/Table";
// import Modal from "react-bootstrap/Modal";
// import AddAssetForm from "./AddAsset";
// import RegistrationService from "../api/service/RegistrationService";

// const AddAssetRegistration = () => {
//   const [showModal, setShowModal] = useState(false);
//   const [assets, setAssets] = useState([]); // Store assets from API

//   // 🔹 Fetch assets from API when the component mounts
//   useEffect(() => {
//     fetchAssets();
//   }, []);

//   // 🔹 Function to fetch assets from backend
//   const fetchAssets = async () => {
//     try {
//       const response = await RegistrationService.getAllAssets();
//       setAssets(response.data); // Store response in state
//     } catch (error) {
//       console.error("Error fetching assets:", error);
//     }
//   };

//   const handleShowModal = () => setShowModal(true);
//   const handleCloseModal = () => setShowModal(false);

//   return (
//     <div className="container mt-4">
//       <div className="d-flex justify-content-between mb-3">
//         <div className="d-flex">
//           <Form.Control type="text" placeholder="Search..." className="me-2" />
//           <Button variant="secondary">Filter</Button>
//           <Button variant="secondary">Upload</Button>
//         </div>
//         <Button variant="primary" onClick={handleShowModal}>Add Asset</Button>
//       </div>

//       <Table striped bordered hover>
//         <thead>
//           <tr>
//             <th>Sr</th>
//             <th>Name</th>
//             <th>Category</th>
//             <th>Price</th>
//             <th>Status</th>
//             <th>Vendor</th>
//             <th>Action</th>
//           </tr>
//         </thead>
//         <tbody>
//           {assets.length > 0 ? (
//             assets.map((asset, index) => (
//               <tr key={asset.assetId}>
//                 <td>{index + 1}</td>
//                 <td>{asset.name}</td>
//                 <td>{asset.category?.categoryName || "N/A"}</td>
//                 <td>${asset.price}</td>
//                 <td>{asset.status}</td>
//                 <td>{asset.vendor}</td>
//                 <td>
//                   <button className="btn btn-sm btn-link">
//                     <i className="fas fa-eye text-primary"></i>
//                   </button>
//                   <button className="btn btn-sm btn-link">
//                     <i className="fas fa-edit text-primary"></i>
//                   </button>
//                   <button className="btn btn-sm btn-link">
//                     <i className="fas fa-trash text-danger"></i>
//                   </button>
//                 </td>
//               </tr>
//             ))
//           ) : (
//             <tr>
//               <td colSpan="7" className="text-center">No assets available</td>
//             </tr>
//           )}
//         </tbody>
//       </Table>

//       <Modal show={showModal} onHide={handleCloseModal} size="lg">
//         <Modal.Header closeButton>
//           <Modal.Title>Add Asset</Modal.Title>
//         </Modal.Header>
//         <Modal.Body>
//           <AddAssetForm onClose={handleCloseModal} refreshAssets={fetchAssets} />
//         </Modal.Body>
//       </Modal>
//     </div>
//   );
// };

// export default AddAssetRegistration;
