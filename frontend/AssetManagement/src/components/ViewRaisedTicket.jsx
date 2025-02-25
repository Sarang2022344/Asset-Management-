import React from 'react'
import { useState, useEffect } from "react";
import axios from "axios";
import "react-toastify/dist/ReactToastify.css";

import "../components/AssetMaintenance.css";



const ViewRaisedTicket = () => {
    const [tickets, setTickets] = useState([
        { id: 1, assetName: "Laptop", issueDescription: "Screen flickering", createdAt: "2024-02-25", status: "Pending" },
        { id: 2, assetName: "Monitor", issueDescription: "No display", createdAt: "2024-02-24", status: "Pending" }
      ]);
    
      return (
        <div className="asset-allocation-container">
          <h2>All Raised Tickets</h2>
          <div className="card">
            <div className="card-body">
              <table className="table table-striped maintenance-table">
                <thead>
                  <tr>
                    <th>Asset Name</th>
                    <th>Issue Description</th>
                    <th>Created At</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {tickets.map((ticket) => (
                    <tr key={ticket.id}>
                      <td>{ticket.assetName}</td>
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

}

export default ViewRaisedTicket;