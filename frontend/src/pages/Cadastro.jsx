import { useState } from 'react'
import { useNavigate } from 'react-router-dom'

import logo from '../assets/svc.png'

import Input from '../components/Input'
import Button from '../components/Button'

function Cadastro() {

  const navigate = useNavigate()

  // =========================
  // STATES
  // =========================

  const [nome, setNome] = useState("")
  const [telefone, setTelefone] = useState("")
  const [email, setEmail] = useState("")
  const [senha, setSenha] = useState("")
  const [confirmarSenha, setConfirmarSenha] = useState("")

  // =========================
  // ERROS
  // =========================

  const [erroNome, setErroNome] = useState("")
  const [erroTelefone, setErroTelefone] = useState("")
  const [erroEmail, setErroEmail] = useState("")
  const [erroSenha, setErroSenha] = useState("")
  const [erroConfirmar, setErroConfirmar] = useState("")

  // =========================
  // FORMATAR TELEFONE
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
  // CADASTRAR
  // =========================

  async function cadastrar() {

    let valido = true

    // limpar erros

    setErroNome("")
    setErroTelefone("")
    setErroEmail("")
    setErroSenha("")
    setErroConfirmar("")

    // =========================
    // VALIDAÇÕES
    // =========================

    if (!nome) {

      setErroNome("Digite seu nome")

      valido = false

    }

    if (telefone.length < 14) {

      setErroTelefone(
        "Telefone incompleto"
      )

      valido = false

    }

    if (!email.includes("@")) {

      setErroEmail(
        "Email inválido"
      )

      valido = false

    }

    if (!senha) {

      setErroSenha(
        "Digite uma senha"
      )

      valido = false

    }

    if (senha.length < 6) {

      setErroSenha(
        "A senha deve ter pelo menos 6 caracteres"
      )

      valido = false

    }

    if (senha !== confirmarSenha) {

      setErroConfirmar(
        "As senhas não coincidem"
      )

      valido = false

    }

    if (!valido) return

    // =========================
    // OBJETO
    // =========================

    const atleta = {

      nome,

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

        alert(
          "Cadastro realizado com sucesso!\n\nSeu perfil está aguardando aprovação do administrador."
        )

        navigate("/")

      }

      else {

        const erro =
          await response.text()

        alert(
          "Erro ao cadastrar: " + erro
        )

      }

    }

    catch (error) {

      console.error(error)

      alert(
        "Erro ao conectar com o servidor"
      )

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

      {/* TOPO */}

      <div className="topo">

        <img
          src={logo}
          alt="Escudo"
          className="logo"
        />

      </div>

      {/* FORM */}

      <div className="area-login">

        <h2>
          Cadastro de Atleta
        </h2>

        <div className="area-expandida ativa">

          {/* NOME */}

          <Input
            type="text"
            placeholder="Nome completo"
            value={nome}
            onChange={(e) =>
              setNome(e.target.value)
            }
          />

          {erroNome && (

            <span className="erro">
              {erroNome}
            </span>

          )}

          {/* TELEFONE */}

          <Input
            type="tel"
            placeholder="Telefone"
            value={telefone}
            onChange={(e) =>
              formatarTelefone(
                e.target.value
              )
            }
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
          />

          {erroSenha && (

            <span className="erro">
              {erroSenha}
            </span>

          )}

          {/* CONFIRMAR */}

          <Input
            type="password"
            placeholder="Confirmar senha"
            value={confirmarSenha}
            onChange={(e) =>
              setConfirmarSenha(
                e.target.value
              )
            }
          />

          {erroConfirmar && (

            <span className="erro">
              {erroConfirmar}
            </span>

          )}

          {/* BOTÃO */}

          <Button
            text="Cadastrar"
            onClick={cadastrar}
          />

          {/* VOLTAR */}

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