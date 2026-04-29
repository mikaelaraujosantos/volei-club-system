import { useState, useEffect } from 'react';

export default function ListaMensalidades({ atletaId = null }) {
  const [mensalidades, setMensalidades] = useState([]);
  const [carregando, setCarregando] = useState(true);
  const [filtro, setFiltro] = useState('TODAS');

  useEffect(() => {
    carregarMensalidades();
  }, [atletaId]);

  async function carregarMensalidades() {
    setCarregando(true);
    try {
      const token = localStorage.getItem('token');
      let url = 'http://localhost:8080/api/mensalidades';
      
      // Se tem atletaId, busca apenas as do atleta
      if (atletaId) {
        url = `http://localhost:8080/api/mensalidades/atleta/${atletaId}`;
      }
      
      const response = await fetch(url, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json'
        }
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const dados = await response.json();
      
      // Garantir que dados seja um array
      if (Array.isArray(dados)) {
        setMensalidades(dados);
      } else if (dados.content && Array.isArray(dados.content)) {
        setMensalidades(dados.content);
      } else {
        console.error('Formato de dados não reconhecido:', dados);
        setMensalidades([]);
      }
      
    } catch (erro) {
      console.error('Erro ao carregar mensalidades:', erro);
      setMensalidades([]);
    } finally {
      setCarregando(false);
    }
  }

  async function handleCancelar(id) {
    if (window.confirm('Tem certeza que deseja cancelar esta mensalidade?')) {
      try {
        const token = localStorage.getItem('token');
        const response = await fetch(`http://localhost:8080/api/mensalidades/${id}/cancelar`, {
          method: 'PATCH',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });
        
        if (response.ok) {
          alert('Mensalidade cancelada com sucesso!');
          carregarMensalidades();
        } else {
          const erro = await response.json();
          alert('Erro ao cancelar: ' + (erro.message || 'Erro desconhecido'));
        }
      } catch (error) {
        console.error('Erro ao cancelar:', error);
        alert('Erro ao cancelar mensalidade');
      }
    }
  }

  async function handlePagar(id) {
    if (window.confirm('Registrar pagamento desta mensalidade?')) {
      try {
        const token = localStorage.getItem('token');
        const hoje = new Date().toISOString().split('T')[0];
        
        const response = await fetch(`http://localhost:8080/api/mensalidades/${id}/pagar?dataPagamento=${hoje}`, {
          method: 'PUT',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });
        
        if (response.ok) {
          alert('Pagamento registrado com sucesso!');
          carregarMensalidades();
        } else {
          const erro = await response.json();
          alert('Erro ao registrar pagamento: ' + (erro.message || 'Erro desconhecido'));
        }
      } catch (error) {
        console.error('Erro ao registrar pagamento:', error);
        alert('Erro ao registrar pagamento');
      }
    }
  }

  function getStatusColor(status) {
    switch(status) {
      case 'PAGA': return '#28a745';
      case 'PENDENTE': return '#ffc107';
      case 'VENCIDA': return '#dc3545';
      case 'CANCELADA': return '#6c757d';
      default: return '#000';
    }
  }

  function getStatusText(status) {
    switch(status) {
      case 'PAGA': return 'Paga';
      case 'PENDENTE': return 'Pendente';
      case 'VENCIDA': return 'Vencida';
      case 'CANCELADA': return 'Cancelada';
      default: return status;
    }
  }

  const mensalidadesFiltradas = filtro === 'TODAS' 
    ? mensalidades 
    : mensalidades.filter(m => m.status === filtro);

  if (carregando) {
    return <div style={{ textAlign: 'center', padding: '20px' }}>Carregando mensalidades...</div>;
  }

  return (
    <div className="lista-mensalidades">
      <div style={{ marginBottom: '20px', display: 'flex', gap: '10px', flexWrap: 'wrap' }}>
        <button 
          onClick={() => setFiltro('TODAS')}
          style={{
            padding: '8px 16px',
            backgroundColor: filtro === 'TODAS' ? '#007bff' : '#e0e0e0',
            color: filtro === 'TODAS' ? 'white' : '#333',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer'
          }}
        >
          Todas
        </button>
        <button 
          onClick={() => setFiltro('PENDENTE')}
          style={{
            padding: '8px 16px',
            backgroundColor: filtro === 'PENDENTE' ? '#ffc107' : '#e0e0e0',
            color: filtro === 'PENDENTE' ? 'white' : '#333',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer'
          }}
        >
          Pendentes
        </button>
        <button 
          onClick={() => setFiltro('PAGA')}
          style={{
            padding: '8px 16px',
            backgroundColor: filtro === 'PAGA' ? '#28a745' : '#e0e0e0',
            color: filtro === 'PAGA' ? 'white' : '#333',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer'
          }}
        >
          Pagas
        </button>
        <button 
          onClick={() => setFiltro('VENCIDA')}
          style={{
            padding: '8px 16px',
            backgroundColor: filtro === 'VENCIDA' ? '#dc3545' : '#e0e0e0',
            color: filtro === 'VENCIDA' ? 'white' : '#333',
            border: 'none',
            borderRadius: '4px',
            cursor: 'pointer'
          }}
        >
          Vencidas
        </button>
      </div>

      {mensalidadesFiltradas.length === 0 ? (
        <div style={{ textAlign: 'center', padding: '40px', color: '#666' }}>
          Nenhuma mensalidade encontrada
        </div>
      ) : (
        <div style={{ overflowX: 'auto' }}>
          <table style={{
            width: '100%',
            borderCollapse: 'collapse',
            backgroundColor: 'white',
            borderRadius: '8px',
            overflow: 'hidden',
            boxShadow: '0 2px 4px rgba(0,0,0,0.1)'
          }}>
            <thead style={{ backgroundColor: '#f8f9fa' }}>
              <tr>
                {!atletaId && <th style={thStyle}>Atleta</th>}
                <th style={thStyle}>Vencimento</th>
                <th style={thStyle}>Valor</th>
                <th style={thStyle}>Status</th>
                <th style={thStyle}>Mês/Ano</th>
                <th style={thStyle}>Ações</th>
              </tr>
            </thead>
            <tbody>
              {mensalidadesFiltradas.map((mensalidade, index) => (
                <tr key={mensalidade.id} style={{
                  borderBottom: '1px solid #ddd',
                  backgroundColor: index % 2 === 0 ? 'white' : '#f9f9f9'
                }}>
                  {!atletaId && <td style={tdStyle}>{mensalidade.nomeAtleta}</td>}
                  <td style={tdStyle}>{new Date(mensalidade.dataVencimento).toLocaleDateString('pt-BR')}</td>
                  <td style={tdStyle}>R$ {mensalidade.valor.toFixed(2)}</td>
                  <td style={{...tdStyle, color: getStatusColor(mensalidade.status), fontWeight: 'bold'}}>
                    {getStatusText(mensalidade.status)}
                  </td>
                  <td style={tdStyle}>{mensalidade.mesReferencia}/{mensalidade.anoReferencia}</td>
                  <td style={tdStyle}>
                    {mensalidade.status === 'PENDENTE' && (
                      <>
                        <button
                          onClick={() => handlePagar(mensalidade.id)}
                          style={{
                            ...buttonStyle,
                            backgroundColor: '#28a745',
                            marginRight: '5px'
                          }}
                          onMouseEnter={(e) => e.target.style.backgroundColor = '#218838'}
                          onMouseLeave={(e) => e.target.style.backgroundColor = '#28a745'}
                        >
                          Pagar
                        </button>
                        <button
                          onClick={() => handleCancelar(mensalidade.id)}
                          style={{
                            ...buttonStyle,
                            backgroundColor: '#dc3545'
                          }}
                          onMouseEnter={(e) => e.target.style.backgroundColor = '#c82333'}
                          onMouseLeave={(e) => e.target.style.backgroundColor = '#dc3545'}
                        >
                          Cancelar
                        </button>
                      </>
                    )}
                    {mensalidade.status === 'VENCIDA' && (
                      <button
                        onClick={() => handlePagar(mensalidade.id)}
                        style={{
                          ...buttonStyle,
                          backgroundColor: '#28a745'
                        }}
                        onMouseEnter={(e) => e.target.style.backgroundColor = '#218838'}
                        onMouseLeave={(e) => e.target.style.backgroundColor = '#28a745'}
                      >
                        Pagar
                      </button>
                    )}
                    {mensalidade.status === 'PAGA' && (
                      <span style={{ color: '#28a745', fontWeight: 'bold' }}>✓ Pago</span>
                    )}
                    {mensalidade.status === 'CANCELADA' && (
                      <span style={{ color: '#6c757d', fontWeight: 'bold' }}>✗ Cancelada</span>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

const thStyle = {
  padding: '12px',
  textAlign: 'left',
  fontWeight: 'bold',
  borderBottom: '2px solid #ddd'
};

const tdStyle = {
  padding: '12px',
  textAlign: 'left'
};

const buttonStyle = {
  padding: '5px 10px',
  color: 'white',
  border: 'none',
  borderRadius: '4px',
  cursor: 'pointer',
  fontSize: '12px'
};