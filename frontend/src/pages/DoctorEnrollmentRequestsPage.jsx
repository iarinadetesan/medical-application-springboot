import { useEffect, useState } from "react";

import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";
import Badge from "react-bootstrap/Badge";
import Spinner from "react-bootstrap/Spinner";
import Table from "react-bootstrap/Table";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";


import NavigationSidebar from "../components/NavigationSidebar.jsx";
import { getDoctorEnrollmentRequests , approveEnrollmentRequest , rejectEnrollmentRequest } from "../services/enrollmentService";
import { getCurrentDoctorDashboard } from "../services/doctorService";
import { getStatusBadgeVariant } from "../assets/utils/statusBadgeUtils.js";

import "../styles/GeneralDashboard.css";
import "../styles/NavigationSidebar.css";

export default function DoctorEnrollmentRequestsPage() {
  const [navIsClosed, setNavIsClosed] = useState(false);
  const [doctor, setDoctor] = useState(null);
  const [requests, setRequests] = useState([]);
  const [loading, setLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState("");
  const [selectedRequest, setSelectedRequest] = useState(null);

  const [showReviewModal, setShowReviewModal] = useState(false);
const [reviewAction, setReviewAction] = useState(null);
const [reviewReason, setReviewReason] = useState("");
const [submittingReview, setSubmittingReview] = useState(false);


   function handleRequestSelect(id){
    if (selectedRequest === id) {
    setSelectedRequest(null);
  } else {
    setSelectedRequest(id);
  }
  }

  function handleNav() {
    setNavIsClosed((prev) => !prev);
  }

  useEffect(() => {
    async function fetchPageData() {
      try {
        setLoading(true);
        setErrorMessage("");

        const [dashboardData, enrollmentRequests] = await Promise.all([
          getCurrentDoctorDashboard(),
          getDoctorEnrollmentRequests(),
        ]);

        setDoctor(dashboardData.doctor);
        setRequests(enrollmentRequests);
      } catch (error) {
        if (error.response) {
          setErrorMessage(error.response.data);
        } else {
          setErrorMessage("Nu s-au putut incarca cererile de inscriere.");
        }
      } finally {
        setLoading(false);
      }
    }

    fetchPageData();
  }, []);

  

  function formatDate(dateValue) {
    if (!dateValue) return "-";
    return new Date(dateValue).toLocaleString("ro-RO");
  }

  async function handleApproveSelectedRequest() {
  if (!selectedRequest) return;

  try {
    await approveEnrollmentRequest(selectedRequest);
    setRequests((prevRequests) =>
      prevRequests.filter((request) => request.id !== selectedRequest)
    );
    setSelectedRequest(null);
  } catch (error) {
    if (error.response) {
      setErrorMessage(error.response.data);
    } else {
      setErrorMessage("Nu s-a putut accepta cererea.");
    }
  }
}

async function handleRejectSelectedRequest() {
  if (!selectedRequest) return;

  try {
    await rejectEnrollmentRequest(selectedRequest);
    setRequests((prevRequests) =>
      prevRequests.filter((request) => request.id !== selectedRequest)
    );
    setSelectedRequest(null);
  } catch (error) {
    if (error.response) {
      setErrorMessage(error.response.data);
    } else {
      setErrorMessage("Nu s-a putut refuza cererea.");
    }
  }
}

function openReviewModal(action) {
  if (!selectedRequest) return;

  setReviewAction(action);
  setReviewReason("");
  setShowReviewModal(true);
}

function closeReviewModal() {
  if (submittingReview) return;

  setShowReviewModal(false);
  setReviewAction(null);
  setReviewReason("");
}

async function handleSubmitReview() {
  if (!selectedRequest || !reviewAction) return;

  try {
    setSubmittingReview(true);
    setErrorMessage("");

    if (reviewAction === "approve") {
      await approveEnrollmentRequest(selectedRequest, reviewReason);
    } else {
      await rejectEnrollmentRequest(selectedRequest, reviewReason);
    }

    setRequests((prevRequests) =>
      prevRequests.filter((request) => request.id !== selectedRequest)
    );

    setSelectedRequest(null);
    closeReviewModal();
  } catch (error) {
    if (error.response) {
      setErrorMessage(error.response.data);
    } else {
      setErrorMessage("Nu s-a putut procesa cererea.");
    }
  } finally {
    setSubmittingReview(false);
  }
}


  return (
    <>
      <Navbar sticky="top" expand="lg" bg="primary" data-bs-theme="dark">
        <Container>
          <Button
            className={`btn btn-primary ${navIsClosed ? "active" : ""}`}
            onClick={handleNav}
          >
            <i className="material-icons">dehaze</i>
          </Button>

          <Navbar.Brand href="/doctor">MedicalApp</Navbar.Brand>

          <Navbar.Toggle />
          <Navbar.Collapse className="justify-content-end">
            <Navbar.Text>
              Signed in as:{" "}
              <a href="#login">
                {doctor?.firstName} {doctor?.lastName}
              </a>
            </Navbar.Text>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <div
        className="d-flex flex-column flex-md-row"
        style={{
          height: "calc(100vh - 56px)",
          minWidth: "100vw",
          overflow: "hidden",
        }}
      >
        <NavigationSidebar navIsClosed={navIsClosed} />

        <div className="main flex-grow-1 hide-scrollbar">
          <div className="report-section hide-scrollbar" style={{ width: "100%" }}>
            <div className="enrollment-card">
              <div className="enrollment-card-header">
                <h2>Cereri de înscriere pacienți</h2>
                <p>
                  Aici puteți vizualiza cererile trimise de pacienți către dumneavoastră.
                </p>
              </div>

              {loading && (
                <div className="text-center py-4">
                  <Spinner animation="border" />
                  <p className="mt-3 mb-0">Se încarcă cererile...</p>
                </div>
              )}

              {!loading && errorMessage && (
                <Alert variant="danger">{errorMessage}</Alert>
              )}

              {!loading && !errorMessage && requests.length === 0 && (
                <Alert variant="info">
                  Nu există cereri de înscriere în așteptare.
                </Alert>
              )}

              {!loading && !errorMessage && requests.length > 0 && (
                <>
                <Table responsive hover>
                  <thead>
                    <tr>
                      
                      <th>Nume</th>
                      
                      <th>Telefon</th>
                      <th>Adresă</th>
                      <th>Status</th>
                      <th>Data trimiterii</th>
                    </tr>
                  </thead>

                  <tbody>
                    {requests.map((request) => (
                      <tr key={request.id}
                      onClick={() => handleRequestSelect(request.id)}
                className={`item ${selectedRequest === request.id ? "selected-row" : ""}`}
                style = {{cursor: "pointer"}}
                      >
                        
                        <td>{request.patientFullName}</td>

<td>{request.patientPhone || "-"}</td>
<td>{request.patientAddress || "-"}</td>

                        <td>
                          <Badge bg={getStatusBadgeVariant(request.status)}>
                            {request.status}
                          </Badge>
                        </td>
                        <td>{formatDate(request.createdAt)}</td>
                      </tr>
                    ))}
                  </tbody>
                </Table>
                <div className="d-flex gap-2 mt-3">
        <div className="d-flex gap-2 mt-3">
  
  <Button
    disabled={!selectedRequest}
    onClick={() => openReviewModal("approve")}
  >
    Acceptă
  </Button>

  <Button
    disabled={!selectedRequest}
    onClick={() => openReviewModal("reject")}
    variant="danger"
  >
    Refuză
  </Button>


</div>

</div>
                </>
              )}
            </div>
          </div>
        </div>
      </div>
      <Modal show={showReviewModal} onHide={closeReviewModal} centered>
  <Modal.Header closeButton>
    <Modal.Title>
      {reviewAction === "approve"
        ? "Acceptă cererea"
        : "Refuză cererea"}
    </Modal.Title>
  </Modal.Header>

  <Modal.Body>
    <Form.Group controlId="reviewReason">
      <Form.Label>Motiv opțional</Form.Label>
      <Form.Control
        as="textarea"
        rows={3}
        value={reviewReason}
        onChange={(event) => setReviewReason(event.target.value)}
        placeholder="Scrieți un motiv (opțional)"
        disabled={submittingReview}
      />
    </Form.Group>
  </Modal.Body>

  <Modal.Footer>
    <Button
      variant="secondary"
      onClick={closeReviewModal}
      disabled={submittingReview}
    >
      Anulează
    </Button>

    <Button
      variant={reviewAction === "approve" ? "primary" : "danger"}
      onClick={handleSubmitReview}
      disabled={submittingReview}
    >
      {submittingReview
        ? "Se trimite..."
        : reviewAction === "approve"
          ? "Acceptă"
          : "Refuză"}
    </Button>
  </Modal.Footer>
</Modal>

    </>
  );
}


