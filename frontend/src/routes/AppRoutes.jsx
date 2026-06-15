import { BrowserRouter, Routes, Route } from 'react-router-dom'

import Inicio from '../pages/Inicio'
import CompletarCadastro from "../pages/CompletarCadastro"
import Cadastro from '../pages/Cadastro'
import Home from '../pages/Home'
import Admin from '../pages/Admin'
import EditarAtleta from "../pages/EditarAtleta";
import ProtectedRoute from './ProtectedRoute'
import CompletarPerfil from "../pages/CompletarPerfil";
import VisualizarAtleta from "../pages/VisualizarAtleta";
function AppRoutes() {

  return (

    <BrowserRouter>

      <Routes>

        <Route path="/" element={<Inicio />} />
        <Route path="/cadastro" element={<Cadastro />} />

        {/* ROTAS PROTEGIDAS */}

        <Route
          path="/home"
          element={
            <ProtectedRoute>
              <Home />
            </ProtectedRoute>
          }
        />

        <Route
          path="/completar-cadastro"
          element={
            <ProtectedRoute>
              <CompletarCadastro />
            </ProtectedRoute>
          }
        />

        <Route
          path="/visualizar/:id"
          element={<VisualizarAtleta />}
        />

        <Route
          path="/admin"
          element={
            <ProtectedRoute>
              <Admin />
            </ProtectedRoute>
          }
        />

        <Route
          path="/editar/:id"
          element={<EditarAtleta />}
        />

        <Route
          path="/completar-perfil"
          element={<CompletarPerfil />}
        />

      </Routes>

    </BrowserRouter>

  )

}

export default AppRoutes