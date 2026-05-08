import '../styles/NavigationSidebar.css'
import { useState } from 'react'

import { useNavigate } from "react-router-dom";
import { logout } from "../services/authService";


import HomeIcon from '@mui/icons-material/Home';

import LogoutIcon from '@mui/icons-material/Logout';

import SettingsIcon from '@mui/icons-material/Settings'
import PersonIcon from '@mui/icons-material/Person';
import MedicalInformationIcon from '@mui/icons-material/MedicalInformation';
import GroupsIcon from '@mui/icons-material/Groups';

function NavigationSidebar({navIsClosed}) {
    
const navigate = useNavigate();

function handleLogout() {
  logout();
  navigate("/");
}


  return (
    <div className={`navcontainer ${navIsClosed ? "sidebar-navclose" : ""}`}>
      <div className="sidebar-nav">
      <div className="sidebar-nav-upper-options">
         
        <div className="sidebar-nav-option option1">
          <h3>
  <HomeIcon  />
  <span className="sidebar-nav-text">Dashboard</span>
      </h3>
        </div>

        <div className="sidebar-nav-option">
        <h3>  <GroupsIcon/>
          <span className="sidebar-nav-text">Pacienți</span></h3>
        </div>

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