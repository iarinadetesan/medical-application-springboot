import MySidebar from "../components/Sidebar";

const DashboardLayout = ({ children }) => {
  return (
    <div style={styles.layout}>
      <MySidebar />

      <div style={styles.content}>
        {children}
      </div>
    </div>
  );
};

const styles = {
  layout: {
    display: "flex",
    minHeight: "100vh",
    backgroundColor: "#f5f7fb",
    margin: 0,
    padding: 0
  },
  content: {
    flex: 1,
    padding: "30px",
  },
};

export default DashboardLayout;