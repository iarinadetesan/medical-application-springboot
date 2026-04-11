import { Link } from "react-router-dom";

const MySidebar = () => {
  return (
    <div style={styles.sidebar}>
      <h2 style={styles.logo}>My App</h2>

      <nav style={styles.nav}>
        <Link to="/patient" style={styles.link}>Dashboard</Link>
        <Link to="/patient/prescriptions" style={styles.link}>Rețete</Link>
        <Link to="/patient/referrals" style={styles.link}>Trimiteri</Link>
        <Link to="/patient/messages" style={styles.link}>Mesaje</Link>
        <Link to="/patient/documents" style={styles.link}>Documente</Link>
      </nav>
    </div>
  );
};

const styles = {
  sidebar: {
    width: "270px",
    minHeight: "100vh",
    backgroundColor: "#1e293b",
    color: "white",
    left: 0,
    padding: "24px 16px",
  },
  logo: {
    marginBottom: "30px",
    fontSize: "24px",
  },
  nav: {
    display: "flex",
    flexDirection: "column",
    gap: "14px",
  },
  link: {
    color: "white",
    textDecoration: "none",
    padding: "10px 12px",
    borderRadius: "8px",
    backgroundColor: "#334155",
  },
};

export default MySidebar;