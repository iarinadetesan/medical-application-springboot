import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import DoctorDashboard from "./pages/DoctorDashboard";
import PatientDashboard from "./pages/PatientDashboard";
import AdminDashboard from "./pages/AdminDashboard";
import ProtectedRoute from "./components/ProtectedRoute";
import DashboardLayout from "./layouts/DashboardLayout";
import GeneralDashboard from "./layouts/GeneralDashboard";
import RegisterPatientPage from "./pages/RegisterPatientPage";
import RegisterDoctorPage from "./pages/RegisterDoctorPage";
import DoctorRegistrationStatusPage from "./pages/DoctorRegistrationStatusPage";
import DoctorEnrollmentRequestsPage from "./pages/DoctorEnrollmentRequestsPage";


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/test" element={<GeneralDashboard />} />
        <Route path="/layout" element={<DashboardLayout />} />
        <Route path="/register/patient" element={<RegisterPatientPage />} />
        <Route path="/register/doctor" element = {<RegisterDoctorPage />}/>
        <Route
  path="/doctor-registration-status/:id"
  element={<DoctorRegistrationStatusPage />}
/>

        <Route
          path="/doctor"
          element={
            <ProtectedRoute allowedRole="DOCTOR">
              <DoctorDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/patient"
          element={
            <ProtectedRoute allowedRole="PATIENT">
              <PatientDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin"
          element={
            <ProtectedRoute allowedRole="ADMIN">
              <AdminDashboard />
            </ProtectedRoute>
          }
        
        />
        <Route
  path="/doctor/enrollment-requests"
  element={
    <ProtectedRoute allowedRole="DOCTOR">
      <DoctorEnrollmentRequestsPage />
    </ProtectedRoute>
  }
/>

      </Routes>
    </BrowserRouter>
    
  );
}

export default App;