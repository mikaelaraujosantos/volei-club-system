import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

export default function VisualizarAtleta() {

  const { id } = useParams();

  const navigate = useNavigate();

  const [atleta, setAtleta] = useState(null);

  const [carregando, setCarregando] = useState(true);

  useEffect(() => {

    carregarAtleta();

  }, []);

  async function carregarAtleta() {

    try {

      const token =
        localStorage.getItem("token");

      const response = await fetch(
        `http://localhost:8080/atletas/${id}`,
        {
          method: "GET",
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      if (!response.ok) {

        throw new Error(
          "Erro ao carregar atleta"
        );

      }

      const data =
        await response.json();

      setAtleta(data);

    }

    catch (erro) {

      console.error(erro);

      alert(
        "Erro ao carregar atleta"
      );

    }

    finally {

      setCarregando(false);

    }

  }

  if (carregando) {

    return (

      <div className="admin-container">

        <h2>Carregando...</h2>

      </div>

    );

  }

  if (!atleta) {

    return (

      <div className="admin-container">

        <h2>Atleta não encontrado</h2>

      </div>

    );

  }

  return (

    <div className="admin-container">

      <div className="card">

        <h1>Informações do Atleta</h1>

        <hr />

        <p>
          <strong>ID:</strong> {atleta.id}
        </p>

        <p>
          <strong>Nome:</strong> {atleta.nome}
        </p>

        <p>
          <strong>Idade:</strong> {atleta.idade}
        </p>

        <p>
          <strong>Data de Nascimento:</strong>{" "}
          {atleta.dataNascimento || "-"}
        </p>

        <p>
          <strong>Telefone:</strong>{" "}
          {atleta.telefone || "-"}
        </p>

        <p>
          <strong>Posição:</strong>{" "}
          {atleta.posicao || "Não definida"}
        </p>

        <p>
          <strong>Altura:</strong>{" "}
          {atleta.altura
            ? `${atleta.altura} m`
            : "-"}
        </p>

        <p>
          <strong>Responsável:</strong>{" "}
          {atleta.responsavel || "-"}
        </p>

        <p>
          <strong>Telefone Responsável:</strong>{" "}
          {atleta.telefoneResponsavel || "-"}
        </p>

        <p>
          <strong>Perfil Completo:</strong>{" "}
          {atleta.perfilCompleto
            ? "Sim"
            : "Não"}
        </p>

        <p>
          <strong>Status:</strong>{" "}
          {atleta.ativo
            ? "Ativo"
            : "Pendente/Inativo"}
        </p>

        <br />

        <button
          className="btn-primary"
          onClick={() =>
            navigate("/admin")
          }
        >
          Voltar
        </button>

      </div>

    </div>

  );

}