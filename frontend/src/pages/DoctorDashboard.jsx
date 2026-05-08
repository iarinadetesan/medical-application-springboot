import { useState, useRef, useEffect } from 'react'
import { useNavigate } from "react-router-dom";
import { getCurrentDoctorDashboard } from "../services/doctorService";

import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';
import Badge from 'react-bootstrap/Badge';

import Navbar from 'react-bootstrap/Navbar';
import NavigationSidebar from '../components/NavigationSidebar.jsx'
import ChatPanel from '../components/ChatPanel.jsx'
import ReportContainer from '../components/ReportContainer.jsx';

import '../styles/GeneralDashboard.css'
import '../styles/NavigationSidebar.css'

import DoctorConsultationsContainer from '../components/doctor-specific/DoctorConsultationsContainer';
import DoctorPatientsContainer from '../components/doctor-specific/DoctorPatientsContainer';
import DoctorPrescriptionsContainer from '../components/doctor-specific/DoctorPrescriptionsContainer';
import DoctorReferralsContainer from '../components/doctor-specific/DoctorReferralsContainer';





function DoctorDashboard() {


    const [show, setShow] = useState(false);
  const target = useRef(null);
  const [navIsClosed, setNavIsClosed] = useState(false);

  function handleNav (){
    setNavIsClosed(!navIsClosed);
  };

  const navigate = useNavigate();
  
    const [dashboard, setDashboard] = useState(null);
    const [loading, setLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState("");
  


   async function fetchDashboard() {
  try {
    const data = await getCurrentDoctorDashboard();
    console.log("Doctor dashboard:", data);
    setDashboard(data);
    setErrorMessage("");
  } catch (error) {
    console.error("Eroare la încărcarea dashboard-ului:", error);

    if (error.response) {
      setErrorMessage(error.response.data);
    } else {
      setErrorMessage("Nu s-au putut încărca datele dashboard-ului.");
    }
  } finally {
    setLoading(false);
  }
}

useEffect(() => {
  fetchDashboard();

  const intervalId = setInterval(() => {
    fetchDashboard();
  }, 30000);

  return () => {
    clearInterval(intervalId);
  };
}, []);
  if (loading) {
  return <div>Se încarcă dashboard-ul...</div>;
}

if (errorMessage) {
  return <div>{errorMessage}</div>;
}
    const doctor = dashboard?.doctor;
    const summary = dashboard?.summary;


  return (
    <>
      
    <Navbar sticky="top" expand="lg" bg="primary" data-bs-theme="dark" >
      <Container >
        <Button className={` btn btn-primary ${navIsClosed ? "active" : ""}`} onClick={handleNav}>
            <i className="material-icons">dehaze</i></Button>
        <Navbar.Brand href="#home">MedicalApp</Navbar.Brand>
        
        <Navbar.Toggle />
        <Navbar.Collapse className="justify-content-end">
            
          <Navbar.Text>
            Signed in as: <a href="#login">{doctor?.firstName} {doctor?.lastName}</a>
          </Navbar.Text>
            
        </Navbar.Collapse>
      </Container>
    </Navbar>
    
    <div className="d-flex flex-column flex-md-row " style={{ height: "calc(100vh - 56px)", minWidth: "100vw", overflow: "hidden" }}>
        
        <NavigationSidebar navIsClosed={navIsClosed}/>
        
        <div className="main d-flex flex-column flex-md-row flex-grow-1 hide-scrollbar">
            
              <div className="report-section d-flex flex-column flex-md-row hide-scrollbar">
            <DoctorPatientsContainer objects={dashboard.patients}  />
            <DoctorConsultationsContainer objects={dashboard.recentConsultations} patients={dashboard.patients} refreshDashboard={fetchDashboard}/>
            <DoctorPrescriptionsContainer
  objects={dashboard.prescriptions}
  patients={dashboard.patients} refreshDashboard={fetchDashboard}
/>
            <DoctorReferralsContainer objects={dashboard.referrals} patients={dashboard.patients} refreshDashboard={fetchDashboard}/>
            
        </div>
              
<ChatPanel/>
        </div>
        
    </div>
    
      

    </>
  )
}


export default DoctorDashboard
