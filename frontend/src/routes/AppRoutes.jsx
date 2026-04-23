import { BrowserRouter, Routes, Route } from 'react-router-dom'

import Inicio from '../pages/Inicio'
import Login from '../pages/Login'
import Cadastro from '../pages/Cadastro'
import Home from '../pages/Home'
import Admin from '../pages/Admin'

import ProtectedRoute from './ProtectedRoute'

function AppRoutes() {

  return (

    <BrowserRouter>

      <Routes>

        <Route path="/" element={<Inicio />} />

        <Route path="/login" element={<Login />} />

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
          path="/admin"
          element={
            <ProtectedRoute>
              <Admin />
            </ProtectedRoute>
          }
        />

      </Routes>

    </BrowserRouter>

  )

}

export default AppRoutes