import api from "../api/api";

export const getDoctorRegistrationRequestsByStatus = async (status) => {
  const response = await api.get(`/doctor-requests?status=${status}`);
  return response.data;
};

export const getAllDoctorRegistrationRequests = async () => {
  const [pending, approved, rejected] = await Promise.all([
    getDoctorRegistrationRequestsByStatus("PENDING"),
    getDoctorRegistrationRequestsByStatus("APPROVED"),
    getDoctorRegistrationRequestsByStatus("REJECTED"),
  ]);

  return [...pending, ...approved, ...rejected];
};

export const approveDoctorRegistrationRequest = async (id, reason = null) => {
  const response = await api.post(`/doctor-requests/${id}/approve`, {
    reason,
  });

  return response.data;
};

export const rejectDoctorRegistrationRequest = async (id, reason = null) => {
  const response = await api.post(`/doctor-requests/${id}/reject`, {
    reason,
  });

  return response.data;
};
