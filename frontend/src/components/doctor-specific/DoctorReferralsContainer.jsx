import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Offcanvas from 'react-bootstrap/Offcanvas';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';

import Table from 'react-bootstrap/Table';
import Badge from 'react-bootstrap/Badge';

import "../../styles/ReportContainerTable.css"
import AddDocumentModal from '../AddDocumentModal';

import { getStatusBadgeVariant } from '../../assets/utils/StatusBadgeUtils';

export default function DoctorReferralsContainer({ objects = [], patients = [], refreshDashboard }) {

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
                    <h1 className="recent-Articles">Trimiteri medicale</h1>
                    <Button onClick={() => handleShow('md-down')}
                     className="view">Vezi tot</Button>
                </div>
                        
              {objects.length === 0 ? (
            <div className="report-body" >
  <p className="empty-message">Nu există trimiteri medicale.</p> </div>
) : (  
            <Table responsive className="report-body" striped bordered hover>
            <thead >
              <tr >
                <th className="t-op ">Data</th>
                <th className="t-op">Pacient</th>
                <th  className="t-op">Specializare</th>
                <th  className="t-op">Motiv / Detalii</th>
                <th  className="t-op">Valabil până la data de</th>
                <th className="t-op ">Status</th>
              </tr>
            </thead>
            <tbody className="items">
              {objects.map((referral) => (
                <tr className="item" key={referral.id}>
                  
                  
                 
                  
                  <td className="t-op-nextlvl">{referral.issueDate.substring(0,10) || "-"}</td>
                  
                  <td className="t-op-nextlvl">
                    {referral.patientName}
                  </td>
                  <td className="t-op-nextlvl">{referral.specialization || "Fără specializare"}</td>
                  <td className="t-op-nextlvl">
                    {referral.reason}
                  </td>
                  <td className="t-op-nextlvl">{referral.expiryDate.substring(0,10)}</td>
                  <td className="t-op-nextlvl"> <Badge bg={getStatusBadgeVariant(referral.status)}>
                   {referral.status || "Fără status"}</Badge>
                  </td>
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
            Trimiteri medicale
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
         {objects.length === 0 ? (
            <div className="report-body" >
  <p className="empty-message">Nu există trimiteri medicale. Apăsați butonul „Adaugă” pentru a crea o nouă trimitere.</p> </div>
) : (
          <Table responsive className="report-body" striped bordered hover>
            <thead >
              <tr >
               <th className="t-op ">Data</th>
                <th className="t-op">Pacient</th>
                <th  className="t-op">Specializare</th>
                <th  className="t-op">Motiv / Detalii</th>
                <th  className="t-op">Valabil până la data de</th>
                <th className="t-op ">Status</th>

              </tr>
            </thead>
            <tbody className="items">
              {objects.map((referral) => (
                <tr key={referral.id}
                onClick={() => handleItemSelect(referral.id)}
                className={`item ${selectedItemId === referral.id ? "selected-row" : ""}`}
                style = {{cursor: "pointer"}}>
                  
              
                  <td className="t-op-nextlvl">{referral.issueDate.substring(0,10) || "-"}</td>
                  
                  <td className="t-op-nextlvl">
                    {referral.patientName}
                  </td>
                  <td className="t-op-nextlvl">{referral.specialization || "Fără specializare"}</td>
                  <td className="t-op-nextlvl">
                    {referral.reason}
                  </td>
                  <td className="t-op-nextlvl">{referral.expiryDate.substring(0,10)}</td>
                  <td className="t-op-nextlvl"> <Badge bg={getStatusBadgeVariant(referral.status)}>
                   {referral.status || "Fără status"}</Badge>
                  </td>
                 
                </tr>
              ))}
            </tbody>
          </Table>)}
          
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