import { useState, useEffect } from 'react';

export default function CriarMensalidade({ onSuccess, onClose }) {
  const [atletas, setAtletas] = useState([]);
  const [atletasSelecionados, setAtletasSelecionados] = useState([]);
  const [todosAtletas, setTodosAtletas] = useState(false);
  const [dataVencimento, setDataVencimento] = useState('');
  const [valor, setValor] = useState('');
  const [mesReferencia, setMesReferencia] = useState(new Date().getMonth() + 1);
  const [anoReferencia, setAnoReferencia] = useState(new Date().getFullYear());
  const [observacao, setObservacao] = useState('');
  const [carregando, setCarregando] = useState(false);
  const [erroCarregamento, setErroCarregamento] = useState(false);

  useEffect(() => {
    carregarAtletas();
  }, []);

  async function carregarAtletas() {
    try {
      setErroCarregamento(false);
      const token = localStorage.getItem('token');
      
      if (!token) {
        console.error('Token não encontrado');
        setErroCarregamento(true);
        return;
      }

      const response = await fetch('http://localhost:8080/atletas', {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const dados = await response.json();
      
      console.log('Dados recebidos da API:', dados);
      
      // Verificar se é uma resposta paginada (tem content)
      if (dados.content && Array.isArray(dados.content)) {
        setAtletas(dados.content);
      } 
      // Se for array direto
      else if (Array.isArray(dados)) {
        setAtletas(dados);
      } 
      else {
        console.error('Formato de dados não reconhecido:', dados);
        setAtletas([]);
      }
      
    } catch (erro) {
      console.error('Erro ao carregar atletas:', erro);
      setErroCarregamento(true);
      setAtletas([]);
    }
  }

  function toggleAtleta(atletaId) {
    if (atletasSelecionados.includes(atletaId)) {
      setAtletasSelecionados(atletasSelecionados.filter(id => id !== atletaId));
    } else {
      setAtletasSelecionados([...atletasSelecionados, atletaId]);
    }
  }

  async function handleSubmit(e) {
    e.preventDefault();
    
    if (!dataVencimento || !valor) {
      alert('Preencha data de vencimento e valor');
      return;
    }

    setCarregando(true);

    try {
      const token = localStorage.getItem('token');
      let atletasIds = [];
      
      if (todosAtletas || atletasSelecionados.length === 0) {
        atletasIds = [];
      } else {
        atletasIds = atletasSelecionados;
      }

      console.log('Enviando requisição:', {
        atletasIds,
        dataVencimento,
        valor: parseFloat(valor),
        mesReferencia,
        anoReferencia,
        observacao
      });

      const response = await fetch('http://localhost:8080/api/mensalidades/bulk', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
          atletasIds: atletasIds,
          dataVencimento: dataVencimento,
          valor: parseFloat(valor),
          mesReferencia: mesReferencia,
          anoReferencia: anoReferencia,
          observacao: observacao
        })
      });

      if (response.ok) {
        if (atletasIds.length === 0) {
          alert('Mensalidade criada para todos os atletas!');
        } else {
          alert(`Mensalidade criada para ${atletasIds.length} atleta(s)!`);
        }
        
        if (onSuccess) onSuccess();
        if (onClose) onClose();
      } else {
        const error = await response.json();
        alert('Erro ao criar mensalidade: ' + (error.message || 'Erro desconhecido'));
      }
      
    } catch (error) {
      console.error('Erro ao criar mensalidade:', error);
      alert('Erro ao criar mensalidade: ' + error.message);
    } finally {
      setCarregando(false);
    }
  }

  return (
    <div style={{
      position: 'fixed',
      top: 0,
      left: 0,
      right: 0,
      bottom: 0,
      backgroundColor: 'rgba(0,0,0,0.5)',
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      zIndex: 1000
    }}>
      <div style={{
        backgroundColor: 'white',
        padding: '30px',
        borderRadius: '8px',
        maxWidth: '600px',
        width: '90%',
        maxHeight: '80vh',
        overflowY: 'auto'
      }}>
        <h2>Criar Mensalidade</h2>
        
        <form onSubmit={handleSubmit}>
          <div style={{ marginBottom: '15px' }}>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Atletas:</label>
            <div style={{ marginBottom: '10px' }}>
              <label>
                <input
                  type="checkbox"
                  checked={todosAtletas}
                  onChange={(e) => {
                    setTodosAtletas(e.target.checked);
                    if (e.target.checked) {
                      setAtletasSelecionados([]);
                    }
                  }}
                />
                Todos os atletas
              </label>
            </div>
            
            {!todosAtletas && (
              <div style={{ maxHeight: '200px', overflowY: 'auto', border: '1px solid #ddd', padding: '10px', borderRadius: '4px' }}>
                {erroCarregamento ? (
                  <p style={{ color: 'red' }}>Erro ao carregar atletas. Tente novamente.</p>
                ) : atletas.length === 0 ? (
                  <p>Nenhum atleta cadastrado.</p>
                ) : (
                  atletas.map(atleta => (
                    <label key={atleta.id} style={{ display: 'block', marginBottom: '5px' }}>
                      <input
                        type="checkbox"
                        checked={atletasSelecionados.includes(atleta.id)}
                        onChange={() => toggleAtleta(atleta.id)}
                      />
                      {atleta.nome}
                    </label>
                  ))
                )}
              </div>
            )}
          </div>

          <div style={{ marginBottom: '15px' }}>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Data Vencimento:</label>
            <input
              type="date"
              value={dataVencimento}
              onChange={(e) => setDataVencimento(e.target.value)}
              required
              style={{ width: '100%', padding: '8px', border: '1px solid #ddd', borderRadius: '4px' }}
            />
          </div>

          <div style={{ marginBottom: '15px' }}>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Valor (R$):</label>
            <input
              type="number"
              step="0.01"
              value={valor}
              onChange={(e) => setValor(e.target.value)}
              required
              style={{ width: '100%', padding: '8px', border: '1px solid #ddd', borderRadius: '4px' }}
            />
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px', marginBottom: '15px' }}>
            <div>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Mês Referência:</label>
              <select 
                value={mesReferencia} 
                onChange={(e) => setMesReferencia(parseInt(e.target.value))}
                style={{ width: '100%', padding: '8px', border: '1px solid #ddd', borderRadius: '4px' }}
              >
                <option value={1}>Janeiro</option>
                <option value={2}>Fevereiro</option>
                <option value={3}>Março</option>
                <option value={4}>Abril</option>
                <option value={5}>Maio</option>
                <option value={6}>Junho</option>
                <option value={7}>Julho</option>
                <option value={8}>Agosto</option>
                <option value={9}>Setembro</option>
                <option value={10}>Outubro</option>
                <option value={11}>Novembro</option>
                <option value={12}>Dezembro</option>
              </select>
            </div>

            <div>
              <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Ano Referência:</label>
              <input
                type="number"
                value={anoReferencia}
                onChange={(e) => setAnoReferencia(parseInt(e.target.value))}
                style={{ width: '100%', padding: '8px', border: '1px solid #ddd', borderRadius: '4px' }}
              />
            </div>
          </div>

          <div style={{ marginBottom: '15px' }}>
            <label style={{ display: 'block', marginBottom: '5px', fontWeight: 'bold' }}>Observação:</label>
            <textarea
              value={observacao}
              onChange={(e) => setObservacao(e.target.value)}
              rows="3"
              style={{ width: '100%', padding: '8px', border: '1px solid #ddd', borderRadius: '4px' }}
            />
          </div>

          <div style={{ display: 'flex', gap: '10px', justifyContent: 'flex-end' }}>
            <button 
              type="submit" 
              disabled={carregando}
              style={{
                padding: '10px 20px',
                backgroundColor: '#007bff',
                color: 'white',
                border: 'none',
                borderRadius: '4px',
                cursor: 'pointer'
              }}
            >
              {carregando ? 'Criando...' : 'Criar Mensalidade'}
            </button>
            <button 
              type="button" 
              onClick={onClose}
              style={{
                padding: '10px 20px',
                backgroundColor: '#6c757d',
                color: 'white',
                border: 'none',
                borderRadius: '4px',
                cursor: 'pointer'
              }}
            >
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}