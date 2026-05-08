
import { getToken } from "./authService";

const API_URL = "http://localhost:8080/api/consultations";

export async function createConsultation(consultationData) {
  const token = getToken();

  const response = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(consultationData),
  });

  if (!response.ok) {
    throw new Error("Eroare la crearea consultatiei");
  }

  return await response.json();
}