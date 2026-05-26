import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function ListaAtletas() {

  const [atletas, setAtletas] = useState([]);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState(null);

  const [pagina, setPagina] = useState(0);
  const [totalPaginas, setTotalPaginas] = useState(0);

  const [busca, setBusca] = useState("");

  const navigate = useNavigate();

  // =========================
  // BUSCAR ATLETAS
  // =========================

  useEffect(() => {

    buscarAtletas();

  }, [pagina]);

  async function buscarAtletas() {

    setCarregando(true);

    setErro(null);

    try {

      const token =
        localStorage.getItem("token");

      const response = await fetch(
        `http://localhost:8080/atletas?page=${pagina}&size=10`,
        {

          method: "GET",

          headers: {

            Authorization:
              `Bearer ${token}`,

            "Content-Type":
              "application/json"

          }

        }
      );

      if (!response.ok) {

        throw new Error(
          `HTTP ${response.status}`
        );

      }

      const data =
        await response.json();

      setAtletas(data.content);

      setTotalPaginas(
        data.totalPages
      );

    }

    catch (erro) {

      console.error(erro);

      setErro(erro.message);

      setAtletas([]);

    }

    finally {

      setCarregando(false);

    }

  }

  // =========================
  // EDITAR
  // =========================

  function editarAtleta(id) {

    navigate(`/editar/${id}`);

  }

  // =========================
  // INATIVAR
  // =========================

  async function excluirAtleta(id) {

    if (
      !window.confirm(
        "Deseja inativar este atleta?"
      )
    ) {

      return;

    }

    try {

      const token =
        localStorage.getItem("token");

      const response = await fetch(
        `http://localhost:8080/atletas/${id}`,
        {

          method: "DELETE",

          headers: {

            Authorization:
              `Bearer ${token}`,

            "Content-Type":
              "application/json"

          }

        }
      );

      if (response.ok) {

        buscarAtletas();

        alert(
          "Atleta inativado!"
        );

      }

      else {

        const erro =
          await response.text();

        alert(
          "Erro ao inativar: "
          + erro
        );

      }

    }

    catch (error) {

      console.error(error);

      alert(
        "Erro ao inativar atleta"
      );

    }

  }

  // =========================
  // ATIVAR
  // =========================

  async function ativarAtleta(id) {

    try {

      const token =
        localStorage.getItem("token");

      const response = await fetch(
        `http://localhost:8080/atletas/${id}/ativar`,
        {

          method: "PUT",

          headers: {

            Authorization:
              `Bearer ${token}`,

            "Content-Type":
              "application/json"

          }

        }
      );

      if (response.ok) {

        buscarAtletas();

        alert(
          "Atleta ativado!"
        );

      }

      else {

        const erro =
          await response.text();

        alert(
          "Erro ao ativar: "
          + erro
        );

      }

    }

    catch (error) {

      console.error(error);

      alert(
        "Erro ao ativar atleta"
      );

    }

  }

  // =========================
  // FILTRO
  // =========================

  const atletasFiltrados =
    atletas.filter(atleta =>

      atleta.nome
        .toLowerCase()
        .includes(
          busca.toLowerCase()
        )

    );

  // =========================
  // LOADING
  // =========================

  if (carregando) {

    return (

      <div className="loading">

        Carregando atletas...

      </div>

    );

  }

  // =========================
  // ERRO
  // =========================

  if (erro) {

    return (

      <div className="erro-box">

        <p>
          Erro ao carregar atletas:
          {" "}
          {erro}
        </p>

        <button
          className="btn-primary"
          onClick={buscarAtletas}
        >

          Tentar novamente

        </button>

      </div>

    );

  }

  // =========================
  // JSX
  // =========================

  return (

    <div>

      {/* BUSCA */}

      <div className="busca-container">

        <input
          type="text"
          placeholder="🔍 Buscar atleta..."
          value={busca}
          onChange={(e) =>
            setBusca(
              e.target.value
            )
          }
          className="input-busca"
        />

      </div>

      {/* TÍTULO */}

      <h2>

        Atletas cadastrados
        {" "}
        ({atletasFiltrados.length})

      </h2>

      {/* TABELA */}

      {atletasFiltrados.length === 0 ? (

        <p>
          Nenhum atleta encontrado
        </p>

      ) : (

        <div className="table-container">

          <table className="table">

            <thead>

              <tr>

                <th>ID</th>
                <th>Nome</th>
                <th>Idade</th>
                <th>Telefone</th>
                <th>Posição</th>
                <th>Status</th>
                <th>Ações</th>

              </tr>

            </thead>

            <tbody>

              {atletasFiltrados.map(atleta => (

                <tr key={atleta.id}>

                  <td>{atleta.id}</td>

                  <td>{atleta.nome}</td>

                  <td>{atleta.idade}</td>

                  <td>{atleta.telefone}</td>

                  <td>
                    {atleta.posicao || "-"}
                  </td>

                  <td>

                    <span
                      className={
                        atleta.ativo
                          ? "badge badge-paga"
                          : "badge badge-cancelada"
                      }
                    >

                      {atleta.ativo
                        ? "Ativo"
                        : "Inativo"}

                    </span>

                  </td>

                  <td>

                    <button
                      className="btn-sm btn-success"
                      onClick={() =>
                        editarAtleta(
                          atleta.id
                        )
                      }
                    >

                      Editar

                    </button>

                    {" "}

                    {atleta.ativo ? (

                      <button
                        className="btn-sm btn-danger"
                        onClick={() =>
                          excluirAtleta(
                            atleta.id
                          )
                        }
                      >

                        Inativar

                      </button>

                    ) : (

                      <button
                        className="btn-sm btn-success"
                        onClick={() =>
                          ativarAtleta(
                            atleta.id
                          )
                        }
                      >

                        Ativar

                      </button>

                    )}

                  </td>

                </tr>

              ))}

            </tbody>

          </table>

        </div>

      )}

      {/* PAGINAÇÃO */}

      <div className="paginacao">

        <button
          className="btn-sm"
          disabled={pagina === 0}
          onClick={() =>
            setPagina(
              pagina - 1
            )
          }
        >

          ← Anterior

        </button>

        <span>

          Página {pagina + 1}
          {" "}
          de {totalPaginas}

        </span>

        <button
          className="btn-sm"
          disabled={
            pagina >= totalPaginas - 1
          }
          onClick={() =>
            setPagina(
              pagina + 1
            )
          }
        >

          Próxima →

        </button>

      </div>

    </div>

  );

}