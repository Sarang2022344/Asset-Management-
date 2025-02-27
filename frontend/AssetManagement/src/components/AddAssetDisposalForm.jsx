import React from 'react';

const AddAssetDisposalForm = ({ isAddFormOpen, handleClose, formData, handleInputChange, handleSubmit }) => {
  // List of reasons for the dropdown
  const reasons = ["Obsolete", "Damaged", "End of Life", "Upgrade", "Other"];

  if (!isAddFormOpen) return null; 

  return (
    <div className="popup-form-overlay">
      <div className="popup-form">
        <h2>Add Asset Disposal</h2>
        <form onSubmit={handleSubmit}>
          {/* Asset ID Field */}
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

          {/* Company ID Field */}
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

          {/* Reason Dropdown */}
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

          {/* Date Field */}
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

          {/* Buttons */}
          <div className="form-buttons">
            <button type="submit">Submit</button>
            <button type="button" onClick={handleClose}>
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddAssetDisposalForm;