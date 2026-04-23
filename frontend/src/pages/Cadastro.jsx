import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

import logo from '../assets/svc.png'

import Input from '../components/Input'
import Button from '../components/Button'

function Cadastro() {

  const navigate = useNavigate()

  // =========================
  // STATES DOS CAMPOS
  // =========================

  const [nome, setNome] = useState("")
  const [idade, setIdade] = useState("")
  const [telefone, setTelefone] = useState("")
  const [email, setEmail] = useState("")
  const [senha, setSenha] = useState("")
  const [confirmarSenha, setConfirmarSenha] = useState("")

  // =========================
  // STATES DE ERRO
  // =========================

  const [erroNome, setErroNome] = useState("")
  const [erroIdade, setErroIdade] = useState("")
  const [erroTelefone, setErroTelefone] = useState("")
  const [erroEmail, setErroEmail] = useState("")
  const [erroSenha, setErroSenha] = useState("")
  const [erroConfirmar, setErroConfirmar] = useState("")

  // =========================
  // MÁSCARA TELEFONE
  // =========================

  function formatarTelefone(valor) {

    let numeros = valor.replace(/\D/g, "")

    numeros = numeros.slice(0, 11)

    if (numeros.length <= 2) {

      setTelefone(numeros)

    }

    else if (numeros.length <= 7) {

      setTelefone(
        `(${numeros.slice(0,2)}) ${numeros.slice(2)}`
      )

    }

    else {

      setTelefone(
        `(${numeros.slice(0,2)}) ${numeros.slice(2,7)}-${numeros.slice(7)}`
      )

    }

  }

  // =========================
  // FUNÇÃO CADASTRAR
  // =========================

  async function cadastrar() {

  let valido = true

  // limpa erros

  setErroNome("")
  setErroIdade("")
  setErroTelefone("")
  setErroEmail("")
  setErroSenha("")
  setErroConfirmar("")

  // NOME

  if (!nome) {

    setErroNome("Digite seu nome")
    valido = false

  }

  // IDADE

  if (!idade) {

    setErroIdade("Digite sua idade")
    valido = false

  }

  else if (idade < 8 || idade > 50) {

    setErroIdade("Idade deve ser entre 8 e 50 anos")
    valido = false

  }

  // TELEFONE

  if (telefone.length < 14) {

    setErroTelefone("Telefone incompleto")
    valido = false

  }

  // EMAIL

  if (!email.includes("@")) {

    setErroEmail("Email inválido")
    valido = false

  }

  // SENHA

  if (!senha) {

    setErroSenha("Digite uma senha")
    valido = false

  }

  // CONFIRMAR SENHA

  if (senha !== confirmarSenha) {

    setErroConfirmar("As senhas não coincidem")
    valido = false

  }

  if (!valido) return

 
  // ENVIO PARA BACKEND

  const usuario = {

    nome,
    idade: Number(idade),
    telefone,
    email,
    senha

  }

  try {

    const response = await fetch(
      "http://localhost:8080/usuarios",
      {

        method: "POST",

        headers: {
          "Content-Type": "application/json"
        },

        body: JSON.stringify(usuario)

      }
    )

    if (response.ok) {

      alert("Cadastro realizado com sucesso!")

      navigate("/")

    }

    else {

      alert("Erro ao cadastrar usuário")

    }

  }

  catch (error) {

    console.error(error)

    alert("Erro ao conectar com o servidor")

  }

}

  // =========================
  // VOLTAR
  // =========================

  function voltar() {

    navigate("/")

  }

  // =========================
  // JSX
  // =========================

  return (

    <main>

      {/* ESCUDO */}

      <div className="topo">

        <img
          src={logo}
          alt="Escudo"
          className="logo"
        />

      </div>


      {/* FORMULÁRIO */}

      <div className="area-login">

        <h2>Cadastro de Atleta</h2>

        <div className="area-expandida ativa">

          {/* NOME */}

          <Input
            type="text"
            placeholder="Nome completo"
            value={nome}
            onChange={(e) =>
              setNome(e.target.value)
            }
            erro={erroNome}
          />

          {erroNome && (
            <span className="erro">
              {erroNome}
            </span>
          )}


          {/* IDADE */}

          <Input
            type="number"
            placeholder="Idade"
            value={idade}
            onChange={(e) =>
              setIdade(e.target.value)
            }
            erro={erroIdade}
          />

          {erroIdade && (
            <span className="erro">
              {erroIdade}
            </span>
          )}


          {/* TELEFONE */}

          <Input
            type="tel"
            placeholder="Telefone"
            value={telefone}
            onChange={(e) =>
              formatarTelefone(e.target.value)
            }
            erro={erroTelefone}
          />

          {erroTelefone && (
            <span className="erro">
              {erroTelefone}
            </span>
          )}


          {/* EMAIL */}

          <Input
            type="email"
            placeholder="Email"
            value={email}
            onChange={(e) =>
              setEmail(e.target.value)
            }
            erro={erroEmail}
          />

          {erroEmail && (
            <span className="erro">
              {erroEmail}
            </span>
          )}


          {/* SENHA */}

          <Input
            type="password"
            placeholder="Senha"
            value={senha}
            onChange={(e) =>
              setSenha(e.target.value)
            }
            erro={erroSenha}
          />

          {erroSenha && (
            <span className="erro">
              {erroSenha}
            </span>
          )}


          {/* CONFIRMAR SENHA */}

          <Input
            type="password"
            placeholder="Confirmar senha"
            value={confirmarSenha}
            onChange={(e) =>
              setConfirmarSenha(e.target.value)
            }
            erro={erroConfirmar}
          />

          {erroConfirmar && (
            <span className="erro">
              {erroConfirmar}
            </span>
          )}


          {/* BOTÃO CADASTRAR */}

          <Button
            text="Cadastrar"
            onClick={cadastrar}
          />


          {/* BOTÃO VOLTAR */}

          <button
            className="button-secundario"
            onClick={voltar}
          >

            Voltar

          </button>

        </div>

      </div>


      {/* RODAPÉ */}

      <div className="rodape">

        Sobradinho Vôlei Clube ©

      </div>

    </main>

  )

}

export default Cadastro