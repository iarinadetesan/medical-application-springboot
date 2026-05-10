import '../styles/NavigationSidebar.css'
import { useState } from 'react'

import { useLocation , useNavigate } from "react-router-dom";
import { logout , getRole} from "../services/authService";


import HomeIcon from '@mui/icons-material/Home';

import LogoutIcon from '@mui/icons-material/Logout';

import SettingsIcon from '@mui/icons-material/Settings'
import PersonIcon from '@mui/icons-material/Person';
import MedicalInformationIcon from '@mui/icons-material/MedicalInformation';
import GroupsIcon from '@mui/icons-material/Groups';

function NavigationSidebar({navIsClosed}) {
    
const navigate = useNavigate();
const location = useLocation();

const role = getRole();

function getOptionClass(path) {
  return `sidebar-nav-option ${
    location.pathname === path
      ? "option1"
      : ""
  }`;
}

function handleLogout() {
  logout();
  navigate("/");
}

function handlePatientsClick() {
  navigate("/doctor/enrollment-requests");
}

function handleMedicalHistoryClick() {
  navigate ("/patient");
}
function handleDashboardClick() {
  if (role === "DOCTOR") {
    navigate("/doctor");
  } else if (role === "PATIENT") {
    navigate("/patient");
  } else if (role === "ADMIN") {
    navigate("/admin");
  }
}





  return (
    <div className={`navcontainer ${navIsClosed ? "sidebar-navclose" : ""}`}>
      <div className="sidebar-nav">
      <div className="sidebar-nav-upper-options">
         
        <div 
        className={getOptionClass(role === "DOCTOR" ? "/doctor" : "/patient")}
        
        onClick={handleDashboardClick}>
  <h3>
    <HomeIcon />
    <span className="sidebar-nav-text">Dashboard</span>
  </h3>
</div>

{role === "DOCTOR" && (
  <div 
  className={getOptionClass("/doctor/enrollment-requests")}
   onClick={handlePatientsClick}>
    <h3>
      <GroupsIcon />
      <span className="sidebar-nav-text">Pacienți</span>
    </h3>
  </div>
)}

{role === "PATIENT" && (
  <div 
  className={getOptionClass("/patient/medical-history")} 
  onClick={handleMedicalHistoryClick}>
    <h3>
      <MedicalInformationIcon />
      <span className="sidebar-nav-text">Istoric Medical</span>
    </h3>
  </div>
)}


</div>
    <div className="sidebar-nav-lower-options">
        <div className="sidebar-nav-option">
          <h3> <PersonIcon/>
            
            <span className="sidebar-nav-text">Profil</span>
            </h3>
        </div>
        <div className="sidebar-nav-option">
          <h3>
  <SettingsIcon />
  <span className="sidebar-nav-text">Setări</span>
</h3>
        </div>
        <div className="sidebar-nav-option" onClick = {handleLogout}>
          <h3> <LogoutIcon/> 
          <span className="sidebar-nav-text">Sign out</span>
          
          
          </h3>
        </div>
      </div>
      </div> 
    </div>
  )
}

export default NavigationSidebar