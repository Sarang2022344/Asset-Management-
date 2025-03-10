
import React, { useState } from "react";

const EditAssetDisposalForm = ({ formData, handleInputChange, handleSubmit, handleClose }) => {
  const [errors, setErrors] = useState({});
  const reasons = ["Obsolete", "Damaged", "End of Life", "Upgrade", "Other"];


  const validateForm = () => {
    let newErrors = {};

    if (!formData.reason) {
      newErrors.reason = "Please select a valid reason!";
    }
    if (!formData.date) {
      newErrors.date = "Please enter a valid date!";
    } else {
      const selectedDate = new Date(formData.date);
      const today = new Date();
      if (selectedDate < today.setHours(0, 0, 0, 0)) {
        newErrors.date = "Date cannot be in the past!";
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };


  const handleFormSubmit = (e) => {
    e.preventDefault(); 
    if (validateForm()) {
      handleSubmit(e); 
    }
  };
  

  return (
    <div className="popup-form-overlay">
      <div className="popup-form">
        <h2>Edit Asset Disposal</h2>
        <form onSubmit={handleFormSubmit}>
          {/* Read-Only Fields */}
          <div className="form-group">
            <label>Sr</label>
            <input type="text" name="sr" value={formData.disposalId} readOnly />
          </div>

          <div className="form-group">
            <label>Asset Name</label>
            <input type="text" name="assetName" value={formData.assetName} readOnly />
          </div>

          {/* Editable Reason */}
          <div className="form-group">
            <label>Reason</label>
            <select name="reason" value={formData.reason} onChange={handleInputChange} required>
              <option value="" disabled>Select a reason</option>
              {reasons.map((reason, index) => (
                <option key={index} value={reason}>
                  {reason}
                </option>
              ))}
            </select>
            {errors.reason && <span className="error-text">{errors.reason}</span>}
          </div>

          {/* Editable Date */}
          <div className="form-group">
            <label>Date</label>
            <input type="date" name="date" value={formData.date} onChange={handleInputChange} required />
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

export default EditAssetDisposalForm;
