import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Offcanvas from 'react-bootstrap/Offcanvas';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import Table from 'react-bootstrap/Table';
import "../../styles/ReportContainerTable.css"
import AddDocumentModal from '../AddDocumentModal';

import { getStatusBadgeVariant } from '../../assets/utils/StatusBadgeUtils';

export default function DoctorConsultationsContainer({ objects = [], patients = [],refreshDashboard }) {

const [show, setShow] = useState(false);
    const [fullscreen, setFullscreen] = useState(true);
    const [selectedItemId, setSelectedItemId] = useState(null);
  
    function handleItemSelect(id){
      if (selectedItemId === id) {
      setSelectedItemId(null);
    } else {
      setSelectedItemId(id);
    }
    }
  
    function handleShow(breakpoint) {
      setFullscreen(breakpoint);
      setShow(true);
    }
    function handleClose() {
    setShow(false);
    setSelectedItemId(null);
  }


    return(
<>
        <div className="report-container table-responsive">
                <div className="report-header">
                    <h1 className="recent-Articles">Consultații recente</h1>
                    <Button onClick={() => handleShow('md-down')}
                     className="view">Vezi tot</Button>
                </div>
                        
            
            <Table responsive className="report-body" striped bordered hover>
            <thead >
              <tr >
                <th className="t-op ">Data</th>
                <th className="t-op">Pacient</th>
                <th  className="t-op">Diagnostic</th>
                <th  className="t-op">Scop / Detalii</th>
              </tr>
            </thead>
            <tbody className="items">
              {objects.map((consultation) => (
                <tr className="item" key={consultation.id}>
                  
                  <td className="t-op-nextlvl">{consultation.consultationDate.substring(0,10) || "-"}</td>
                  <td className="t-op-nextlvl">
                    {consultation.patientName}
                  </td>
                  <td className="t-op-nextlvl">
                    {
                      consultation.diagnosis ||
                      "-"}
                  </td>
                  <td className="t-op-nextlvl">
                    {consultation.notes}
                  </td>
                </tr>
              ))}
            </tbody>
          </Table> 
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
            Retete medicale
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
         
          <Table responsive className="report-body" striped bordered hover>
            <thead >
              <tr >
                <th className="t-op ">Data</th>
                <th className="t-op">Pacient</th>
                <th  className="t-op">Diagnostic</th>
                <th  className="t-op">Scop / Detalii</th>

              </tr>
            </thead>
            <tbody className="items">
              {objects.map((consultation) => (
                <tr key={consultation.id}
                onClick={() => handleItemSelect(consultation.id)}
                className={`item ${selectedItemId === consultation.id ? "selected-row" : ""}`}
                style = {{cursor: "pointer"}}

                
                  

                >
                  
              
                  <td className="t-op-nextlvl">{consultation.consultationDate.substring(0,10) || "-"}</td>
                  <td className="t-op-nextlvl">
                    {consultation.patientName}
                  </td>
                  <td className="t-op-nextlvl">
                    {
                      consultation.diagnosis ||
                      "-"}
                  </td>
                  <td className="t-op-nextlvl">
                    {consultation.notes}
                  </td>
                 
                </tr>
              ))}
            </tbody>
          </Table>
         
      <div className="d-flex gap-2 mt-3">  <Button
  disabled={!selectedItemId}
  onClick={() => {
    const selectedItem = objects.find(
      (item) => item.id === selectedItemId
    );

    console.log("Item selectat:", selectedItem);
  }}
>
  Modifică
</Button>
<AddDocumentModal patients = {patients} refreshDashboard={refreshDashboard}/></div>
        </Modal.Body>
      </Modal>
        </>        
    );
}