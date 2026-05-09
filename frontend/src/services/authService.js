import api from "../api/api";

export const loginUser = async (email, password) => {
  const response = await api.post("/auth/login", {
    email,
    password,
  });

  return response.data;
};

export function logout() {
  localStorage.removeItem("token");
  localStorage.removeItem("role");
  localStorage.removeItem("userId");
  localStorage.removeItem("email");
  localStorage.removeItem("username");
}


export function getToken() {
  return localStorage.getItem("token");
}

export function getRole() {
  return localStorage.getItem("role");
}

export function isLoggedIn() {
  return !!localStorage.getItem("token");
}

export function getUserId() {
  return localStorage.getItem("userId");
}

const API_BASE_URL = "http://localhost:8080/api";

export async function registerPatient(registerData) {
  const response = await fetch(`${API_BASE_URL}/patient-register`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(registerData),
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || "Eroare la înregistrarea pacientului.");
  }

  return await response.json();
}

export function saveAuthData(loginResponse) {
  localStorage.setItem("token", loginResponse.token);
  localStorage.setItem("username", loginResponse.username);
  localStorage.setItem("email", loginResponse.email);
  localStorage.setItem("role", loginResponse.role);
  localStorage.setItem("userId", loginResponse.userId);
  localStorage.setItem("profileId", loginResponse.profileId);
}