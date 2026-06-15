import { useState } from "react";

import ListaAtletas from "../components/ListaAtletas";
import ListaMensalidades from "../components/ListaMensalidades";
import CriarMensalidade from "../components/CriarMensalidade";

export default function Admin() {

  const [mostrarModalMensalidade, setMostrarModalMensalidade] =
    useState(false);

  const [abaAtiva, setAbaAtiva] =
    useState("atletas");

  return (

    <div className="admin-container">

      <h1>Painel do Administrador</h1>

      {/* =========================
          ABAS
      ========================= */}

      <div className="abas">

        <button
          className={
            abaAtiva === "atletas"
              ? "aba-ativa"
              : ""
          }
          onClick={() => setAbaAtiva("atletas")}
        >
          Gerenciar Atletas
        </button>

        <button
          className={
            abaAtiva === "aprovacoes"
              ? "aba-ativa"
              : ""
          }
          onClick={() => setAbaAtiva("aprovacoes")}
        >
          Aprovações
        </button>

        <button
          className={
            abaAtiva === "mensalidades"
              ? "aba-ativa"
              : ""
          }
          onClick={() => setAbaAtiva("mensalidades")}
        >
          Gerenciar Mensalidades
        </button>

      </div>

      {/* =========================
          ABA ATLETAS
      ========================= */}

      {abaAtiva === "atletas" && (

        <div className="card">

          <ListaAtletas
            mostrarInativos={false}
          />

        </div>

      )}

      {/* =========================
          ABA APROVAÇÕES
      ========================= */}

      {abaAtiva === "aprovacoes" && (

        <div className="card">

          <h2>
            Atletas aguardando aprovação
          </h2>

          <ListaAtletas
            somenteInativos={true}
          />

        </div>

      )}

      {/* =========================
          ABA MENSALIDADES
      ========================= */}

      {abaAtiva === "mensalidades" && (

        <div className="card">

          <div className="header-mensalidades">

            <h2>Mensalidades</h2>

            <button
              className="btn-primary"
              onClick={() =>
                setMostrarModalMensalidade(true)
              }
            >
              + Nova Mensalidade
            </button>

          </div>

          <ListaMensalidades />

        </div>

      )}

      {/* =========================
          MODAL MENSALIDADE
      ========================= */}

      {mostrarModalMensalidade && (

        <CriarMensalidade

          onSuccess={() => {

            setMostrarModalMensalidade(false);

            window.location.reload();

          }}

          onClose={() =>
            setMostrarModalMensalidade(false)
          }

        />

      )}

    </div>

  );

}