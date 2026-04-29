import { useState } from "react";
import ListaAtletas from "../components/ListaAtletas";
import ListaMensalidades from "../components/ListaMensalidades";
import CriarMensalidade from "../components/CriarMensalidade";

export default function Admin() {
  const [mostrarModalMensalidade, setMostrarModalMensalidade] = useState(false);
  const [abaAtiva, setAbaAtiva] = useState("atletas");

  const [nome, setNome] = useState("");
  const [idade, setIdade] = useState("");
  const [telefone, setTelefone] = useState("");
  const [posicao, setPosicao] = useState("");
  const [altura, setAltura] = useState("");
  const [responsavel, setResponsavel] = useState("");
  
  // Novos campos para o USUÁRIO
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");

  async function cadastrarAtleta() {
  if (nome === "" || idade === "" || telefone === "" || email === "" || senha === "") {
    alert("Preencha todos os campos obrigatórios (Nome, Idade, Telefone, Email, Senha)");
    return;
  }

  try {
    const token = localStorage.getItem("token");

    const resposta = await fetch("http://localhost:8080/atletas/cadastro-completo", {  // ← URL CORRETA
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
      body: JSON.stringify({
        nome: nome,
        idade: parseInt(idade),
        telefone: telefone,
        posicao: posicao,
        altura: altura ? parseFloat(altura) : null,
        responsavel: responsavel,
        email: email,
        senha: senha
      })
    });

    if (resposta.ok) {
      alert(`Atleta cadastrado com sucesso!\n\nEmail de acesso: ${email}\nSenha: ${senha}`);
      limparCampos();
      window.location.reload();
    } else {
      const erro = await resposta.text();
      alert("Erro ao cadastrar atleta: " + erro);
    }

  } catch (erro) {
    console.error(erro);
    alert("Erro ao cadastrar");
  }
}
  function limparCampos() {
    setNome("");
    setIdade("");
    setTelefone("");
    setPosicao("");
    setAltura("");
    setResponsavel("");
    setEmail("");
    setSenha("");
  }

  return (
    <div className="admin-container">
      <h1>Painel do Administrador</h1>

      <div className="abas">
        <button 
          className={abaAtiva === 'atletas' ? 'aba-ativa' : ''}
          onClick={() => setAbaAtiva('atletas')}
        >
          Gerenciar Atletas
        </button>
        <button 
          className={abaAtiva === 'mensalidades' ? 'aba-ativa' : ''}
          onClick={() => setAbaAtiva('mensalidades')}
        >
          Gerenciar Mensalidades
        </button>
      </div>

      {abaAtiva === 'atletas' && (
        <>
          <div className="card">
            <h2>Cadastrar Atleta</h2>
            <div className="form-grid">
              <input
                placeholder="Nome*"
                value={nome}
                onChange={(e) => setNome(e.target.value)}
              />
              <input
                placeholder="Idade*"
                type="number"
                value={idade}
                onChange={(e) => setIdade(e.target.value)}
              />
              <input
                placeholder="Telefone*"
                value={telefone}
                onChange={(e) => setTelefone(e.target.value)}
              />
              <input
                placeholder="Posição"
                value={posicao}
                onChange={(e) => setPosicao(e.target.value)}
              />
              <input
                placeholder="Altura (ex: 1.80)"
                step="0.01"
                type="number"
                value={altura}
                onChange={(e) => setAltura(e.target.value)}
              />
              <input
                placeholder="Responsável"
                value={responsavel}
                onChange={(e) => setResponsavel(e.target.value)}
              />
              <input
                placeholder="Email do Atleta*"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
              />
              <input
                placeholder="Senha do Atleta*"
                type="password"
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
              />
            </div>
            <button className="btn-primary" onClick={cadastrarAtleta}>
              Cadastrar Atleta
            </button>
          </div>

          <div className="card">
            <ListaAtletas />
          </div>
        </>
      )}

      {abaAtiva === 'mensalidades' && (
        <>
          <div className="card">
            <div className="header-mensalidades">
              <h2>Mensalidades</h2>
              <button 
                className="btn-primary"
                onClick={() => setMostrarModalMensalidade(true)}
              >
                + Nova Mensalidade
              </button>
            </div>
            <ListaMensalidades />
          </div>
        </>
      )}

      {mostrarModalMensalidade && (
        <CriarMensalidade 
          onSuccess={() => {
            setMostrarModalMensalidade(false);
            window.location.reload();
          }}
          onClose={() => setMostrarModalMensalidade(false)}
        />
      )}
    </div>
  );
}