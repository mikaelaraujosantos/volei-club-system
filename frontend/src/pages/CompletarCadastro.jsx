import { useState } from "react"
import { useNavigate } from "react-router-dom"

import logo from "../assets/svc.png"

function CompletarCadastro() {

  const navigate = useNavigate()

  const [posicao, setPosicao] = useState("")
  const [altura, setAltura] = useState("")
  const [responsavel, setResponsavel] = useState("")

  async function salvar() {

    try {

      const token =
        localStorage.getItem("token")

      const response = await fetch(
        "http://localhost:8080/atletas",
        {
          method: "POST",

          headers: {

            "Content-Type": "application/json",

            Authorization:
              `Bearer ${token}`

          },

          body: JSON.stringify({

            posicao,
            altura:
              altura
                ? parseFloat(altura)
                : null,

            responsavel

          })

        }
      )

      if (response.ok) {

        alert("Cadastro completo!")

        navigate("/home")

      }

      else {

        alert("Erro ao completar cadastro")

      }

    }

    catch (erro) {

      console.error(erro)

      alert("Erro ao conectar")

    }

  }

  return (

    <main>

      <div className="topo">

        <img
          src={logo}
          className="logo"
        />

      </div>

      <div className="area-login">

        <h2>Completar Cadastro</h2>

        <div className="area-expandida ativa">

          <select
  className="input"
  value={posicao}
  onChange={(e) =>
    setPosicao(e.target.value)
  }
>

  <option value="">
    Não definido
  </option>

  <option value="Levantador">
    Levantador
  </option>

  <option value="Ponteiro">
    Ponteiro
  </option>

  <option value="Oposto">
    Oposto
  </option>

  <option value="Central">
    Central
  </option>

  <option value="Líbero">
    Líbero
  </option>

</select>

          <input
            className="input"
            placeholder="Altura (ex: 1.80)"
            type="number"
            step="0.01"
            value={altura}
            onChange={(e) =>
              setAltura(e.target.value)
            }
          />

          <input
            className="input"
            placeholder="Responsável"
            value={responsavel}
            onChange={(e) =>
              setResponsavel(e.target.value)
            }
          />

          <button
            className="button"
            onClick={salvar}
          >

            Finalizar Cadastro

          </button>

        </div>

      </div>

    </main>

  )

}

export default CompletarCadastro