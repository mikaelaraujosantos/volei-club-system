import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function ListaAtletas() {

  const [atletas, setAtletas] = useState([]);

  const navigate = useNavigate();

  // =========================
  // CARREGAR ATLETAS
  // =========================

  useEffect(() => {

    async function carregar() {

      try {

        const token = localStorage.getItem("token");

        const resposta = await fetch(
          "http://localhost:8080/atletas",
          {
            method: "GET",
            headers: {
              "Authorization": `Bearer ${token}`
            }
          }
        );

        if (!resposta.ok) {

          console.error("Erro ao buscar atletas");
          return;

        }

        const dados = await resposta.json();

        // backend retorna Page
        setAtletas(dados.content);

      } catch (erro) {

        console.error(
          "Erro ao buscar atletas:",
          erro
        );

      }

    }

    carregar();

  }, []);

  // =========================
  // EDITAR ATLETA
  // =========================

  function editarAtleta(id) {

    navigate(`/editar/${id}`);

  }

  // =========================
  // EXCLUIR ATLETA
  // =========================

  async function excluirAtleta(id) {

    const confirmar =
      window.confirm(
        "Deseja excluir este atleta?"
      );

    if (!confirmar) return;

    try {

      const token =
        localStorage.getItem("token");

      const resposta = await fetch(
        `http://localhost:8080/atletas/${id}`,
        {
          method: "DELETE",
          headers: {
            "Authorization": `Bearer ${token}`
          }
        }
      );

      if (resposta.ok) {

        alert("Atleta excluído");

        recarregarLista();

      } else {

        alert("Erro ao excluir atleta");

      }

    } catch (erro) {

      console.error(
        "Erro ao excluir atleta:",
        erro
      );

    }

  }

  // =========================
  // RECARREGAR LISTA
  // =========================

  async function recarregarLista() {

    try {

      const token = localStorage.getItem("token");

      const resposta = await fetch(
        "http://localhost:8080/atletas",
        {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`
          }
        }
      );

      const dados = await resposta.json();

      setAtletas(dados.content);

    } catch (erro) {

      console.error(
        "Erro ao recarregar:",
        erro
      );

    }

  }

  // =========================
  // TELA
  // =========================

  return (

    <div>

      <h2>Lista de Atletas</h2>

      {atletas.length === 0 ? (

        <p>Nenhum atleta cadastrado</p>

      ) : (

        <ul>

          {atletas.map((atleta) => (

            <li key={atleta.id}>

              <strong>
                {atleta.nome}
              </strong>

              {" - "}
              {atleta.posicao}

              {" - "}
              {atleta.idade} anos

              {" "}

              <button
                onClick={() =>
                  editarAtleta(atleta.id)
                }
              >
                Editar
              </button>

              {" "}

              <button
                onClick={() =>
                  excluirAtleta(atleta.id)
                }
              >
                Excluir
              </button>

            </li>

          ))}

        </ul>

      )}

    </div>

  );

}