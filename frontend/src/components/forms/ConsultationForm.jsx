
import { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";

import { createConsultation } from "../../services/consultationService";
import { getUserId } from "../../services/authService";

export default function ConsultationForm({ patients, consultationId, refreshDashboard }) {
  const [formData, setFormData] = useState({
    
    diagnosis: "",
    notes: "",
    consultationDate: "",
    
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

    const consultationData = {
  patientId: Number(formData.patientId),
  doctorId: Number(getUserId()),
  diagnosis: formData.diagnosis,
  notes: formData.notes,
  consultationDate: formData.consultationDate,
  
};

    try {
      await createConsultation(consultationData);
      if (refreshDashboard) {
    await refreshDashboard();
  }

      setSuccessMessage("Consultația a fost salvată cu succes.");

      setFormData({
        
        patientId: "",
        diagnosis: "",
        notes: "",
        consultationDate: "",
        
      });
    } catch (error) {
      setErrorMessage("Nu s-a putut salva consultația.");
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
        <Form.Label>Diagnostic</Form.Label>
        <Form.Control
          type="text"
          name="diagnosis"
          value={formData.diagnosis}
          onChange={handleChange}
          placeholder="Ex: Gripă virală"
          required
        />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Descriere simptome</Form.Label>
        <Form.Control
          as="textarea"
          rows={3}
          name="notes"
          value={formData.notes}
          onChange={handleChange}
          placeholder="Ex: Durere cap, febră, stare de oboseală"
        />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Data consultației</Form.Label>
        <Form.Control
          type="date"
          name="consultationDate"
          value={formData.consultationDate}
          onChange={handleChange}
          required
        />
      </Form.Group>

      



      <Button type="submit" disabled={loading}>
        {loading ? "Se salvează..." : "Creează consultație"}
      </Button>
    </Form>
  );
}