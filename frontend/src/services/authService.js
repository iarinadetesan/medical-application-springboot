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

