import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

import logo from '../assets/svc.png'

import Input from '../components/Input'
import Button from '../components/Button'

function Cadastro() {

  const navigate = useNavigate()

  const [nome, setNome] = useState("")
  const [idade, setIdade] = useState("")
  const [telefone, setTelefone] = useState("")
  const [email, setEmail] = useState("")
  const [senha, setSenha] = useState("")
  const [confirmarSenha, setConfirmarSenha] = useState("")

  const [erroNome, setErroNome] = useState("")
  const [erroIdade, setErroIdade] = useState("")
  const [erroTelefone, setErroTelefone] = useState("")
  const [erroEmail, setErroEmail] = useState("")
  const [erroSenha, setErroSenha] = useState("")
  const [erroConfirmar, setErroConfirmar] = useState("")

  function formatarTelefone(valor) {
    let numeros = valor.replace(/\D/g, "")
    numeros = numeros.slice(0, 11)

    if (numeros.length <= 2) {
      setTelefone(numeros)
    } else if (numeros.length <= 7) {
      setTelefone(`(${numeros.slice(0,2)}) ${numeros.slice(2)}`)
    } else {
      setTelefone(`(${numeros.slice(0,2)}) ${numeros.slice(2,7)}-${numeros.slice(7)}`)
    }
  }

  async function cadastrar() {
    let valido = true

    setErroNome("")
    setErroIdade("")
    setErroTelefone("")
    setErroEmail("")
    setErroSenha("")
    setErroConfirmar("")

    if (!nome) {
      setErroNome("Digite seu nome")
      valido = false
    }

    if (!idade) {
      setErroIdade("Digite sua idade")
      valido = false
    }

    if (telefone.length < 14) {
      setErroTelefone("Telefone incompleto")
      valido = false
    }

    if (!email.includes("@")) {
      setErroEmail("Email inválido")
      valido = false
    }

    if (!senha) {
      setErroSenha("Digite uma senha")
      valido = false
    }

    if (senha !== confirmarSenha) {
      setErroConfirmar("As senhas não coincidem")
      valido = false
    }

    if (!valido) return

    const atleta = {
      nome,
      idade: Number(idade),
      telefone,
      email,
      senha
    }

    try {
      const response = await fetch(
        "http://localhost:8080/atletas/cadastro-completo",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(atleta)
        }
      )

      if (response.ok) {
        alert("Cadastro realizado com sucesso!")
        navigate("/")
      } else {
        const erro = await response.text()
        alert("Erro ao cadastrar: " + erro)
      }
    } catch (error) {
      console.error(error)
      alert("Erro ao conectar com o servidor")
    }
  }

  function voltar() {
    navigate("/")
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
        <h2>Cadastro de Atleta</h2>

        <div className="area-expandida ativa">
          <Input
            type="text"
            placeholder="Nome completo"
            value={nome}
            onChange={(e) => setNome(e.target.value)}
          />

          {erroNome && (
            <span className="erro">
              {erroNome}
            </span>
          )}

          <Input
            type="number"
            placeholder="Idade"
            value={idade}
            onChange={(e) => setIdade(e.target.value)}
          />

          {erroIdade && (
            <span className="erro">
              {erroIdade}
            </span>
          )}

          <Input
            type="tel"
            placeholder="Telefone"
            value={telefone}
            onChange={(e) => formatarTelefone(e.target.value)}
          />

          {erroTelefone && (
            <span className="erro">
              {erroTelefone}
            </span>
          )}

          <Input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          {erroEmail && (
            <span className="erro">
              {erroEmail}
            </span>
          )}

          <Input
            type="password"
            placeholder="Senha"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
          />

          {erroSenha && (
            <span className="erro">
              {erroSenha}
            </span>
          )}

          <Input
            type="password"
            placeholder="Confirmar senha"
            value={confirmarSenha}
            onChange={(e) => setConfirmarSenha(e.target.value)}
          />

          {erroConfirmar && (
            <span className="erro">
              {erroConfirmar}
            </span>
          )}

          <Button
            text="Cadastrar"
            onClick={cadastrar}
          />

          <button
            className="button-secundario"
            onClick={voltar}
          >
            Voltar
          </button>
        </div>
      </div>

      <div className="rodape">
        Sobradinho Vôlei Clube ©
      </div>
    </main>
  )
}

export default Cadastro