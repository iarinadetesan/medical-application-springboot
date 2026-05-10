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

import { getAllDoctors } from "../services/doctorService";
import { createEnrollmentRequest, getMyEnrollmentRequests } from "../services/enrollmentService";

import Form from 'react-bootstrap/Form';
import Alert from 'react-bootstrap/Alert';

import { getStatusBadgeVariant } from '../assets/utils/statusBadgeUtils';

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

    const [doctors, setDoctors] = useState([]);
const [enrollmentRequests, setEnrollmentRequests] = useState([]);
const [selectedDoctorId, setSelectedDoctorId] = useState("");


const [enrollmentMessage, setEnrollmentMessage] = useState("");
const [enrollmentStatus, setEnrollmentStatus] = useState({ type: "", text: "" });
const [isSubmittingEnrollment, setIsSubmittingEnrollment] = useState(false);

const enrollmentStatusLabels = {
  PENDING: "În așteptare",
  APPROVED: "Aprobată",
  REJECTED: "Respinsă",
};

  
    useEffect(() => {
      const fetchDashboard = async () => {
        try {
          const data = await getCurrentPatientDashboard();
          console.log("Patient dashboard:", data);
          setDashboard(data);

          if (!data?.patient?.familyDoctorId) {
            const [doctorList, requests] = await Promise.all([
            getAllDoctors(),
            getMyEnrollmentRequests(),
  ]);

  setDoctors(doctorList);
  setEnrollmentRequests(requests);
}


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

    const needsFamilyDoctor = !patient?.familyDoctorId;

const pendingEnrollmentRequest = enrollmentRequests.find(
  (request) => request.status === "PENDING"
);
const pendingEnrollmentDoctor = doctors.find(
  (doctor) => doctor.id === pendingEnrollmentRequest?.doctorId
);
const selectedDoctor = doctors.find(
  (doctor) => String(doctor.id) === String(selectedDoctorId)
);



const handleEnrollmentSubmit = async (event) => {
  event.preventDefault();
  setEnrollmentStatus({ type: "", text: "" });

  if (!selectedDoctorId) {
    setEnrollmentStatus({
      type: "warning",
      text: "Selectează un medic pentru a trimite cererea.",
    });
    return;
  }

  try {
    setIsSubmittingEnrollment(true);

    const createdRequest = await createEnrollmentRequest({
      doctorId: Number(selectedDoctorId),
      message: enrollmentMessage,
    });

    setEnrollmentRequests((requests) => [createdRequest, ...requests]);
    setEnrollmentMessage("");

    setEnrollmentStatus({
      type: "success",
      text: `Cererea a fost trimisă către Dr. ${selectedDoctor?.firstName ?? ""} ${selectedDoctor?.lastName ?? ""}.`,
    });
  } catch (error) {
    setEnrollmentStatus({
      type: "danger",
      text: error.response?.data || "Nu s-a putut trimite cererea de înscriere.",
    });
  } finally {
    setIsSubmittingEnrollment(false);
  }
};


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
    
    <div className="d-flex flex-column flex-md-row " style={{ height: "calc(100vh - 56px)", minWidth: "100vw", overflow: "hidden" }}>
        
        <NavigationSidebar navIsClosed={navIsClosed}/>
        
        <div className="main d-flex flex-column flex-md-row flex-grow-1 hide-scrollbar">
            
              <div className="report-section d-flex flex-column flex-md-row hide-scrollbar">

{needsFamilyDoctor ? (
  <div className="enrollment-card">
    <div className="enrollment-card-header">
      <h2>Înscrie-te la un medic</h2>
      <p>Alege medicul de familie și trimite o cerere de înscriere.</p>
    </div>

    {pendingEnrollmentRequest ? (
      <>
      <Alert variant="primary" className="mb-0 enrollment-request-status">
    <strong>Ai deja o cerere de înscriere în așteptare.</strong>

    <div>
      Medic:{" "}
      {pendingEnrollmentDoctor
        ? `Dr. ${pendingEnrollmentDoctor.firstName} ${pendingEnrollmentDoctor.lastName}`
        : `ID medic: ${pendingEnrollmentRequest.doctorId}`}
    </div>

    <div>
      Status:
      <Badge bg={getStatusBadgeVariant(pendingEnrollmentRequest.status)}> {enrollmentStatusLabels[pendingEnrollmentRequest.status]}
    </Badge></div>

    {pendingEnrollmentRequest.createdAt && (
      <div>
        Trimisă la:{" "}
        {new Date(pendingEnrollmentRequest.createdAt).toLocaleString("ro-RO")}
      </div>
    )}
  </Alert>
  <Button>Sterge cererea</Button>
  
      </>
      
    ) : (
      <Form onSubmit={handleEnrollmentSubmit} className="enrollment-form">
        {enrollmentStatus.text && (
          <Alert variant={enrollmentStatus.type}>{enrollmentStatus.text}</Alert>
        )}

        <Form.Group controlId="doctorSelect">
          <Form.Label>Medic</Form.Label>
          <Form.Select
            value={selectedDoctorId}
            onChange={(event) => setSelectedDoctorId(event.target.value)}
            disabled={isSubmittingEnrollment || doctors.length === 0}
          >
            <option value="">
              {doctors.length === 0 ? "Nu există medici disponibili" : "Selectează medicul"}
            </option>

            {doctors.map((doctor) => (
              <option key={doctor.id} value={doctor.id}>
                Dr. {doctor.firstName} {doctor.lastName}
                {doctor.cabinetAddress ? ` - ${doctor.cabinetAddress}` : ""}
              </option>
            ))}
          </Form.Select>
        </Form.Group>

        <Form.Group controlId="enrollmentMessage">
          <Form.Label>Mesaj opțional</Form.Label>
          <Form.Control
            as="textarea"
            rows={3}
            value={enrollmentMessage}
            onChange={(event) => setEnrollmentMessage(event.target.value)}
            placeholder="Scrie un mesaj pentru medic, dacă dorești."
            disabled={isSubmittingEnrollment}
          />
        </Form.Group>

        <Button
          type="submit"
          variant="primary"
          disabled={isSubmittingEnrollment || doctors.length === 0}
        >
          {isSubmittingEnrollment ? "Se trimite..." : "Trimite cererea"}
        </Button>
      </Form>
    )}
  </div>
) : (

          <>
            <PatientConsultationsContainer objects={dashboard.recentConsultations}/>
            <PatientPrescriptionsContainer objects={dashboard.prescriptions}/>
            <PatientReferralsContainer objects={dashboard.referrals}/>
          </>  )}
        </div>
              
<ChatPanel/>
        </div>
        
    </div>
    
      

    </>
  )
}


export default PatientDashboard
