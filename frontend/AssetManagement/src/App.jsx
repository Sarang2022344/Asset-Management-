import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { useState, useEffect } from "react";
import Sidebar from "./components/Sidebar";
import EmployeeSidebar from "./components/EmployeeSideBar";
import AddAssetRegistration from "./components/AssetRegistration";
import AssetAllocation from "./components/AssetAllocation";
import AssetMaintenance from "./components/AssetMaintenance";
import AssetDisposal from "./components/AssetDisposal";
import LoginForm from "./components/Login";
import "bootstrap/dist/css/bootstrap.min.css";
import Dashboard from "./components/dashboard";
import EmployeeDashboard from "./components/EmployeeDashboard";
import RaiseTicket from "./components/RaiseTicket";
import ViewRaisedTicket from "./components/ViewRaisedTicket";
import { useNavigate } from "react-router-dom";

// 🔥 Wrapper to Handle Protected Routes
const ProtectedRoutes = ({ isLoggedIn, userRole, handleLogout }) => {
  const navigate = useNavigate(); // ✅ Now useNavigate() is inside Router context

  useEffect(() => {
    if (!isLoggedIn) {
      navigate("/"); // Redirect to login if not logged in
    }
  }, [isLoggedIn, navigate]);

  return isLoggedIn ? (
    <div className="flex">
      {userRole === "admin" ? <Sidebar onLogout={handleLogout} /> : <EmployeeSidebar onLogout={handleLogout} />}
      <main className="ml-64 p-10">
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
  ) : null;
};

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userRole, setUserRole] = useState(null); // "admin" or "employee"

  // Check localStorage on first render to persist login state
  useEffect(() => {
    const storedRole = localStorage.getItem("userRole");
    if (storedRole) {
      setIsLoggedIn(true);
      setUserRole(storedRole);
    }
  }, []);

  const handleLogin = (role) => {
    setIsLoggedIn(true);
    setUserRole(role);
    localStorage.setItem("userRole", role); // Store role in localStorage
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
    setUserRole(null);
    localStorage.removeItem("userRole"); // Remove from localStorage on logout
  };

  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginForm onLogin={handleLogin} />} />
        <Route
          path="*"
          element={<ProtectedRoutes isLoggedIn={isLoggedIn} userRole={userRole} handleLogout={handleLogout} />}
        />
      </Routes>
    </Router>
  );
};

export default App;
