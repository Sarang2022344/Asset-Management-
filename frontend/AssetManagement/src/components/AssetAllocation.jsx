// import React, { useEffect, useState } from 'react';
// import {
//   getAllAllocations,
//   allocateAsset,
//   returnAsset,
//   updateAssetAllocation,
// } from '../api/service/api';
// import 'bootstrap/dist/css/bootstrap.min.css';
// import '../components/AssetAllocation.css'

// const AssetAllocation = () => {
//   const [allocations, setAllocations] = useState([]);
//   const [assetId, setAssetId] = useState('');
//   const [employeeId, setEmployeeId] = useState('');
//   const [userId, setUserId] = useState('');
//   const [allocationId, setAllocationId] = useState('');
//   const [returnedDate, setReturnedDate] = useState('');
//   const [status, setStatus] = useState('');
//   const [message, setMessage] = useState('');
//   const [showAllocateForm, setShowAllocateForm] = useState(false);
//   const [showReturnForm, setShowReturnForm] = useState(false);
//   const [showUpdateForm, setShowUpdateForm] = useState(false);

//   // Fetch all allocations on page load
//   useEffect(() => {
//     const fetchAllocations = async () => {
//       const data = await getAllAllocations();
//       setAllocations(data);
//     };
//     fetchAllocations();
//   }, []);

//   // Handle asset allocation
//   const handleAllocateAsset = async (e) => {
//     e.preventDefault();
//     if (!assetId || !employeeId || !userId) {
//       setMessage('Please fill all fields!');
//       return;
//     }
//     const response = await allocateAsset(assetId, employeeId, userId);
//     setMessage(response);
//     setAssetId('');
//     setEmployeeId('');
//     setUserId('');
//     // Refresh allocations
//     const data = await getAllAllocations();
//     setAllocations(data);
//   };

//   // Handle asset return
//   const handleReturnAsset = async (e) => {
//     e.preventDefault();
//     if (!allocationId || !returnedDate) {
//       setMessage('Please fill all fields!');
//       return;
//     }
//     const response = await returnAsset(allocationId, returnedDate);
//     setMessage(response);
//     setAllocationId('');
//     setReturnedDate('');
//     // Refresh allocations
//     const data = await getAllAllocations();
//     setAllocations(data);
//   };

//   // Handle allocation update
//   const handleUpdateAllocation = async (e) => {
//     e.preventDefault();
//     if (!allocationId || !status) {
//       setMessage('Please fill all fields!');
//       return;
//     }
//     const updates = { status };
//     const response = await updateAssetAllocation(allocationId, updates);
//     setMessage(response);
//     setAllocationId('');
//     setStatus('');
//     // Refresh allocations
//     const data = await getAllAllocations();
//     setAllocations(data);
//   };

//   return (
//     <div className="container mt-4">
//       <h2>Asset Allocation</h2>

//       {/* Display message */}
//       {message && <div className="alert alert-info">{message}</div>}

//       {/* Allocate Asset Box */}
//       <div className="card mb-4">
//         <div
//           className="card-header"
//           onClick={() => setShowAllocateForm(!showAllocateForm)}
//           style={{ cursor: 'pointer' }}
//         >
//           Allocate Asset {showAllocateForm ? '▲' : '▼'}
//         </div>
//         {showAllocateForm && (
//           <div className="card-body">
//             <form onSubmit={handleAllocateAsset}>
//               <div className="mb-3">
//                 <label htmlFor="assetId" className="form-label">Asset ID</label>
//                 <input
//                   type="number"
//                   className="form-control"
//                   id="assetId"
//                   value={assetId}
//                   onChange={(e) => setAssetId(e.target.value)}
//                   required
//                 />
//               </div>
//               <div className="mb-3">
//                 <label htmlFor="employeeId" className="form-label">Employee ID</label>
//                 <input
//                   type="number"
//                   className="form-control"
//                   id="employeeId"
//                   value={employeeId}
//                   onChange={(e) => setEmployeeId(e.target.value)}
//                   required
//                 />
//               </div>
//               <div className="mb-3">
//                 <label htmlFor="userId" className="form-label">User ID</label>
//                 <input
//                   type="number"
//                   className="form-control"
//                   id="userId"
//                   value={userId}
//                   onChange={(e) => setUserId(e.target.value)}
//                   required
//                 />
//               </div>
//               <button type="submit" className="btn btn-primary">Allocate Asset</button>
//             </form>
//           </div>
//         )}
//       </div>

//       {/* Return Asset Box */}
//       <div className="card mb-4">
//         <div
//           className="card-header"
//           onClick={() => setShowReturnForm(!showReturnForm)}
//           style={{ cursor: 'pointer' }}
//         >
//           Return Asset {showReturnForm ? '▲' : '▼'}
//         </div>
//         {showReturnForm && (
//           <div className="card-body">
//             <form onSubmit={handleReturnAsset}>
//               <div className="mb-3">
//                 <label htmlFor="allocationId" className="form-label">Allocation ID</label>
//                 <input
//                   type="number"
//                   className="form-control"
//                   id="allocationId"
//                   value={allocationId}
//                   onChange={(e) => setAllocationId(e.target.value)}
//                   required
//                 />
//               </div>
//               <div className="mb-3">
//                 <label htmlFor="returnedDate" className="form-label">Returned Date</label>
//                 <input
//                   type="date"
//                   className="form-control"
//                   id="returnedDate"
//                   value={returnedDate}
//                   onChange={(e) => setReturnedDate(e.target.value)}
//                   required
//                 />
//               </div>
//               <button type="submit" className="btn btn-primary">Return Asset</button>
//             </form>
//           </div>
//         )}
//       </div>

//       {/* Update Allocation Box */}
//       <div className="card mb-4">
//         <div
//           className="card-header"
//           onClick={() => setShowUpdateForm(!showUpdateForm)}
//           style={{ cursor: 'pointer' }}
//         >
//           Update Allocation {showUpdateForm ? '▲' : '▼'}
//         </div>
//         {showUpdateForm && (
//           <div className="card-body">
//             <form onSubmit={handleUpdateAllocation}>
//               <div className="mb-3">
//                 <label htmlFor="allocationId" className="form-label">Allocation ID</label>
//                 <input
//                   type="number"
//                   className="form-control"
//                   id="allocationId"
//                   value={allocationId}
//                   onChange={(e) => setAllocationId(e.target.value)}
//                   required
//                 />
//               </div>
//               <div className="mb-3">
//                 <label htmlFor="status" className="form-label">Status</label>
//                 <select
//                   className="form-control"
//                   id="status"
//                   value={status}
//                   onChange={(e) => setStatus(e.target.value)}
//                   required
//                 >
//                   <option value="">Select Status</option>
//                   <option value="Assigned">Assigned</option>
//                   <option value="Returned">Returned</option>
//                 </select>
//               </div>
//               <button type="submit" className="btn btn-primary">Update Allocation</button>
//             </form>
//           </div>
//         )}
//       </div>

//       {/* Display Allocations Table */}
//       <div className="card">
//         <div className="card-header">All Asset Allocations</div>
//         <div className="card-body">
//           <table className="table table-striped">
//             <thead>
//               <tr>
//                 <th>Allocation ID</th>
//                 <th>Asset ID</th>
//                 <th>Employee ID</th>
//                 <th>Status</th>
//                 <th>Allocated Date</th>
//                 <th>Returned Date</th>
//               </tr>
//             </thead>
//             <tbody>
//               {allocations.map((allocation) => (
//                 <tr key={allocation.allocationId}>
//                   <td>{allocation.allocationId}</td>
//                   <td>{allocation.asset.assetId}</td>
//                   <td>{allocation.employee.employeeId}</td>
//                   <td>{allocation.status}</td>
//                   <td>{new Date(allocation.allocatedDate).toLocaleDateString()}</td>
//                   <td>{allocation.returnedDate ? new Date(allocation.returnedDate).toLocaleDateString() : 'N/A'}</td>
//                 </tr>
//               ))}
//             </tbody>
//           </table>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default AssetAllocation;

import React, { useEffect, useState } from 'react';
import {
  getAllAllocations,
  allocateAsset,
  returnAsset,
  updateAssetAllocation,
} from '../api/service/api';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../components/AssetAllocation.css';

const AssetAllocation = () => {
  const [allocations, setAllocations] = useState([]);
  const [assetId, setAssetId] = useState('');
  const [employeeId, setEmployeeId] = useState('');
  const [userId, setUserId] = useState('');
  const [allocationId, setAllocationId] = useState('');
  const [returnedDate, setReturnedDate] = useState('');
  const [status, setStatus] = useState('');
  const [message, setMessage] = useState('');
  const [showAllocateForm, setShowAllocateForm] = useState(false);
  const [showReturnForm, setShowReturnForm] = useState(false);
  const [showUpdateForm, setShowUpdateForm] = useState(false);

  // Fetch all allocations on page load
  useEffect(() => {
    const fetchAllocations = async () => {
      const data = await getAllAllocations();
      setAllocations(data);
    };
    fetchAllocations();
  }, []);

  // Handle asset allocation
  const handleAllocateAsset = async (e) => {
    e.preventDefault();
    if (!assetId || !employeeId || !userId) {
      setMessage('Please fill all fields!');
      return;
    }
    const response = await allocateAsset(assetId, employeeId, userId);
    setMessage(response);
    setAssetId('');
    setEmployeeId('');
    setUserId('');
    // Refresh allocations
    const data = await getAllAllocations();
    setAllocations(data);
  };

  // Handle asset return
  const handleReturnAsset = async (e) => {
    e.preventDefault();
    if (!allocationId || !returnedDate) {
      setMessage('Please fill all fields!');
      return;
    }
    const response = await returnAsset(allocationId, returnedDate);
    setMessage(response);
    setAllocationId('');
    setReturnedDate('');
    // Refresh allocations
    const data = await getAllAllocations();
    setAllocations(data);
  };

  // Handle allocation update
  const handleUpdateAllocation = async (e) => {
    e.preventDefault();
    if (!allocationId || !status) {
      setMessage('Please fill all fields!');
      return;
    }
    const updates = { status };
    const response = await updateAssetAllocation(allocationId, updates);
    setMessage(response);
    setAllocationId('');
    setStatus('');
    // Refresh allocations
    const data = await getAllAllocations();
    setAllocations(data);
  };

  return (
    <div className="asset-allocation-container">
      <h2>Asset Allocation</h2>

      {/* Display message */}
      {message && <div className="alert alert-info">{message}</div>}

      {/* Allocate Asset Box */}
      <div className="card mb-4">
        <div
          className="card-header"
          onClick={() => setShowAllocateForm(!showAllocateForm)}
          style={{ cursor: 'pointer' }}
        >
          Allocate Asset {showAllocateForm ? '▲' : '▼'}
        </div>
        {showAllocateForm && (
          <div className="card-body">
            <form onSubmit={handleAllocateAsset}>
              <div className="mb-3">
                <label htmlFor="assetId" className="form-label">Asset ID</label>
                <input
                  type="number"
                  className="form-control"
                  id="assetId"
                  value={assetId}
                  onChange={(e) => setAssetId(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="employeeId" className="form-label">Employee ID</label>
                <input
                  type="number"
                  className="form-control"
                  id="employeeId"
                  value={employeeId}
                  onChange={(e) => setEmployeeId(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="userId" className="form-label">User ID</label>
                <input
                  type="number"
                  className="form-control"
                  id="userId"
                  value={userId}
                  onChange={(e) => setUserId(e.target.value)}
                  required
                />
              </div>
              <button type="submit" className="btn btn-primary">Allocate Asset</button>
            </form>
          </div>
        )}
      </div>

      {/* Return Asset Box */}
      <div className="card mb-4">
        <div
          className="card-header"
          onClick={() => setShowReturnForm(!showReturnForm)}
          style={{ cursor: 'pointer' }}
        >
          Return Asset {showReturnForm ? '▲' : '▼'}
        </div>
        {showReturnForm && (
          <div className="card-body">
            <form onSubmit={handleReturnAsset}>
              <div className="mb-3">
                <label htmlFor="allocationId" className="form-label">Allocation ID</label>
                <input
                  type="number"
                  className="form-control"
                  id="allocationId"
                  value={allocationId}
                  onChange={(e) => setAllocationId(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="returnedDate" className="form-label">Returned Date</label>
                <input
                  type="date"
                  className="form-control"
                  id="returnedDate"
                  value={returnedDate}
                  onChange={(e) => setReturnedDate(e.target.value)}
                  required
                />
              </div>
              <button type="submit" className="btn btn-primary">Return Asset</button>
            </form>
          </div>
        )}
      </div>

      {/* Update Allocation Box */}
      <div className="card mb-4">
        <div
          className="card-header"
          onClick={() => setShowUpdateForm(!showUpdateForm)}
          style={{ cursor: 'pointer' }}
        >
          Update Allocation {showUpdateForm ? '▲' : '▼'}
        </div>
        {showUpdateForm && (
          <div className="card-body">
            <form onSubmit={handleUpdateAllocation}>
              <div className="mb-3">
                <label htmlFor="allocationId" className="form-label">Allocation ID</label>
                <input
                  type="number"
                  className="form-control"
                  id="allocationId"
                  value={allocationId}
                  onChange={(e) => setAllocationId(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="status" className="form-label">Status</label>
                <select
                  className="form-control"
                  id="status"
                  value={status}
                  onChange={(e) => setStatus(e.target.value)}
                  required
                >
                  <option value="">Select Status</option>
                  <option value="Assigned">Assigned</option>
                  <option value="Returned">Returned</option>
                </select>
              </div>
              <button type="submit" className="btn btn-primary">Update Allocation</button>
            </form>
          </div>
        )}
      </div>

      {/* Display Allocations Table */}
      <div className="card">
        <div className="card-header">All Asset Allocations</div>
        <div className="card-body">
          <table className="table table-striped">
            <thead>
              <tr>
                <th>Allocation ID</th>
                <th>Asset ID</th>
                <th>Employee ID</th>
                <th>Status</th>
                <th>Allocated Date</th>
                <th>Returned Date</th>
              </tr>
            </thead>
            <tbody>
              {allocations.map((allocation) => (
                <tr key={allocation.allocationId}>
                  <td>{allocation.allocationId}</td>
                  <td>{allocation.asset.assetId}</td>
                  <td>{allocation.employee.employeeId}</td>
                  <td>{allocation.status}</td>
                  <td>{new Date(allocation.allocatedDate).toLocaleDateString()}</td>
                  <td>{allocation.returnedDate ? new Date(allocation.returnedDate).toLocaleDateString() : 'N/A'}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default AssetAllocation;