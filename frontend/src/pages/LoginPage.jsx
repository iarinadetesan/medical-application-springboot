import { useCallback, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

import Container from "react-bootstrap/Container";
import Card from "react-bootstrap/Card";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";

import { loginUser, saveAuthData } from "../services/authService";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const navigateByRole = useCallback((role) => {
    if (role === "DOCTOR") {
      navigate("/doctor");
    } else if (role === "PATIENT") {
      navigate("/patient");
    } else if (role === "ADMIN") {
      navigate("/admin");
    } else {
      setMessage("Rol necunoscut.");
    }
  }, [navigate]);

  useEffect(() => {
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");

    if (token && role) {
      navigateByRole(role);
    }
  }, [navigateByRole]);

  const handleLogin = async (event) => {
    event.preventDefault();
    setMessage("");
    setLoading(true);

    try {
      const data = await loginUser(email, password);

      saveAuthData(data);
      navigateByRole(data.role);
    } catch (error) {
      if (error.response) {
        setMessage(error.response.data);
      } else {
        setMessage("Nu se poate conecta la sistem.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container
      className="d-flex justify-content-center align-items-center py-4"
      style={{ minHeight: "100vh" }}
    >
      <Card style={{ maxWidth: "460px", width: "100%" }} className="shadow-sm">
        <Card.Body>
          <Card.Title className="text-center mb-2">Autentificare</Card.Title>
          <Card.Text className="text-center text-muted mb-4">
            Intră in contul tău MedicalApp.
          </Card.Text>

          {message && <Alert variant="danger">{message}</Alert>}

          <Form onSubmit={handleLogin}>
            <Form.Group className="mb-3" controlId="loginEmail">
              <Form.Label>Email</Form.Label>
              <Form.Control
                type="email"
                placeholder="nume@email.com"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
                required
              />
            </Form.Group>

            <Form.Group className="mb-4" controlId="loginPassword">
              <Form.Label>Parola</Form.Label>
              <Form.Control
                type="password"
                placeholder="Introduceți parola"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
                required
              />
            </Form.Group>

            <Button type="submit" className="w-100" disabled={loading}>
              {loading ? "Se autentifică..." : "Autentificare"}
            </Button>
          </Form>

          <hr />

          <div className="text-center">
            <div className="text-muted mb-2">Nu ai cont?</div>
            <div className="d-flex gap-2 justify-content-center flex-wrap">
              <Link to="/register/patient">Cont pacient</Link>
              <span className="text-muted">|</span>
              <Link to="/register/doctor">Cerere cont medic</Link>
            </div>
          </div>
        </Card.Body>
      </Card>
    </Container>
  );
}

export default LoginPage;
