import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";
import Badge from "react-bootstrap/Badge";
import Spinner from "react-bootstrap/Spinner";
import Table from "react-bootstrap/Table";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import ButtonGroup from "react-bootstrap/ButtonGroup";

import {
  getAllDoctorRegistrationRequests,
  approveDoctorRegistrationRequest,
  rejectDoctorRegistrationRequest,
} from "../services/doctorRegistrationAdminService";

import { logout } from "../services/authService";
import { getStatusBadgeVariant } from "../assets/utils/statusBadgeUtils";

export default function AdminDashboard() {
  const navigate = useNavigate();

  const [requests, setRequests] = useState([]);
  const [statusFilter, setStatusFilter] = useState("ALL");
  const [selectedRequestId, setSelectedRequestId] = useState(null);

  const [loading, setLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState("");

  const [showReviewModal, setShowReviewModal] = useState(false);
  const [reviewAction, setReviewAction] = useState(null);
  const [reviewReason, setReviewReason] = useState("");
  const [submittingReview, setSubmittingReview] = useState(false);

  async function fetchRequests() {
    try {
      setLoading(true);
      setErrorMessage("");

      const data = await getAllDoctorRegistrationRequests();

      data.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

      setRequests(data);
    } catch (error) {
      if (error.response) {
        setErrorMessage(error.response.data);
      } else {
        setErrorMessage("Nu s-au putut încărca cererile medicilor.");
      }
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    fetchRequests();
  }, []);

  function handleLogout() {
    logout();
    navigate("/");
  }

  function handleRequestSelect(id) {
    setSelectedRequestId((currentId) => (currentId === id ? null : id));
  }

  function openReviewModal(action) {
    if (!selectedRequestId) return;

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
    if (!selectedRequestId || !reviewAction) return;

    try {
      setSubmittingReview(true);
      setErrorMessage("");

      if (reviewAction === "approve") {
        await approveDoctorRegistrationRequest(selectedRequestId, reviewReason);
      } else {
        await rejectDoctorRegistrationRequest(selectedRequestId, reviewReason);
      }

      setSelectedRequestId(null);
      closeReviewModal();
      await fetchRequests();
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

  function formatDate(dateValue) {
    if (!dateValue) return "-";
    return new Date(dateValue).toLocaleString("ro-RO");
  }

  const filteredRequests =
    statusFilter === "ALL"
      ? requests
      : requests.filter((request) => request.status === statusFilter);

  const selectedRequest = requests.find(
    (request) => request.id === selectedRequestId
  );

  const selectedRequestIsPending = selectedRequest?.status === "PENDING";

  return (
    <>
      <Navbar sticky="top" expand="lg" bg="primary" data-bs-theme="dark">
        <Container>
          <Navbar.Brand href="/admin">MedicalApp Admin</Navbar.Brand>

          <Navbar.Toggle />
          <Navbar.Collapse className="justify-content-end">
            <Navbar.Text className="me-3">Signed in as: Admin</Navbar.Text>
            <Button variant="outline-light" size="sm" onClick={handleLogout}>
              Sign out
            </Button>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <Container className="py-4">
        <div className="bg-white rounded shadow-sm p-4">
          <div className="d-flex justify-content-between align-items-start gap-3 flex-wrap mb-4">
            <div>
              <h2 className="mb-1">Cereri de înscriere medici</h2>
              <p className="text-muted mb-0">
                Aici se pot vizualiza și gestiona cererile de creare cont medic.
              </p>
            </div>

            <ButtonGroup>
              <Button
                variant={statusFilter === "ALL" ? "primary" : "outline-primary"}
                onClick={() => setStatusFilter("ALL")}
              >
                Toate
              </Button>
              <Button
                variant={
                  statusFilter === "PENDING" ? "primary" : "outline-primary"
                }
                onClick={() => setStatusFilter("PENDING")}
              >
                Pending
              </Button>
              <Button
                variant={
                  statusFilter === "APPROVED" ? "primary" : "outline-primary"
                }
                onClick={() => setStatusFilter("APPROVED")}
              >
                Approved
              </Button>
              <Button
                variant={
                  statusFilter === "REJECTED" ? "primary" : "outline-primary"
                }
                onClick={() => setStatusFilter("REJECTED")}
              >
                Rejected
              </Button>
            </ButtonGroup>
          </div>

          {loading && (
            <div className="text-center py-4">
              <Spinner animation="border" />
              <p className="mt-3 mb-0">Se incarca cererile...</p>
            </div>
          )}

          {!loading && errorMessage && (
            <Alert variant="danger">{errorMessage}</Alert>
          )}

          {!loading && !errorMessage && filteredRequests.length === 0 && (
            <Alert variant="info">Nu există cereri pentru filtrul ales.</Alert>
          )}

          {!loading && !errorMessage && filteredRequests.length > 0 && (
            <>
              <Table responsive hover>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Medic</th>
                    <th>Email</th>
                    <th>Username</th>
                    <th>Licenta</th>
                    <th>Telefon</th>
                    <th>Status</th>
                    <th>Data trimiterii</th>
                  </tr>
                </thead>

                <tbody>
                  {filteredRequests.map((request) => (
                    <tr
                      key={request.id}
                      onClick={() => handleRequestSelect(request.id)}
                      className={
                        selectedRequestId === request.id ? "table-primary" : ""
                      }
                      style={{ cursor: "pointer" }}
                    >
                      <td>{request.id}</td>
                      <td>
                        {request.firstName} {request.lastName}
                      </td>
                      <td>{request.email}</td>
                      <td>{request.username}</td>
                      <td>{request.licenseNumber}</td>
                      <td>{request.phone || "-"}</td>
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
                <Button
                  disabled={!selectedRequestId || !selectedRequestIsPending}
                  onClick={() => openReviewModal("approve")}
                >
                  Aproba
                </Button>

                <Button
                  disabled={!selectedRequestId || !selectedRequestIsPending}
                  onClick={() => openReviewModal("reject")}
                  variant="danger"
                >
                  Respinge
                </Button>
              </div>

              {selectedRequestId && !selectedRequestIsPending && (
                <Alert variant="secondary" className="mt-3 mb-0">
                  Doar cererile PENDING pot fi aprobate sau respinse.
                </Alert>
              )}
            </>
          )}
        </div>
      </Container>

      <Modal show={showReviewModal} onHide={closeReviewModal} centered>
        <Modal.Header closeButton>
          <Modal.Title>
            {reviewAction === "approve" ? "Aproba cererea" : "Respinge cererea"}
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
              placeholder="Scrieți un motiv, dacă este cazul..."
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
                ? "Aprobă"
                : "Respinge"}
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}
