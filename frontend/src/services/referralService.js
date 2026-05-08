
import { getToken } from "./authService";

const API_URL = "http://localhost:8080/api/referrals";

export async function createReferral(referralData) {
  const token = getToken();

  const response = await fetch(API_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(referralData),
  });

  if (!response.ok) {
    throw new Error("Eroare la crearea trimiterii medicale");
  }

  return await response.json();
}