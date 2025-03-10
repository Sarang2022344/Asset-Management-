const API_BASE_URL = import.meta.env.VITE_APP_API_URL;
import React, { useState, useEffect } from "react";
import "./AssetDisposal.css";
import Sidebar from "./Sidebar";
import AssetDisposalService from "../api/service/AssetDisposal";
import AddAssetDisposalForm from "../components/AddAssetDisposalForm";
import EditAssetDisposalForm from "../components/Editdisposal";
import axios from "axios";


const AssetDisposal = () => {
  const [tableData, setTableData] = useState([]);
  const [isAddFormOpen, setIsAddFormOpen] = useState(false);
  const [isEditFormOpen, setIsEditFormOpen] = useState(false);
  const [isFilterPopupOpen, setIsFilterPopupOpen] = useState(false);
  const [isFilteredPopupOpen, setIsFilteredPopupOpen] = useState(false);

  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [filteredData, setFilteredData] = useState([]);
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

  const handleFilterButtonClick = () => {
    setIsFilterPopupOpen(true);
    console.log("Filter popup opened:", isFilterPopupOpen); // Debugging state update
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
    if (e) e.preventDefault();
    try {
      const newDisposal = await AssetDisposalService.addDisposal(formData);
      const assetResponse = await axios.get(`${API_BASE_URL}/api/registration/get/${newDisposal.assetId}`);
      const assetData = assetResponse.data;
  
      // Add the assetName to the newDisposal object
      const disposalWithAssetName = {
        ...newDisposal,
        assetName: assetData.name || "Unknown",
      };
  
      setTableData([...tableData, disposalWithAssetName]);
      setIsAddFormOpen(false);
      alert("Asset Disposed successfully");
    } catch (error) {
      console.error("Error adding disposal:", error);
      // alert("Asset is already disposed"); // Display the error message from the backend
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
      alert("Asset Edit Successfully");
    } catch (error) {
      console.error("Error updating disposal:", error);
    }
  };
  

  const handleClose = () => {
    setIsAddFormOpen(false);
    setIsEditFormOpen(false);
  };

  const handleFilterApply = async () => {
    try {
      console.log("Applying filter for dates:", startDate, endDate); // Debugging input
  
      const response = await axios.get(`${API_BASE_URL}/disposal/range`, {
        params: { startDate, endDate },
      });
  
      console.log("Filtered data received:", response.data); // Debugging API response
  
      // Fetch asset names for each asset ID in the filtered data
      const updatedFilteredData = await Promise.all(
        response.data.map(async (disposal) => {
          try {
            const assetResponse = await axios.get(`${API_BASE_URL}/api/registration/get/${disposal.assetId}`);
            const assetData = assetResponse.data;
            console.log("Asset Response:", assetResponse.data);
  
            return {
              ...disposal,
              assetName: assetData.name || "Unknown",
            };
          } catch (error) {
            console.error(`Error fetching asset name for ID ${disposal.assetId}:`, error);
            return { ...disposal, assetName: "Unknown" };
          }
        })
      );
  
      console.log("Updated Filtered Data with Asset Names:", updatedFilteredData);
  
      setFilteredData(updatedFilteredData);
      setIsFilteredPopupOpen(true);
      setIsFilterPopupOpen(false);
  
    } catch (error) {
      console.error("Error fetching filtered data:", error.response?.data || error.message);
      alert("Error fetching filtered data. Please check the console.");
    }
  };


  const FilterPopup = ({ isOpen, onClose, onApply, startDate, setStartDate, endDate, setEndDate }) => {
    if (!isOpen) return null;

    return (
      <div className="popup-form-overlay">
        <div className="popup-form">
          <h2>Filter by Date Range</h2>
          <form
            onSubmit={(e) => {
              e.preventDefault();
              onApply();
            }}
          >
            <div className="form-group">
              <label>Start Date</label>
              <input
                type="date"
                value={startDate}
                onChange={(e) => setStartDate(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <label>End Date</label>
              <input
                type="date"
                value={endDate}
                onChange={(e) => setEndDate(e.target.value)}
                required
              />
            </div>
            <div className="form-buttons">
              <button type="submit">Apply</button>
              <button type="button" onClick={onClose}>
                Cancel
              </button>
            </div>
          </form>
        </div>
      </div>
    );
  };

  const FilteredDataPopup = ({ isOpen, onClose, filteredData }) => {
    if (!isOpen) return null;

    return (
      <div className="popup-form-overlay">
        <div className="popup-form">
          <h2>Filtered Asset Disposal Data</h2>
          <div className="table-container">
            <table className="asset-disposal-table">
              <thead>
                <tr>
                  <th>Sr</th>
                  <th>Asset Name</th>
                  <th>Reason</th>
                  <th>Date</th>
                </tr>
              </thead>
              <tbody>
                {filteredData.length > 0 ? (
                  filteredData.map((row) => (
                    <tr key={row.disposalId}>
                      <td>{row.disposalId}</td>
                      <td>{row.assetName}</td>
                      <td>{row.reason}</td>
                      <td>{row.disposalDate}</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="4">No records found</td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
          <div className="form-buttons">
            <button type="button" onClick={onClose}>
              Close
            </button>
          </div>
        </div>
      </div>
);
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
          <button onClick={handleFilterButtonClick}>Filter</button>
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
        handleSubmit={(e) => handleAddSubmit(e)}
      />
      {isEditFormOpen && (
        <EditAssetDisposalForm
          formData={formData}
          handleInputChange={handleInputChange}
          handleSubmit={handleEditSubmit}
          handleClose={handleClose}
        />
      )}
      {/* Filter Popup */}
      <FilterPopup
       isOpen={isFilterPopupOpen}
       onClose={() => setIsFilterPopupOpen(false)}
       onApply={handleFilterApply}
       startDate={startDate}
       setStartDate={setStartDate}
       endDate={endDate}
       setEndDate={setEndDate}
     />

     {/* Filtered Data Popup */}
     <FilteredDataPopup
  isOpen={isFilteredPopupOpen}
  onClose={() => setIsFilteredPopupOpen(false)}
  filteredData={filteredData}
/>
    </div>
  );
};

export default AssetDisposal;