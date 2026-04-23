import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'

import Input from '../components/Input'
import Button from '../components/Button'

function Login() {

  const [email, setEmail] = useState("")
  const [senha, setSenha] = useState("")

  const navigate = useNavigate()

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

      console.log("STATUS:", response.status)

      if (!response.ok) {

        alert("Email ou senha inválidos")
        return

      }

      const data = await response.json()

      console.log("RESPOSTA:", data)

      // salva token
      localStorage.setItem("token", data.token)

      console.log(
        "Token salvo:",
        localStorage.getItem("token")
      )

      alert("Login realizado com sucesso")

      // redireciona
      navigate("/home")

    } catch (error) {

      console.error("ERRO:", error)

      alert("Erro ao conectar com o servidor")

    }

  }

  return (

    <main>
        <div className="area-login">
          <h2>Login</h2>
          </div>
        
      
  

      <div className="form">

        <Input
          type="email"
          placeholder="Digite seu email"
          value={email}
          onChange={(e) =>
            setEmail(e.target.value)
          }
        />

        <Input
          type="password"
          placeholder="Digite sua senha"
          value={senha}
          onChange={(e) =>
            setSenha(e.target.value)
          }
        />

        <Button
          text="Entrar"
          onClick={entrar}
        />

        <p>
          Não tem conta?{" "}
          <Link to="/cadastro">
            Criar cadastro
          </Link>
        </p>

      </div>

    </main>

  )

}

export default Login