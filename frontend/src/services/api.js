const API_URL = 'http://localhost:8080/api';

export const api = {
  // Mensalidades
  async criarMensalidade(dados) {
    const token = localStorage.getItem('token');
    const response = await fetch(`${API_URL}/mensalidades`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(dados)
    });
    return response.json();
  },

  async criarMensalidadeEmMassa(dados) {
    const token = localStorage.getItem('token');
    const response = await fetch(`${API_URL}/mensalidades/bulk`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(dados)
    });
    return response.json();
  },

  async cancelarMensalidade(id) {
    const token = localStorage.getItem('token');
    const response = await fetch(`${API_URL}/mensalidades/${id}/cancelar`, {
      method: 'PATCH',
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
    return response.json();
  },

  async registrarPagamento(id, dataPagamento) {
    const token = localStorage.getItem('token');
    const response = await fetch(`${API_URL}/mensalidades/${id}/pagar?dataPagamento=${dataPagamento}`, {
      method: 'PUT',
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
    return response.json();
  },

  async listarMensalidadesPorAtleta(atletaId) {
    const token = localStorage.getItem('token');
    const response = await fetch(`${API_URL}/mensalidades/atleta/${atletaId}`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
    return response.json();
  },

  async listarTodasMensalidades() {
    const token = localStorage.getItem('token');
    const response = await fetch(`${API_URL}/mensalidades`, {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
    return response.json();
  },

  async listarAtletas() {
    const token = localStorage.getItem('token');
    const response = await fetch('http://localhost:8080/atletas', {
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
    return response.json();
  }
};

export default api;