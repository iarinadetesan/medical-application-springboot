import api from "../api/api";

export const createEnrollmentRequest = async ({ doctorId, message }) => {
  const response = await api.post("/enrollments", { doctorId, message });
  return response.data;
};

export const getMyEnrollmentRequests = async () => {
  const response = await api.get("/enrollments/my");
  return response.data;
};
