import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

import logo from '../assets/svc.png'

import Input from '../components/Input'
import Button from '../components/Button'

function Inicio() {

  const navigate = useNavigate()

  const [aberto, setAberto] = useState(false)

  const [email, setEmail] = useState("")
  const [senha, setSenha] = useState("")

  function toggleLogin() {

    setAberto(!aberto)

  }

  async function entrar() {

    if (email === "" || senha === "") {

      alert("Preencha email e senha")
      return

    }

    try {

      const response = await fetch(
        "http://localhost:8080/auth/login",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            email: email,
            senha: senha
          })
        }
      )

      if (!response.ok) {

        alert("Email ou senha inválidos")
        return

      }

      const data = await response.json()

      // salva token
      localStorage.setItem("token", data.token)

      alert("Login realizado com sucesso")

      navigate("/home")

    } catch (error) {

      console.error(error)

      alert("Erro ao conectar com o servidor")

    }

  }

  function irCadastro() {

    navigate("/cadastro")

  }

  return (

    <main>

      <div className="topo">

        <img
          src={logo}
          alt="Escudo"
          className="logo"
        />

      </div>

      <div className="area-login">

        <button
          className="botao-login"
          onClick={toggleLogin}
        >

          Login

        </button>

        <div
          className={`area-expandida ${aberto ? "ativa" : ""}`}
        >

          <Input
            type="email"
            placeholder="Digite seu email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          <Input
            type="password"
            placeholder="Digite sua senha"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
          />

          <Button
            text="Entrar"
            onClick={entrar}
          />

          <Button
            text="Criar Cadastro"
            onClick={irCadastro}
          />

        </div>

      </div>

      <div className="rodape">

        Sobradinho Vôlei Clube ©

      </div>

    </main>

  )

}

export default Inicio