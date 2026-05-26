import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import logo from "../assets/svc.png";

import Input from "../components/Input";
import Button from "../components/Button";

function CompletarPerfil() {

  const navigate = useNavigate();

  // =========================
  // STATES
  // =========================

  const [dataNascimento, setDataNascimento] = useState("");

  const [posicao, setPosicao] = useState("");

  const [altura, setAltura] = useState("");

  const [responsavel, setResponsavel] = useState("");

  const [
    telefoneResponsavel,
    setTelefoneResponsavel
  ] = useState("");

  const [menorIdade, setMenorIdade] =
    useState(false);

  const [carregando, setCarregando] =
    useState(false);

  // =========================
  // CALCULAR IDADE
  // =========================

  function calcularIdade(data) {

    const hoje = new Date();

    const nascimento =
      new Date(data);

    let idade =
      hoje.getFullYear() -
      nascimento.getFullYear();

    const mes =
      hoje.getMonth() -
      nascimento.getMonth();

    if (
      mes < 0 ||
      (
        mes === 0 &&
        hoje.getDate() <
        nascimento.getDate()
      )
    ) {

      idade--;

    }

    return idade;

  }

  // =========================
  // VERIFICAR MENOR IDADE
  // =========================

  useEffect(() => {

    if (dataNascimento) {

      const idade =
        calcularIdade(dataNascimento);

      setMenorIdade(idade < 18);

    }

  }, [dataNascimento]);

  // =========================
  // FORMATAR TELEFONE
  // =========================

  function formatarTelefone(valor) {

    let numeros =
      valor.replace(/\D/g, "");

    numeros =
      numeros.slice(0, 11);

    if (numeros.length <= 2) {

      setTelefoneResponsavel(
        numeros
      );

    }

    else if (numeros.length <= 7) {

      setTelefoneResponsavel(
        `(${numeros.slice(0,2)}) ${numeros.slice(2)}`
      );

    }

    else {

      setTelefoneResponsavel(
        `(${numeros.slice(0,2)}) ${numeros.slice(2,7)}-${numeros.slice(7)}`
      );

    }

  }

  // =========================
  // FINALIZAR PERFIL
  // =========================

  async function finalizarPerfil() {

    if (!dataNascimento) {

      alert(
        "Informe sua data de nascimento"
      );

      return;

    }

    if (!posicao) {

      alert(
        "Selecione sua posição"
      );

      return;

    }

    if (
      menorIdade &&
      (
        !responsavel ||
        !telefoneResponsavel
      )
    ) {

      alert(
        "Informe os dados do responsável"
      );

      return;

    }

    try {

      setCarregando(true);

      const token =
        localStorage.getItem("token");

      const response = await fetch(
        "http://localhost:8080/atletas/completar-perfil",
        {

          method: "PUT",

          headers: {

            "Content-Type":
              "application/json",

            Authorization:
              `Bearer ${token}`

          },

          body: JSON.stringify({

            dataNascimento,

            posicao,

            altura:
              altura
                ? parseFloat(altura)
                : null,

            responsavel,

            telefoneResponsavel

          })

        }
      );

      if (response.ok) {

        alert(
          "Perfil concluído com sucesso!"
        );

        localStorage.setItem(
  "perfilCompleto",
  true
);

navigate("/home");

      }

      else {

        const erro =
          await response.text();

        alert(
          "Erro: " + erro
        );

      }

    }

    catch (erro) {

      console.error(erro);

      alert(
        "Erro ao conectar com servidor"
      );

    }

    finally {

      setCarregando(false);

    }

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
          alt="Logo"
          className="logo"
        />

      </div>

      <div className="container-completar">

        <div className="card-completar">

          <h2>
            Completar Perfil
          </h2>

          <p className="subtitulo-completar">

            Finalize seu cadastro
            para acessar o sistema

          </p>

          <div className="form-completar">

            {/* DATA NASCIMENTO */}

            <label className="label-completar">

              Data de nascimento

            </label>

            <Input
              type="date"
              value={dataNascimento}
              onChange={(e) =>
                setDataNascimento(
                  e.target.value
                )
              }
            />

            {/* POSIÇÃO */}

            <label className="label-completar">

              Posição

            </label>

            <select
              className="select-completar"
              value={posicao}
              onChange={(e) =>
                setPosicao(
                  e.target.value
                )
              }
            >

              <option value="">
                Selecione
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

              <option value="Não definido">
                Não definido
              </option>

            </select>

            {/* ALTURA */}

            <label className="label-completar">

              Altura

            </label>

            <Input
              type="number"
              step="0.01"
              placeholder="Ex: 1.82"
              value={altura}
              onChange={(e) =>
                setAltura(
                  e.target.value
                )
              }
            />

            {/* MENOR IDADE */}

            {menorIdade && (

              <>

                <div className="info-menor">

                  Menores de idade precisam
                  informar um responsável.

                </div>

                <div className="grupo-responsavel">

                  <label className="label-completar">

                    Nome do responsável

                  </label>

                  <Input
                    type="text"
                    placeholder="Nome completo"
                    value={responsavel}
                    onChange={(e) =>
                      setResponsavel(
                        e.target.value
                      )
                    }
                  />

                  <label className="label-completar">

                    Telefone do responsável

                  </label>

                  <Input
                    type="tel"
                    placeholder="(74) 99999-9999"
                    value={telefoneResponsavel}
                    onChange={(e) =>
                      formatarTelefone(
                        e.target.value
                      )
                    }
                  />

                </div>

              </>

            )}

            {/* BOTÃO */}

            <Button
              text={
                carregando
                  ? "Salvando..."
                  : "Finalizar Cadastro"
              }
              onClick={finalizarPerfil}
              className="botao-finalizar"
            />

          </div>

        </div>

      </div>

      {/* RODAPÉ */}

      <div className="rodape">

        Sobradinho Vôlei Clube ©

      </div>

    </main>

  );

}

export default CompletarPerfil;