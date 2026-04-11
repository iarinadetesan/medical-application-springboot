import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getCurrentPatientDashboard } from "../services/patientService";


function PatientDashboard() {
  const navigate = useNavigate();

  const [dashboard, setDashboard] = useState(null);
  const [loading, setLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    const fetchDashboard = async () => {
      try {
        const data = await getCurrentPatientDashboard();
        console.log("Patient dashboard:", data);
        setDashboard(data);
      } catch (error) {
        console.error("Eroare la încărcarea dashboard-ului:", error);

        if (error.response) {
          setErrorMessage(error.response.data);
        } else {
          setErrorMessage("Nu s-au putut încărca datele dashboard-ului.");
        }
      } finally {
        setLoading(false);
      }
    };

    fetchDashboard();
  }, []);

  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };

  if (loading) {
    return <div style={styles.page}>Se încarcă dashboard-ul...</div>;
  }

  if (errorMessage) {
    return (
      <div style={styles.page}>
        <h1>Dashboard Pacient</h1>
        <p style={styles.error}>{errorMessage}</p>
        <button onClick={handleLogout} style={styles.logoutButton}>
          Logout
        </button>
      </div>
    );
  }

  const patient = dashboard?.patient;
  const summary = dashboard?.summary;

  return (
    <div style={styles.page}>
      <div style={styles.header}>
        <div>
          <h1 style={styles.title}>Dashboard Pacient</h1>
          <p style={styles.subtitle}>
            Bine ai venit, {patient?.firstName} {patient?.lastName}!
          </p>
        </div>

        <button onClick={handleLogout} style={styles.logoutButton}>
          Logout
        </button>
      </div>

      <div style={styles.topGrid}>
        <div style={styles.card}>
          <h2 style={styles.cardTitle}>Istoric sănătate</h2>
          <p><strong>Consultații:</strong> {summary?.consultationsCount}</p>
          <p><strong>Rețete:</strong> {summary?.prescriptionsCount}</p>
          <p><strong>Trimiteri:</strong> {summary?.referralsCount}</p>
          <p><strong>Total documente:</strong> {summary?.totalDocumentsCount}</p>
        </div>

        <div style={styles.card}>
          <h2 style={styles.cardTitle}>Medic de familie</h2>
          <p>
            <strong>Medic:</strong>{" "}
            {patient?.familyDoctorName ? patient.familyDoctorName : "Neasignat"}
          </p>
          <p><strong>Email:</strong> {patient?.email}</p>
          <p><strong>Telefon:</strong> {patient?.phone}</p>
        </div>
      </div>

      <div style={styles.section}>
        <h2 style={styles.sectionTitle}>Consultații recente</h2>
        {dashboard?.recentConsultations?.length > 0 ? (
          <table style={styles.table}>
            <thead>
              <tr>
                <th>ID</th>
                <th>Data</th>
                <th>Doctor</th>
                <th>Scop / Detalii</th>
              </tr>
            </thead>
            <tbody>
              {dashboard.recentConsultations.map((consultation) => (
                <tr key={consultation.id}>
                  <td>{consultation.id}</td>
                  <td>{consultation.consultationDate || "-"}</td>
                  <td>
                    {consultation.doctorName}
                  </td>
                  <td>
                    {
                      consultation.diagnosis ||
                      "-"}
                  </td>
                  <td>
                    {consultation.notes}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <p>Nu există consultații recente.</p>
        )}
      </div>

      <div style={styles.sectionGrid}>
        <div style={styles.card}>
          <h2 style={styles.cardTitle}>Rețete medicale</h2>
          {dashboard?.prescriptions?.length > 0 ? (
            <ul style={styles.list}>
              {dashboard.prescriptions.map((prescription) => (
                <li key={prescription.id} style={styles.listItem}>
                  
                  {prescription.status || "Fără status"} —{" "}
                  {prescription.issueDate || prescription.date || "-"}
                  {prescription.medicationName}
                  {prescription.dosage}
                  {prescription.instructions}
                  {prescription.issueDate}
                  {prescription.expiryDate}
                  {prescription.doctorName}
                </li>
              ))}
            </ul>
          ) : (
            <p>Nu există rețete.</p>
          )}
        </div>

        <div style={styles.card}>
          <h2 style={styles.cardTitle}>Trimiteri</h2>
          {dashboard?.referrals?.length > 0 ? (
            <ul style={styles.list}>
              {dashboard.referrals.map((referral) => (
                <li key={referral.id} style={styles.listItem}>
                  <strong>#{referral.id}</strong> —{" "}
                  {referral.specialization || "Fără specializare"} —{" "}
                  {referral.status || "Fără status"}
                  {referral.reason}
                  {referral.referringDoctorId}
                </li>
              ))}
            </ul>
          ) : (
            <p>Nu există trimiteri.</p>
          )}
        </div>
      </div>
    </div>
  );
}

const styles = {
  page: {
    minHeight: "100vh",
    backgroundColor: "#f5f7fb",
    padding: "30px",
    fontFamily: "Arial, sans-serif",
  },
  header: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    marginBottom: "30px",
  },
  title: {
    margin: 0,
    fontSize: "32px",
  },
  subtitle: {
    marginTop: "8px",
    color: "#555",
  },
  topGrid: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fit, minmax(320px, 1fr))",
    gap: "20px",
    marginBottom: "25px",
  },
  sectionGrid: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fit, minmax(320px, 1fr))",
    gap: "20px",
    marginTop: "20px",
  },
  section: {
    backgroundColor: "white",
    padding: "20px",
    borderRadius: "16px",
    boxShadow: "0 2px 10px rgba(0,0,0,0.08)",
    marginBottom: "20px",
  },
  sectionTitle: {
    marginTop: 0,
    marginBottom: "15px",
  },
  card: {
    backgroundColor: "white",
    padding: "20px",
    borderRadius: "16px",
    boxShadow: "0 2px 10px rgba(0,0,0,0.08)",
  },
  cardTitle: {
    marginTop: 0,
    marginBottom: "15px",
    fontSize: "20px",
  },
  table: {
    width: "100%",
    borderCollapse: "collapse",
  },
  list: {
    paddingLeft: "18px",
    margin: 0,
  },
  listItem: {
    marginBottom: "10px",
  },
  logoutButton: {
    padding: "10px 18px",
    border: "none",
    borderRadius: "10px",
    cursor: "pointer",
    backgroundColor: "#d9534f",
    color: "white",
    fontWeight: "bold",
  },
  error: {
    color: "red",
    marginBottom: "20px",
  },
};

export default PatientDashboard;