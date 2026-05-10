
import { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";

import { createReferral } from "../../services/referralService";
import { getUserId } from "../../services/authService";

export default function ReferralForm({ patients, consultationId , refreshDashboard}) {
  const [formData, setFormData] = useState({
    
    specialization: "",
    reason: "",
    issueDate: "",
    expiryDate: "",
    status: "PENDING",
  });

  const [loading, setLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  function handleChange(event) {
    const { name, value } = event.target;

    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  }

  async function handleSubmit(event) {
    event.preventDefault();

    setLoading(true);
    setSuccessMessage("");
    setErrorMessage("");

    const referralData = {
  patientId: Number(formData.patientId),
  referringDoctorId: Number(getProfileId()),
  specialization: formData.specialization,
  reason: formData.reason,
  issueDate: formData.issueDate,
  expiryDate: formData.expiryDate,
  status: formData.status,
};

    try {
      await createReferral(referralData);
      if (refreshDashboard) {
    await refreshDashboard();
  }
      setSuccessMessage("Trimiterea a fost creată cu succes.");

      setFormData({
        
        patientId: "",
        specialization: "",
        reason: "",
        issueDate: "",
        expiryDate: "",
        status: "PENDING",
      });
    } catch (error) {
      setErrorMessage("Nu s-a putut crea rețeta.");
      console.error(error);
    } finally {
      setLoading(false);
    }
  }

  return (
    <Form onSubmit={handleSubmit}>
      {successMessage && (
        <Alert variant="success">{successMessage}</Alert>
      )}

      {errorMessage && (
        <Alert variant="danger">{errorMessage}</Alert>
      )}

      <Form.Group className="mb-3">
  <Form.Label>Pacient</Form.Label>

  <Form.Select
    name="patientId"
    value={formData.patientId}
    onChange={handleChange}
    required
  >
    <option value="">Selectează pacientul</option>

    {patients.map((patient) => (
      <option key={patient.id} value={patient.id}>
        {patient.lastName} {patient.firstName}
      </option>
    ))}
  </Form.Select>
</Form.Group>
      

      <Form.Group className="mb-3">
        <Form.Label>Specializare</Form.Label>
        <Form.Control
          type="text"
          name="specialization"
          value={formData.specialization}
          onChange={handleChange}
          placeholder="Ex: Cardiologie"
          required
        />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Motiv</Form.Label>
        <Form.Control
          as="textarea"
          rows={3}
          name="reason"
          value={formData.reason}
          onChange={handleChange}
          placeholder="Ex: Suspect Insuficiență Cardiacă"
        />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Data emiterii</Form.Label>
        <Form.Control
          type="date"
          name="issueDate"
          value={formData.issueDate}
          onChange={handleChange}
          required
        />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Data expirării</Form.Label>
        <Form.Control
          type="date"
          name="expiryDate"
          value={formData.expiryDate}
          onChange={handleChange}
          required
        />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Status</Form.Label>
        <Form.Select
          name="status"
          value={formData.status}
          onChange={handleChange}
        >
          <option value="PENDING">Activă/In asteptare</option>
          <option value="EXPIRED">Expirată</option>
          <option value="COMPLETED">Utilizată</option>
        </Form.Select>
      </Form.Group>

      <Button type="submit" disabled={loading}>
        {loading ? "Se salvează..." : "Creează trimitere"}
      </Button>
    </Form>
  );
}