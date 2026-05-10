import api from "../api/api";

import { getToken } from "./authService";


const API_URL = "http://localhost:8080/api/enrollments";

export const createEnrollmentRequest = async ({ doctorId, message }) => {
  const response = await api.post("/enrollments", { doctorId, message });
  return response.data;
};

export const getMyEnrollmentRequests = async () => {
  const response = await api.get("/enrollments/my");
  return response.data;
};

export const getDoctorEnrollmentRequests = async () => {
  const response = await api.get("/enrollments/pending");
  return response.data;
}

export const approveEnrollmentRequest = async (id, reason = null) => {
  const response = await api.post(`/enrollments/${id}/approve`, {
    reason,
  });

  return response.data;
};

export const rejectEnrollmentRequest = async (id, reason = null) => {
  const response = await api.post(`/enrollments/${id}/reject`, {
    reason,
  });

  return response.data;
};

