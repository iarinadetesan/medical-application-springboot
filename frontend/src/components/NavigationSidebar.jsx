import '../styles/NavigationSidebar.css'
import { useState } from 'react'
function NavigationSidebar({navIsClosed}) {
    

  return (
    <div className={`navcontainer ${navIsClosed ? "navclose" : ""}`}>
      <div className="nav">
      <div className="nav-upper-options">
         
        <div className="nav-option option1">
          <h3>Dashboard</h3>
        </div>

        <div className="nav-option">
          <h3>Pacienti</h3>
        </div>

        <div className="nav-option">
          <h3>Programari</h3>
        </div>

        <div className="nav-option">
          <h3>Mesaje</h3>
        </div>
</div>
    <div className="nav-lower-options">
        <div className="nav-option">
          <h3>Profil</h3>
        </div>
        <div className="nav-option">
          <h3>Setari</h3>
        </div>
        <div className="nav-option">
          <h3>Sign out</h3>
        </div>
      </div>
      </div> 
    </div>
  )
}

export default NavigationSidebar