import api from "../api/api";

export const getCurrentDoctorDashboard = async () => {
  const response = await api.get("/doctors/me/dashboard");
  return response.data;
};