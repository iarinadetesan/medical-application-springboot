
import { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";

import { createPrescription } from "../../services/prescriptionService";
import { getUserId } from "../../services/authService";

export default function PrescriptionForm({ patients, consultationId , refreshDashboard}) {
  const [formData, setFormData] = useState({
    medicationName: "",
    dosage: "",
    instructions: "",
    issueDate: "",
    expiryDate: "",
    status: "ACTIVE",
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

    const prescriptionData = {
  patientId: Number(formData.patientId),
  doctorId: Number(getUserId()),
  consultationId: consultationId || null,
  medicationName: formData.medicationName,
  dosage: formData.dosage,
  instructions: formData.instructions,
  issueDate: formData.issueDate,
  expiryDate: formData.expiryDate,
  status: formData.status,
};

    try {
      await createPrescription(prescriptionData);


if (refreshDashboard) {
  await refreshDashboard();
}
      setSuccessMessage("Rețeta a fost creată cu succes.");

      setFormData({
        patientId: "",
        medicationName: "",
        dosage: "",
        instructions: "",
        issueDate: "",
        expiryDate: "",
        status: "ACTIVE",
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
        <Form.Label>Medicament</Form.Label>
        <Form.Control
          type="text"
          name="medicationName"
          value={formData.medicationName}
          onChange={handleChange}
          placeholder="Ex: Paracetamol"
          required
        />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Dozaj</Form.Label>
        <Form.Control
          type="text"
          name="dosage"
          value={formData.dosage}
          onChange={handleChange}
          placeholder="Ex: 500mg, de 2 ori pe zi"
          required
        />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Instrucțiuni</Form.Label>
        <Form.Control
          as="textarea"
          rows={3}
          name="instructions"
          value={formData.instructions}
          onChange={handleChange}
          placeholder="Ex: după masă, timp de 5 zile"
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
          <option value="ACTIVE">Activa</option>
          <option value="EXPIRED">Expirată</option>
          <option value="DISPENSED">Eliberată</option>
        </Form.Select>
      </Form.Group>

      <Button type="submit" disabled={loading}>
        {loading ? "Se salvează..." : "Creează rețetă"}
      </Button>
    </Form>
  );
}