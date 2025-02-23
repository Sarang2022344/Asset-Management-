import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useState } from "react";
import Sidebar from "./components/Sidebar";
import AssetRegistration from "./components/AssetRegistration";
import AssetAllocation from "./components/AssetAllocation";
import AssetMaintenance from "./components/AssetMaintenance";
import AssetDisposal from "./components/AssetDisposal";
import LoginForm from "./components/Login";

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleLogin = () => {
    setIsLoggedIn(true); // Set login state to true
  };

  return (
    <Router>
      {!isLoggedIn ? (
        <LoginForm onLogin={handleLogin} />
      ) : (
        <div className="flex">
          <Sidebar />
          <main className="ml-64 p-10 flex-1">
            <Routes>
              <Route path="/asset-registration" element={<AssetRegistration />} />
              <Route path="/asset-allocation" element={<AssetAllocation />} />
              <Route path="/asset-maintenance" element={<AssetMaintenance />} />
              <Route path="/asset-disposal" element={<AssetDisposal />} />
            </Routes>
          </main>
        </div>
      )}
    </Router>
  );
};

export default App;
