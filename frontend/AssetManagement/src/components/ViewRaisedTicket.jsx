import React, { useState, useEffect } from "react";
import axios from "axios";
import "react-toastify/dist/ReactToastify.css";
import "../components/AssetMaintenance.css";

const ViewRaisedTicket = () => {
  const [tickets, setTickets] = useState([]);
  const [assetNames, setAssetNames] = useState({}); // Store asset names separately
  const employeeId = 4; // Hardcoded Employee ID for now

  useEffect(() => {
    fetchTickets();
  }, []);

  const fetchTickets = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/tickets/employee/${employeeId}`);
      console.log("Tickets Response:", response.data);
      setTickets(response.data);

      // Fetch asset names separately
      response.data.forEach((ticket) => fetchAssetName(ticket.assetId));
    } catch (error) {
      console.error("Error fetching tickets:", error);
    }
  };

  const fetchAssetName = async (assetId) => {
    try {
      const response = await axios.get(`http://localhost:8080/api/registration/get/${assetId}`);
      setAssetNames((prev) => ({ ...prev, [assetId]: response.data.name })); // Store asset name
    } catch (error) {
      console.error(`Error fetching asset name for ID ${assetId}:`, error);
    }
  };

  return (
    <div className="asset-allocation-container">
      <h2>All Raised Tickets</h2>
      <div className="card">
        <div className="card-body">
          <table className="table table-striped maintenance-table">
            <thead>
              <tr>
                <th>Ticket ID</th>
                <th>Asset Name</th>
                <th>Issue Description</th>
                <th>Created At</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {tickets.map((ticket) => (
                <tr key={ticket.ticketId}>
                  <td>{ticket.ticketId}</td>
                  <td>{assetNames[ticket.assetId] || "Loading..."}</td> {/* Fetch asset name */}
                  <td>{ticket.issueDescription}</td>
                  <td>{ticket.createdAt}</td>
                  <td>{ticket.status}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default ViewRaisedTicket;
