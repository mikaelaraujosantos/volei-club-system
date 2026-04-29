import { useState, useEffect } from 'react';
import ListaMensalidades from '../components/ListaMensalidades';

function Home() {
  const [atletaId, setAtletaId] = useState(null);
  const [dadosAtleta, setDadosAtleta] = useState(null);
  const [carregando, setCarregando] = useState(true); // <-- ESTAVA FALTANDO ESTA LINHA

  useEffect(() => {
    // Função para carregar dados do atleta
    async function carregarDadosAtleta() {
      try {
        const id = localStorage.getItem('atletaId');
        
        if (!id) {
          console.log('Atleta não está logado');
          setCarregando(false);
          return;
        }
        
        const token = localStorage.getItem('token');
        
        // Buscar dados do atleta
        const response = await fetch(`http://localhost:8080/atletas/${id}`, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        
        if (response.ok) {
          const atleta = await response.json();
          setDadosAtleta(atleta);
          setAtletaId(parseInt(id));
        }
      } catch (erro) {
        console.error('Erro ao carregar dados do atleta:', erro);
      } finally {
        setCarregando(false);
      }
    }
    
    carregarDadosAtleta();
  }, []);

  if (carregando) {
    return (
      <div className="loading-container">
        <p>Carregando seus dados...</p>
      </div>
    );
  }

  if (!atletaId || !dadosAtleta) {
    return (
      <main className="home-container">
        <h1>Área do Atleta</h1>
        <div className="card">
          <p>Você não está logado como atleta.</p>
          <button onClick={() => window.location.href = '/'}>
            Fazer Login
          </button>
        </div>
      </main>
    );
  }

  return (
    <main className="home-container">
      <h1>Área do Atleta</h1>
      
      <div className="welcome-section">
        <p>Bem-vindo, {dadosAtleta.nome}!</p>
        <p>Este é o sistema do Sobradinho Vôlei Clube.</p>
      </div>

      <div className="card">
        <h2>Minhas Informações</h2>
        <div className="info-atleta">
          <p><strong>Posição:</strong> {dadosAtleta.posicao || 'Não informada'}</p>
          <p><strong>Telefone:</strong> {dadosAtleta.telefone}</p>
          <p><strong>Responsável:</strong> {dadosAtleta.responsavel || 'Não informado'}</p>
        </div>
      </div>

      <div className="card">
        <h2>Minhas Mensalidades</h2>
        <ListaMensalidades atletaId={atletaId} />
      </div>
    </main>
  );
}

export default Home;