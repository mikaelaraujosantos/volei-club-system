import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";

export default function EditarAtleta() {

  const { id } = useParams();

  const navigate = useNavigate();

  const [nome, setNome] = useState("");
  const [idade, setIdade] = useState("");
  const [telefone, setTelefone] = useState("");
  const [posicao, setPosicao] = useState("");
  const [altura, setAltura] = useState("");
  const [responsavel, setResponsavel] = useState("");
  const [ativo, setAtivo] = useState(true);

  // =========================
  // CARREGAR DADOS DO ATLETA
  // =========================

  useEffect(() => {

    async function carregar() {

      try {

        const token = localStorage.getItem("token");

        const resposta = await fetch(
          `http://localhost:8080/atletas/${id}`,
          {
            method: "GET",
            headers: {
              "Authorization": `Bearer ${token}`
            }
          }
        );

        const dados = await resposta.json();

        setNome(dados.nome);
        setIdade(dados.idade);
        setTelefone(dados.telefone);
        setPosicao(dados.posicao);
        setAltura(dados.altura);
        setResponsavel(dados.responsavel);
        setAtivo(dados.ativo);

      } catch (erro) {

        console.error("Erro ao carregar atleta:", erro);

      }

    }

    carregar();

  }, [id]);

  // =========================
  // SALVAR EDIÇÃO
  // =========================

  async function salvar() {

    try {

      const token = localStorage.getItem("token");

      const resposta = await fetch(
        `http://localhost:8080/atletas/${id}`,
        {
          method: "PUT",
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
            ativo
          })
        }
      );

      if (resposta.ok) {

        alert("Atleta atualizado");

        navigate("/admin");

      } else {

        alert("Erro ao atualizar atleta");

      }

    } catch (erro) {

      console.error("Erro ao salvar:", erro);

    }

  }

  // =========================
  // TELA
  // =========================

  return (

    <div>

      <h2>Editar Atleta</h2>

      <input
        placeholder="Nome"
        value={nome}
        onChange={(e) =>
          setNome(e.target.value)
        }
      />

      <br />

      <input
        placeholder="Idade"
        value={idade}
        onChange={(e) =>
          setIdade(e.target.value)
        }
      />

      <br />

      <input
        placeholder="Telefone"
        value={telefone}
        onChange={(e) =>
          setTelefone(e.target.value)
        }
      />

      <br />

      <input
        placeholder="Posição"
        value={posicao}
        onChange={(e) =>
          setPosicao(e.target.value)
        }
      />

      <br />

      <input
        placeholder="Altura"
        value={altura}
        onChange={(e) =>
          setAltura(e.target.value)
        }
      />

      <br />

      <input
        placeholder="Responsável"
        value={responsavel}
        onChange={(e) =>
          setResponsavel(e.target.value)
        }
      />

      <br />

      <button onClick={salvar}>
        Salvar
      </button>

    </div>

  );

}