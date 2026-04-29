import { Navigate } from "react-router-dom"

function ProtectedRoute({ children }) {

  const token = localStorage.getItem("token")

  // se NÃO tiver token → volta login
  if (!token) {

    return <Navigate to="/" />

  }

  // se tiver token → entra
  return children

}

export default ProtectedRoute