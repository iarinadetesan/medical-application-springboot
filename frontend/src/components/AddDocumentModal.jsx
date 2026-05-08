import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';


import PrescriptionForm from './forms/PrescriptionForm.jsx';
import ReferralForm from './forms/ReferralForm.jsx';
import ConsultationForm from './forms/ConsultationForm.jsx';

function ControlledTabsExample({ patients=[],refreshDashboard}) {
  const [key, setKey] = useState('prescription');

  return (
    <Tabs
      id="controlled-tab-example"
      activeKey={key}
      onSelect={(k) => setKey(k)}
      className="mb-3"
    >
      <Tab eventKey="prescription" title="Rețetă">
        <PrescriptionForm patients = {patients}  refreshDashboard={refreshDashboard}/>
      </Tab>
      <Tab eventKey="referral" title="Trimitere">
        <ReferralForm patients = {patients}  refreshDashboard={refreshDashboard}/>
      </Tab>
      <Tab eventKey="contact" title="Consultație" >
        <ConsultationForm patients = {patients} refreshDashboard={refreshDashboard}/>
      </Tab>
      <Tab eventKey="document" title="Alt Document" disabled>
        Tab content pt document.
      </Tab>
    </Tabs>
  );
}



function AddDocumentModal({patients = [], refreshDashboard}) {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <>
      <Button variant="primary" onClick={handleShow}>
        Adaugă
      </Button>

      <Modal show={show} onHide={handleClose}
      size="lg">
        <Modal.Header closeButton>
          <Modal.Title>Documente medicale</Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <ControlledTabsExample patients={patients} refreshDashboard={refreshDashboard}/>

        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Închide
          </Button>
          
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default AddDocumentModal;