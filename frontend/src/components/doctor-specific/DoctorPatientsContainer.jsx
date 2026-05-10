import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Offcanvas from 'react-bootstrap/Offcanvas';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import Table from 'react-bootstrap/Table';
import Badge from 'react-bootstrap/Badge';
import "../../styles/ReportContainerTable.css"
export default function DoctorPatientsContainer({objects}) {
  const [show, setShow] = useState(false);
  const [fullscreen, setFullscreen] = useState(true);
  const [selectedPatientId, setSelectedPatientId] = useState(null);

  function handlePatientSelect(patientId){
    if (selectedPatientId === patientId) {
    setSelectedPatientId(null);
  } else {
    setSelectedPatientId(patientId);
  }
  }

  function handleShow(breakpoint) {
    setFullscreen(breakpoint);
    setShow(true);
  }
  function handleClose() {
  setShow(false);
  setSelectedPatientId(null);
}

  
    return(
<>
        <div className="report-container table-responsive">
                <div className="report-header">
                    <h1 className="recent-Articles">Pacienți</h1>
                    <Button onClick={() => handleShow('md-down')}
                     className="view">Vezi tot</Button>
                </div>
                        
              {objects.length === 0 ? (
            <div className="report-body" >
  <p className="empty-message">Nu există pacienți.</p> </div>
) : (  
            <Table responsive className="report-body" striped bordered hover>
            <thead >
              <tr >
                <th className="t-op ">Nume</th>
                <th className="t-op ">Prenume</th>
                <th className="t-op ">CNP</th>
                <th className="t-op">Domiciliu</th>
                

              </tr>
            </thead>
            <tbody className="items">
              {objects.map((patient) => (
                <tr className="item" key={patient.id}>
                  
              
                  <td className="t-op-nextlvl"> {patient.lastName}</td>
                  <td className="t-op-nextlvl">{patient.firstName}</td>
                  <td className="t-op-nextlvl">{patient.cnp}</td>
                  <td className="t-op-nextlvl">{patient.address}</td>
                  
                 
                </tr>
              ))}
            </tbody>
          </Table> )}
                    </div>

        <Modal
        show={show}
        fullscreen={fullscreen}
        onHide={handleClose}
        dialogClassName="modal-100w"
        aria-labelledby="example-custom-modal-styling-title"
        size="lg"
      >
        <Modal.Header closeButton>
          <Modal.Title id="example-custom-modal-styling-title">
            Pacienți
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {objects.length === 0 ? (
            <div className="report-body" >
  <p className="empty-message">Nu există pacienți înscriși. 
    Pentru a accepta cererea de înscriere a unui pacient, navigați la secțiunea „Pacienți” din meniul din stânga paginii.</p> </div>
) : (
          <Table striped bordered hover
          
          >
            <thead >
              <tr >
                <th className="t-op ">Nume</th>
                <th className="t-op ">Prenume</th>
                <th className="t-op ">CNP</th>
                <th className="t-op">Domiciliu</th>
                

              </tr>
            </thead>
            <tbody className="items">
              {objects.map((patient) => (
                <tr 
                key={patient.id}
                onClick={() => handlePatientSelect(patient.id)}
                className={`item ${selectedPatientId === patient.id ? "selected-row" : ""}`}
                style = {{cursor: "pointer"}}
                
                >
                  
              
                  <td className="t-op-nextlvl"> {patient.lastName}</td>
                  <td className="t-op-nextlvl">{patient.firstName}</td>
                  <td className="t-op-nextlvl">{patient.cnp}</td>
                  <td className="t-op-nextlvl">{patient.address}</td>
                  
                 
                </tr>
              ))}
            </tbody>
          </Table> )}
          <div className="d-flex gap-2 mt-3">
        <Button
  disabled={!selectedPatientId}
  onClick={() => {
    const selectedPatient = objects.find(
      (patient) => patient.id === selectedPatientId
    );

    console.log("Pacient selectat:", selectedPatient);
  }}
>
  Modifică
</Button></div>
        </Modal.Body>
      </Modal>
       </>         
    );
}