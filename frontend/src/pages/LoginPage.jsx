import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../services/authService";
import { Link } from "react-router-dom";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");

    if (token && role) {
      if (role === "DOCTOR") {
        navigate("/doctor");
      } else if (role === "PATIENT") {
        navigate("/patient");
      } else if (role === "ADMIN") {
        navigate("/admin");
      }
    }
  }, [navigate]);

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const data = await loginUser(email, password);

      localStorage.setItem("token", data.token);
      localStorage.setItem("username", data.username);
      localStorage.setItem("email", data.email);
      localStorage.setItem("role", data.role);
      localStorage.setItem("userId", data.userId);
      localStorage.setItem("profileId", data.profileId);

      if (data.role === "DOCTOR") {
        navigate("/doctor");
      } else if (data.role === "PATIENT") {
        navigate("/patient");
      } else if (data.role === "ADMIN") {
        navigate("/admin");
      } else {
        setMessage("Rol necunoscut.");
      }
    } catch (error) {
      if (error.response) {
        setMessage(error.response.data);
      } else {
        setMessage("Nu se poate conecta la backend.");
      }
    }
  };

  return (
    <>
    <div style={{ padding: "20px" }}>
      <h2>Login</h2>

      <form onSubmit={handleLogin}>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <br /><br />

        <input
          type="password"
          placeholder="Parola"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <br /><br />

        <button type="submit">Login</button>
      </form>

      <p>{message}</p>
    </div>
    <div className="text-center mt-3">
  Nu ai cont? <Link to="/register/patient">Creează cont de pacient</Link>
</div>
    </>
  );
}

export default LoginPage;