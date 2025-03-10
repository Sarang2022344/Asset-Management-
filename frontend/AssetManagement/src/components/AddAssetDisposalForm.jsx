
import React, { useState } from 'react';

const AddAssetDisposalForm = ({ isAddFormOpen, handleClose, formData, handleInputChange, handleSubmit }) => {
  const [errors, setErrors] = useState({});
  const reasons = ["Obsolete", "Damaged", "End of Life", "Upgrade", "Other"];

  if (!isAddFormOpen) return null;

  // 🔹 Validation before submitting
  const validateForm = () => {
    let newErrors = {};

    if (!formData.assetId.match(/^[0-9]+$/)) {
      newErrors.assetId = "Asset ID must be a number!";
    }
    if (!formData.companyId.match(/^[0-9]+$/)) {
      newErrors.companyId = "Company ID must be a number!";
    }
    if (!formData.reason) {
      newErrors.reason = "Please select a reason!";
    }
    if (!formData.date) {
      newErrors.date = "Please select a valid date!";
    } else {
      const selectedDate = new Date(formData.date);
      const today = new Date();
      if (selectedDate < today.setHours(0, 0, 0, 0)) {
        newErrors.date = "Date cannot be in the past!";
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0; // ✅ Return `true` if no errors
  };

  // 🔹 Handle Submit with Validation
  const handleFormSubmit = (e) => {
    e.preventDefault();
    if (validateForm()) {
      handleSubmit();
    }
  };

  return (
    <div className="popup-form-overlay">
      <div className="popup-form">
        <h2>Add Asset Disposal</h2>
        <form onSubmit={(e) => handleFormSubmit(e)}>

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
            {errors.assetId && <span className="error-text">{errors.assetId}</span>}
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
            {errors.companyId && <span className="error-text">{errors.companyId}</span>}
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
              <option value="" disabled>Select a reason</option>
              {reasons.map((reason, index) => (
                <option key={index} value={reason}>
                  {reason}
                </option>
              ))}
            </select>
            {errors.reason && <span className="error-text">{errors.reason}</span>}
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
            {errors.date && <span className="error-text">{errors.date}</span>}
          </div>

          {/* Buttons */}
          <div className="form-buttons">
            <button type="submit">Submit</button>
            <button type="button" onClick={handleClose}>Cancel</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddAssetDisposalForm;
