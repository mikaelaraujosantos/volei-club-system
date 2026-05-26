import { Navigate } from "react-router-dom";

export default function ProtectedRoute({
  children
}) {

  const token =
    localStorage.getItem("token");

  const role =
    localStorage.getItem("role");

  const perfilCompleto =
    localStorage.getItem(
      "perfilCompleto"
    );

  // =========================
  // SEM LOGIN
  // =========================

  if (!token) {

    return <Navigate to="/" />;

  }

  // =========================
  // ADMIN
  // =========================

  if (role === "ADMIN") {

    return children;

  }

  // =========================
  // ATLETA SEM PERFIL
  // =========================

  if (
    role === "ATLETA" &&
    perfilCompleto !== "true"
  ) {

    return (
      <Navigate
        to="/completar-perfil"
      />
    );

  }

  // =========================
  // LIBERADO
  // =========================

  return children;

}