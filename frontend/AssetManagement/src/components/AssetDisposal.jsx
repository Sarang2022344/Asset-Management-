import React, { useState , useEffect } from "react";
import "./AssetDisposal.css";
import Sidebar from "./Sidebar";
import AssetDisposalService from "../api/service/AssetDisposal";
; 


const AssetDisposal = () => {
  const [tableData, setTableData] = useState([]);

  const [isFormOpen, setIsFormOpen] = useState(false);
  const [formData, setFormData] = useState({
    assetDisposalId: "",
    assetId: "",
    companyId: "",
    reason: "", // Reason will now be selected from a dropdown
    date: "",
  });

  const [editingRow, setEditingRow] = useState(null);

  // Handle form input changes
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    if (editingRow !== null) {
      // Update existing row
      const updatedData = tableData.map((row, index) =>
        index === editingRow ? formData : row
      );
      setTableData(updatedData);
      setEditingRow(null);
    } else {
      // Add new row
      setTableData([...tableData, formData]);
    }
    setIsFormOpen(false);
    setFormData({ assetDisposalId: "", assetId: "", companyId: "", reason: "", date: "" });
  };

  // Handle edit button click
  const handleEdit = (index) => {
    setFormData(tableData[index]);
    setEditingRow(index);
    setIsFormOpen(true);
  };

  // List of reasons for the dropdown
  const reasons = [
    "Obsolete",
    "Damaged",
    "End of Life",
    "Upgrade",
    "Other",
  ];

  useEffect(() => {
    // Fetch disposal data from backend
    AssetDisposalService.getAllDisposals()
      .then((data) => setTableData(data))
      .catch((error) => console.error("Error fetching data:", error));
  }, []);

  return (
    <div className="page-container">
      {/* Sidebar */}
      <Sidebar />

      {/* Main Content */}
      <div className="main-content">
        {/* Header */}
        <div className="header">
          <div className="search-bar">
            <input type="text" placeholder="Search..." />
          </div>
          <div className="filter-button">
            <button>Filter</button>
          </div>
          <div className="add-asset-button">
            <button onClick={() => setIsFormOpen(true)}>Add Asset for Disposal</button>
          </div>
        </div>

        {/* Page Content */}
        <div className="content">
          <h1>Asset Disposal Data</h1>
          <div className="table-container">
            <table className="asset-disposal-table">
              <thead>
                <tr>
                  <th>Asset Disposal ID</th>
                  <th>Asset Name</th>
                  <th>Company ID</th>
                  <th>Reason</th>
                  <th>Date</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
              {tableData.length > 0 ? (
                tableData.map((row, index) => (
                  <tr key={index}>
                    <td>{row.disposalId}</td>
                    <td>{row.assetName}</td>
                    <td>{row.companyId}</td>
                    <td>{row.reason}</td>
                    <td>{row.disposalDate}</td>
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

      {/* Pop-up Form */}
      {isFormOpen && (
        <div className="popup-form-overlay">
          <div className="popup-form">
            <h2>{editingRow !== null ? "Edit Asset Disposal" : "Add Asset Disposal"}</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label>Asset Disposal ID</label>
                <input
                  type="text"
                  name="assetDisposalId"
                  value={formData.assetDisposalId}
                  onChange={handleInputChange}
                  required
                />
              </div>
              <div className="form-group">
                <label>Asset ID</label>
                <input
                  type="text"
                  name="assetId"
                  value={formData.assetId}
                  onChange={handleInputChange}
                  required
                />
              </div>
              <div className="form-group">
                <label>Company ID</label>
                <input
                  type="text"
                  name="companyId"
                  value={formData.companyId}
                  onChange={handleInputChange}
                  required
                />
              </div>
              <div className="form-group">
                <label>Reason</label>
                <select
                  name="reason"
                  value={formData.reason}
                  onChange={handleInputChange}
                  required
                >
                  <option value="">Select a reason</option>
                  {reasons.map((reason, index) => (
                    <option key={index} value={reason}>
                      {reason}
                    </option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label>Date</label>
                <input
                  type="date"
                  name="date"
                  value={formData.date}
                  onChange={handleInputChange}
                  required
                />
              </div>
              <div className="form-buttons">
                <button type="submit">Submit</button>
                <button type="button" onClick={() => setIsFormOpen(false)}>
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  )
}

export default AssetDisposal
