export const _Navbar = (props) => {
  return (
    <>
      <nav className="navbar navbar-expand-lg bg-body-tertiary">
        <div className="container-fluid">
          <a className="navbar-brand" href="#">
            JavaTech
          </a>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
              <li className="nav-item">
                <a className="nav-link active" aria-current="page" href="#">
                  Home
                </a>
              </li>
            </ul>
            <div className="d-flex">
              <span>
                Hello <b>{props.username}</b>&nbsp;
                <button
                  className="border-0 bg-transparent"
                  onClick={() => props.handleLogout()}
                >
                  Logout
                </button>
              </span>
            </div>
          </div>
        </div>
      </nav>
    </>
  );
};
