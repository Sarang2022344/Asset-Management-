import React from "react";

const EditAssetDisposalForm = ({
  formData,
  handleInputChange,
  handleSubmit,
  handleClose, // Changed from setIsFormOpen to handleClose
}) => {
  const reasons = ["Obsolete", "Damaged", "End of Life", "Upgrade", "Other"];

  return (
    <div className="popup-form-overlay">
      <div className="popup-form">
        <h2>Edit Asset Disposal</h2>
        <form onSubmit={handleSubmit}>
          {/* Sr (Read-Only) */}
          <div className="form-group">
            <label>Sr</label>
            <input
              type="text"
              name="sr"
              value={formData.disposalId}
              readOnly
            />
          </div>

          {/* Asset Name (Read-Only) */}
          <div className="form-group">
            <label>Asset Name</label>
            <input
              type="text"
              name="assetName"
              value={formData.assetName}
              readOnly
            />
          </div>

          {/* Editable Reason Field */}
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

          {/* Editable Date Field */}
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
            <button type="button" onClick={handleClose}> {/* Updated to use handleClose */}
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EditAssetDisposalForm;