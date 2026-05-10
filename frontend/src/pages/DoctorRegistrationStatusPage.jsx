import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

import Container from "react-bootstrap/Container";
import Card from "react-bootstrap/Card";
import Alert from "react-bootstrap/Alert";
import Spinner from "react-bootstrap/Spinner";
import Badge from "react-bootstrap/Badge";

import { getStatusBadgeVariant } from "../assets/utils/StatusBadgeUtils";

import { getMyRegistrationRequest } from "../services/authService";

export default function DoctorRegistrationStatusPage() {
  const { id } = useParams();

  const [status, setStatus] = useState("");
  const [loading, setLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    async function fetchStatus() {
      try {
        setLoading(true);
        setErrorMessage("");

        const response = await getMyRegistrationRequest({ id });
        setStatus(response);
      } catch (error) {
        setErrorMessage(
          error.response?.data ||
            error.message ||
            "Nu s-a putut incarca statusul cererii."
        );
      } finally {
        setLoading(false);
      }
    }

    fetchStatus();
  }, [id]);

  function getVariant(statusValue) {
    if (statusValue === "APPROVED") return "success";
    if (statusValue === "REJECTED") return "danger";
    return "warning";
  }

  function getLabel(statusValue) {
    if (statusValue === "APPROVED") return "Aprobată";
    if (statusValue === "REJECTED") return "Respinsă";
    if (statusValue === "PENDING") return "În așteptare";
    return statusValue;
  }

  return (
    <Container
      className="d-flex justify-content-center align-items-center py-4"
      style={{ minHeight: "100vh" }}
    >
      <Card style={{ maxWidth: "520px", width: "100%" }} className="shadow-sm">
        <Card.Body className="text-center">
          <Card.Title className="mb-4">Status cerere medic</Card.Title>

          {loading && (
            <div>
              <Spinner animation="border" role="status" />
              <p className="mt-3 mb-0">Se incarca statusul...</p>
            </div>
          )}

          {!loading && errorMessage && (
            <Alert variant="danger">{errorMessage}</Alert>
          )}

          {!loading && !errorMessage && status && (
            <>
              <p className="mb-2">Statusul cererii tale este:</p>

              <Badge bg={getStatusBadgeVariant(status)} style={{ fontSize: "1rem" }}>
                {getLabel(status)}
              </Badge>
            </>
          )}

          <div className="mt-4">
            <Link to="/">Inapoi la autentificare</Link>
          </div>
        </Card.Body>
      </Card>
    </Container>
  );
}
