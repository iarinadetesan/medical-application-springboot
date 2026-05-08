
import { getToken } from "./authService";

const API_URL = "http://localhost:8080/api/prescriptions";

export async function createPrescription(prescriptionData) {
  const token = getToken();

  const response = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(prescriptionData),
  });

  if (!response.ok) {
    throw new Error("Eroare la crearea rețetei");
  }

  return await response.json();
}