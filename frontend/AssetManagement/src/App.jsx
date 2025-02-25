import { BrowserRouter as Router, Routes, Route,Navigate } from "react-router-dom";
import { useState } from "react";
import Sidebar from "./components/Sidebar";
import EmployeeSidebar from "./components/EmployeeSideBar";
import AddAssetRegistration  from "./components/AssetRegistration";
import AssetAllocation from "./components/AssetAllocation";
import AssetMaintenance from "./components/AssetMaintenance";
import AssetDisposal from "./components/AssetDisposal";
import LoginForm from "./components/Login";
import 'bootstrap/dist/css/bootstrap.min.css';
import Dashboard from "./components/dashboard";
import EmployeeDashboard from "./components/EmployeeDashboard";
import RaiseTicket from "./components/RaiseTicket";
import ViewRaisedTicket from "./components/ViewRaisedTicket";




const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userRole, setUserRole] = useState(null); // "admin" or "employee"

  const handleLogin = (role) => {
    setIsLoggedIn(true);
    setUserRole(role);
  };

  return (
    <Router>
      {!isLoggedIn ? (
        <LoginForm onLogin={handleLogin} />
      ) : (
        <div className="flex">
          {userRole === "admin" ? <Sidebar /> : <EmployeeSidebar />}
          <main className="ml-64 p-10 ">
            <Routes>
              {userRole === "admin" ? (
                <>
                  <Route path="/asset-dashboard" element={<Dashboard />} />
                  <Route path="/asset-registration" element={<AddAssetRegistration />} />
                  <Route path="/asset-allocation" element={<AssetAllocation />} />
                  <Route path="/asset-maintenance" element={<AssetMaintenance />} />
                  <Route path="/asset-disposal" element={<AssetDisposal />} />
                  <Route path="*" element={<Navigate to="/asset-dashboard" />} />
                </>
              ) : (
                <>
                  <Route path="/employee-dashboard" element={<EmployeeDashboard />} />
                  <Route path="/employee-asset-allocation" element={<RaiseTicket />} />
                  <Route path="/employee-view-raisedtickets" element={<ViewRaisedTicket />} />
                  <Route path="*" element={<Navigate to="/employee-dashboard" />} />
                </>
              )}
            </Routes>
          </main>
        </div>
      )}
    </Router>
  );
};

export default App;
