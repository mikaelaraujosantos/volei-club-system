import { useState } from "react";

import ListaAtletas from "../components/ListaAtletas";

export default function Admin() {

  const [nome, setNome] = useState("");
  const [idade, setIdade] = useState("");
  const [telefone, setTelefone] = useState("");
  const [posicao, setPosicao] = useState("");
  const [altura, setAltura] = useState("");
  const [responsavel, setResponsavel] = useState("");

  async function cadastrarAtleta() {

    if (
      nome === "" ||
      idade === "" ||
      telefone === ""
    ) {

      alert("Preencha os campos obrigatórios");
      return;

    }

    try {

      const token =
        localStorage.getItem("token");

      const resposta = await fetch(
        "http://localhost:8080/atletas",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
          },
          body: JSON.stringify({
            nome,
            idade,
            telefone,
            posicao,
            altura,
            responsavel,
            ativo: true
          })
        }
      );

      if (resposta.ok) {

        alert("Atleta cadastrado");

        limparCampos();

        window.location.reload();

      } else {

        alert("Erro ao cadastrar atleta");

      }

    } catch (erro) {

      console.error(erro);

    }

  }

  function limparCampos() {

    setNome("");
    setIdade("");
    setTelefone("");
    setPosicao("");
    setAltura("");
    setResponsavel("");

  }

  return (

    <div className="admin-container">

      <h1>Painel do Administrador</h1>

      {/* CARD CADASTRO */}

      <div className="card">

        <h2>Cadastrar Atleta</h2>

        <div className="form-grid">

          <input
            placeholder="Nome"
            value={nome}
            onChange={(e) =>
              setNome(e.target.value)
            }
          />

          <input
            placeholder="Idade"
            value={idade}
            onChange={(e) =>
              setIdade(e.target.value)
            }
          />

          <input
            placeholder="Telefone"
            value={telefone}
            onChange={(e) =>
              setTelefone(e.target.value)
            }
          />

          <input
            placeholder="Posição"
            value={posicao}
            onChange={(e) =>
              setPosicao(e.target.value)
            }
          />

          <input
            placeholder="Altura"
            value={altura}
            onChange={(e) =>
              setAltura(e.target.value)
            }
          />

          <input
            placeholder="Responsável"
            value={responsavel}
            onChange={(e) =>
              setResponsavel(e.target.value)
            }
          />

        </div>

        <button
          className="btn-primary"
          onClick={cadastrarAtleta}
        >
          Cadastrar
        </button>

      </div>

      {/* CARD LISTA */}

      <div className="card">

        

        <ListaAtletas />

      </div>

    </div>

  );

}