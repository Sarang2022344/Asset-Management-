import React, { useState, useEffect } from "react";
import "./AssetDisposal.css";
import Sidebar from "./Sidebar";
import AssetDisposalService from "../api/service/AssetDisposal";
import AddAssetDisposalForm from "../components/AddAssetDisposalForm";
import EditAssetDisposalForm from "../components/Editdisposal";

const AssetDisposal = () => {
  const [tableData, setTableData] = useState([]);
  const [isAddFormOpen, setIsAddFormOpen] = useState(false);
  const [isEditFormOpen, setIsEditFormOpen] = useState(false);
  const [formData, setFormData] = useState({
    assetId: '',
    companyId: '',
    reason: '',
    date: '',
  });

  useEffect(() => {
    // Fetch disposal data from backend
    AssetDisposalService.getAllDisposals()
      .then((data) => setTableData(data))
      .catch((error) => console.error("Error fetching data:", error));
  }, []);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleAdd = () => {
    setFormData({
      assetId: "",
      companyId: "",
      reason: "",
      date: "",
    });
    setIsAddFormOpen(true);
  };

  const handleEdit = (disposalId) => {
    const disposal = tableData.find((item) => item.disposalId === disposalId);
    if (disposal) {
      setFormData({
        disposalId: disposal.disposalId,
        assetName: disposal.assetName,
        reason: disposal.reason,
        date: disposal.disposalDate,
      });
      setIsEditFormOpen(true);
    }
  };
  

  const handleAddSubmit = async (e) => {
    e.preventDefault();
    try {
      const newDisposal = await AssetDisposalService.addDisposal(formData);
      // Fetch the asset details
      const assetResponse = await axios.get(`${ASSET_API_URL}/get/${newDisposal.assetId}`);
      const assetData = assetResponse.data;
      // Add the assetName to the newDisposal object
      const disposalWithAssetName = {
        ...newDisposal,
        assetName: assetData.name || "Unknown",
      };
      setTableData([...tableData, disposalWithAssetName]);
      setIsAddFormOpen(false);
    } catch (error) {
      console.error("Error adding disposal:", error);
    }
  };


  const handleEditSubmit = async (e) => {
    e.preventDefault();
    try {
      const updatedDisposal = await AssetDisposalService.updateDisposal(
        formData.disposalId,
        { reason: formData.reason, disposalDate: formData.date }
      );
  
      setTableData((prevData) =>
        prevData.map((item) =>
          item.disposalId === formData.disposalId ? { ...item, ...updatedDisposal } : item
        )
      );
      setIsEditFormOpen(false);
    } catch (error) {
      console.error("Error updating disposal:", error);
    }
  };
  

  const handleClose = () => {
    setIsAddFormOpen(false);
    setIsEditFormOpen(false);
  };

  return (
    <div className="page-container">
      <Sidebar />
      <div className="main-content">
        <div className="header">
          <div className="search-bar">
            <input type="text" placeholder="Search..." />
          </div>
          <div className="filter-button">
            <button>Filter</button>
          </div>
          <div className="add-asset-button">
            <button onClick={handleAdd}>Add Asset for Disposal</button>
          </div>
        </div>
        <div className="content">
          <h1>Asset Disposal Data</h1>
          <div className="table-container">
            <table className="asset-disposal-table">
              <thead>
                <tr>
                  <th>Sr</th>
                  <th>Asset Name</th>
                  <th>Reason</th>
                  <th>Date</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
  {tableData.length > 0 ? (
    tableData.map((row) => (
      <tr key={row.disposalId}>
        <td>{row.disposalId}</td>
        <td>{row.assetName}</td>
        <td>{row.reason}</td>
        <td>{row.disposalDate}</td>
        <td>
          <button
            className="edit-button"
            onClick={() => handleEdit(row.disposalId)}
          >
            Edit
          </button>
        </td>
      </tr>
    ))
  ) : (
    <tr>
      <td colSpan="5">No records found</td>
    </tr>
  )}
</tbody>

            </table>
          </div>
        </div>
      </div>
      <AddAssetDisposalForm
        isAddFormOpen={isAddFormOpen}
        handleClose={handleClose}
        formData={formData}
        handleInputChange={handleInputChange}
        handleSubmit={handleAddSubmit}
      />
      {isEditFormOpen && (
        <EditAssetDisposalForm
          formData={formData}
          handleInputChange={handleInputChange}
          handleSubmit={handleEditSubmit}
          handleClose={handleClose}
        />
      )}
    </div>
  );
};

export default AssetDisposal;