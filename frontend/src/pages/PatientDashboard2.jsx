import { useState, useRef, useEffect } from 'react'
import { useNavigate } from "react-router-dom";
import { getCurrentPatientDashboard } from "../services/patientService";

import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';
import Badge from 'react-bootstrap/Badge';

import Navbar from 'react-bootstrap/Navbar';
import NavigationSidebar from '../components/NavigationSidebar'
import ChatPanel from '../components/ChatPanel'
import ReportContainer from '../components/ReportContainer';

import '../styles/GeneralDashboard.css'
import PatientConsultationsContainer from '../components/patient-specific/PatientConsultationsContainer';
import PatientPrescriptionsContainer from '../components/patient-specific/PatientPrescriptionsContainer';
import PatientReferralsContainer from '../components/patient-specific/PatientReferralsContainer';

function PatientDashboard() {


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
  
    useEffect(() => {
      const fetchDashboard = async () => {
        try {
          const data = await getCurrentPatientDashboard();
          console.log("Patient dashboard:", data);
          setDashboard(data);
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
      };
  
      fetchDashboard();
    }, []);
  
    const handleLogout = () => {
      localStorage.clear();
      navigate("/");
    };
  
    if (loading) {
      return <div >Se încarcă dashboard-ul...</div>;
    }
  
    if (errorMessage) {
      return (
        <div >
          <h1>Dashboard Pacient</h1>
          <p >{errorMessage}</p>
          <button onClick={handleLogout} >
            Logout
          </button>
        </div>
      );
    }
  
    const patient = dashboard?.patient;
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
            Signed in as: <a href="#login">{patient?.firstName} {patient?.lastName}</a>
          </Navbar.Text>
            
        </Navbar.Collapse>
      </Container>
    </Navbar>
    
    <div className="d-flex flex-column flex-md-row " style={{ minHeight: "100vh", minWidth: "100vw" }}>
        
        <NavigationSidebar navIsClosed={navIsClosed}/>
        
        <div className="main d-flex flex-column flex-md-row flex-grow-1">
            
              <div className="report-section d-flex flex-column flex-md-row">
            <PatientConsultationsContainer objects={dashboard.recentConsultations}/>
            <PatientPrescriptionsContainer objects={dashboard.prescriptions}/>
            <PatientReferralsContainer objects={dashboard.referrals}/>
            
        </div>
              
<ChatPanel/>
        </div>
        
    </div>
    
      

    </>
  )
}


export default PatientDashboard
