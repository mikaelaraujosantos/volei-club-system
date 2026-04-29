import axios from "axios";

const API_URL = "http://localhost:8080/auth";

// função de login
const login = async (email, senha) => {
  try {
    const response = await axios.post(`${API_URL}/login`, {
      email,
      senha
    });

    // salva o token no localStorage
    if (response.data.token) {
      localStorage.setItem("token", response.data.token);
    }

    return response.data;

  } catch (error) {
    console.error("Erro no login:", error);
    throw error;
  }
};

// logout
const logout = () => {
  localStorage.removeItem("token");
};

// pegar token
const getToken = () => {
  return localStorage.getItem("token");
};

const AuthService = {
  login,
  logout,
  getToken
};

export default AuthService;