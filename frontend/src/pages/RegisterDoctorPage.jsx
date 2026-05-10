import { useState } from "react";
import { Link } from "react-router-dom";

import Container from "react-bootstrap/Container";
import Card from "react-bootstrap/Card";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

import { registerDoctor } from "../services/authService";

export default function RegisterDoctorPage() {
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
    firstName: "",
    lastName: "",
    licenseNumber: "",
    phone: "",
    cabinetAddress: "",
  });

  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  function handleChange(event) {
    const { name, value } = event.target;

    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  }

  async function handleSubmit(event) {
    event.preventDefault();

    setErrorMessage("");
    setSuccessMessage("");

    if (formData.password !== formData.confirmPassword) {
      setErrorMessage("Parolele nu coincid.");
      return;
    }

    const registerData = {
      username: formData.username,
      email: formData.email,
      password: formData.password,
      firstName: formData.firstName,
      lastName: formData.lastName,
      licenseNumber: formData.licenseNumber,
      phone: formData.phone,
      cabinetAddress: formData.cabinetAddress,
    };

    try {
      setLoading(true);

      await registerDoctor(registerData);

      setSuccessMessage(
        "Cererea dumneavoastră de înregistrare a fost trimisă. Veți putea folosi contul după aprobarea administratorului. Ați primit un email de confirmare."
      );

      setFormData({
        username: "",
        email: "",
        password: "",
        confirmPassword: "",
        firstName: "",
        lastName: "",
        licenseNumber: "",
        phone: "",
        cabinetAddress: "",
      });
    } catch (error) {
      setErrorMessage(error.message || "Nu s-a putut trimite cererea.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <Container
      className="d-flex justify-content-center align-items-center py-4"
      style={{ minHeight: "100vh" }}
    >
      <Card style={{ maxWidth: "700px", width: "100%" }} className="shadow-sm">
        <Card.Body>
          <Card.Title className="text-center mb-4">
            Înregistrare medic
          </Card.Title>

          {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
          {successMessage && <Alert variant="success">{successMessage}</Alert>}

          <Form onSubmit={handleSubmit}>
            <Row>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Nume utilizator</Form.Label>
                  <Form.Control
                    type="text"
                    name="username"
                    value={formData.username}
                    onChange={handleChange}
                    placeholder="Ex: dr.popescu"
                    required
                  />
                </Form.Group>
              </Col>

              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    placeholder="Ex: medic@email.com"
                    required
                  />
                </Form.Group>
              </Col>
            </Row>

            <Row>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Parola</Form.Label>
                  <Form.Control
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    placeholder="Introdu parola"
                    required
                  />
                </Form.Group>
              </Col>

              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Confirmați parola</Form.Label>
                  <Form.Control
                    type="password"
                    name="confirmPassword"
                    value={formData.confirmPassword}
                    onChange={handleChange}
                    placeholder="Reintrodu parola"
                    required
                  />
                </Form.Group>
              </Col>
            </Row>

            <hr />

            <Row>
              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Nume</Form.Label>
                  <Form.Control
                    type="text"
                    name="lastName"
                    value={formData.lastName}
                    onChange={handleChange}
                    placeholder="Ex: Popescu"
                    required
                  />
                </Form.Group>
              </Col>

              <Col md={6}>
                <Form.Group className="mb-3">
                  <Form.Label>Prenume</Form.Label>
                  <Form.Control
                    type="text"
                    name="firstName"
                    value={formData.firstName}
                    onChange={handleChange}
                    placeholder="Ex: Andrei"
                    required
                  />
                </Form.Group>
              </Col>
            </Row>

            <Form.Group className="mb-3">
              <Form.Label>Numar licență / parafă</Form.Label>
              <Form.Control
                type="text"
                name="licenseNumber"
                value={formData.licenseNumber}
                onChange={handleChange}
                placeholder="Ex: MED123456"
                required
              />
            </Form.Group>

            <Form.Group className="mb-3">
              <Form.Label>Telefon</Form.Label>
              <Form.Control
                type="text"
                name="phone"
                value={formData.phone}
                onChange={handleChange}
                placeholder="Ex: 0712345678"
              />
            </Form.Group>

            <Form.Group className="mb-4">
              <Form.Label>Adresa cabinetului</Form.Label>
              <Form.Control
                as="textarea"
                rows={2}
                name="cabinetAddress"
                value={formData.cabinetAddress}
                onChange={handleChange}
                placeholder="Adresa completa a cabinetului"
              />
            </Form.Group>

            <Button type="submit" className="w-100" disabled={loading}>
              {loading ? "Se trimite cererea..." : "Trimite cererea"}
            </Button>
          </Form>

          <div className="text-center mt-3">
            Aveți deja cont? <Link to="/">Autentificare</Link>
          </div>
        </Card.Body>
      </Card>
    </Container>
  );
}
