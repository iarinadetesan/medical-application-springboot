import { useState, useRef } from 'react'


import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';
import Badge from 'react-bootstrap/Badge';

import Navbar from 'react-bootstrap/Navbar';
import NavigationSidebar from '../components/NavigationSidebar'
import ChatPanel from '../components/ChatPanel'
import ReportContainer from '../components/ReportContainer';
import AddDocumentModal from '../components/AddDocumentModal';
import '../styles/GeneralDashboard.css'
import { Add } from '@mui/icons-material';


function GeneralDashboard() {


    const [show, setShow] = useState(false);
  const target = useRef(null);
  const [navIsClosed, setNavIsClosed] = useState(false);

  function handleNav (){
    setNavIsClosed(!navIsClosed);
  };



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
            Signed in as: <a href="#login">Mark Otto</a>
          </Navbar.Text>
            
        </Navbar.Collapse>
      </Container>
    </Navbar>
    
    <div className="d-flex flex-column flex-md-row " style={{ minHeight: "100vh", minWidth: "100vw" }}>
        
        <NavigationSidebar navIsClosed={navIsClosed}/>
        
        <div className="main d-flex flex-column flex-md-row flex-grow-1">
            
              <div className="report-section d-flex flex-column flex-md-row">
            <AddDocumentModal/>
            <ReportContainer/>
            <ReportContainer/>
            <ReportContainer/>
            <ReportContainer/>
        </div>
        {/*<div className="side-section">
        
          <div className="chat-box-container">

            <div className="chat-box-header">
              <h3>Mesaje</h3>
            </div>
            <div className="chat-box-body">
              <h4>Buna ziua!</h4>
              <h4>Buna ziua!</h4>
              <h4>Buna ziua!</h4>
              <h4>Buna ziua!</h4>
              <h4>Buna ziua!</h4>
            </div>
            
          
          </div>
          <div className="chat-box-container">

            <div className="chat-box-header">
              <h3>Mesaje</h3>
            </div>
            <div className="chat-box-body">
              <h4>Buna ziua!</h4>
              <h4>Buna ziua!</h4>
              <h4>Buna ziua!</h4>
              <h4>Buna ziua!</h4>
            </div>
            
          
           
        </div>
        </div>*/}
              
<ChatPanel/>
        </div>
        
    </div>
    
      

    </>
  )
}

export default GeneralDashboard
