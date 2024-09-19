import { Navbar, Container, Nav, NavDropdown, Button } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import useAuth from "../hooks/useAuth";

export const _Navbar = () => {
  const { user, logout } = useAuth(); 
  const navigate = useNavigate();
  
  const handleLogout = () => {
    logout();
    navigate("/login");
  };
  
  return (
    <Navbar expand="lg" className="bg-body-tertiary">
      <Container>
        <Navbar.Brand>
          <Link to="/" className="navbar-brand">
            JavaTech
          </Link>
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Link to="/" className="nav-link">
              Home
            </Link>
            <Link to="my-todo" className="nav-link">
              My Todo
            </Link>
          </Nav>
        </Navbar.Collapse>
        <Navbar.Collapse className="justify-content-end">
          <Navbar.Text>
            username: <b> {user ? user.sub : 'Guest'}</b> &nbsp;
          </Navbar.Text>
          <Nav.Link onClick={handleLogout}>Logout</Nav.Link>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};
