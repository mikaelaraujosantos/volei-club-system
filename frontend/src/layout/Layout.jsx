import { Link, useNavigate } from "react-router-dom"

function Layout({ children }) {

  const navigate = useNavigate()

  function logout() {

    localStorage.removeItem("token")
    localStorage.removeItem("role")
    localStorage.removeItem("atletaId")

    navigate("/")

  }

  return (

    <div className="layout">

      {/* SIDEBAR */}

      <aside className="sidebar">

        <h2 className="logo-text">
          SVC
        </h2>

        <nav className="menu">

          <Link to="/home">
            Home
          </Link>

          <Link to="/admin">
            Admin
          </Link>

        </nav>

      </aside>

      {/* ÁREA PRINCIPAL */}

      <div className="main">

        {/* HEADER */}

        <header className="header">

          <button
            className="button-secundario"
            onClick={logout}
          >
            Sair
          </button>

        </header>

        {/* CONTEÚDO */}

        <main className="content">

          {children}

        </main>

      </div>

    </div>

  )

}

export default Layout