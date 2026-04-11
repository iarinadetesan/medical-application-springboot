import api from "../api/api";

export const getCurrentPatientDashboard = async () => {
  const response = await api.get("/patients/me/dashboard");
  return response.data;
};