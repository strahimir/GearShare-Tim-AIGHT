import { Link, useNavigate } from 'react-router';
import '../index.css';
import gearShareLogo from '../assets/images/gearshare-logo.png';
import { useState } from 'react';
import { useAuth } from '../hooks/useAuth';
import { Search } from 'lucide-react';

function Header() {

  const { user, loading, handleLogin, handleLogout } = useAuth()

  const [menuOpen, setMenuOpen] = useState(false);
  const [searchOpen, setSearchOpen] = useState(false);
  const [location, setLocation] = useState('');
  const [product, setProduct] = useState('');
  const navigate = useNavigate();

  const toggleMenu = () => setMenuOpen(!menuOpen);
  const toggleSearch = () => setSearchOpen(!searchOpen);

  const handleSearch = (e) => {
    e.preventDefault();
    if (!location && !product) return;
    navigate(`/catalog?location=${encodeURIComponent(location)}&product=${encodeURIComponent(product)}`);
    setLocation('');
    setProduct('');
    setSearchOpen(false);
  };

  return (
    <header>
      <div className="logo-image-container">
        <img src={gearShareLogo} alt="Logo" />
        <h2>GearShare</h2>
      </div>

      <div className="welcome-page-buttons-container">
        <div className="right-controls">
          {/* Search icon i form */}

          {user &&
            <div className="search-container">
              <div className="search-icon" onClick={toggleSearch}> <Search /> </div>
              {searchOpen && (
                <form className="header-search-form" onSubmit={handleSearch}>
                  <input
                    type="text"
                    placeholder="Lokacija"
                    value={location}
                    onChange={(e) => setLocation(e.target.value)}
                  />
                  <input
                    type="text"
                    placeholder="Proizvod"
                    value={product}
                    onChange={(e) => setProduct(e.target.value)}
                  />
                  <button type="submit">Pretraži</button>
                </form>
              )}
            </div>
          }

          <div className="login-container">
            {/* <Link to='/login'>Prijavi se</Link> */}
            {!user
              ? (
                <div className='profile-icon' onClick={handleLogin}>Prijavi se</div>)
              : (
                <>
                  <div className='profile-icon' onClick={handleLogout}>Odjavi se</div>
                  <div className="login-container">
                    <Link to='/profile' className="profile-icon">Profil</Link>
                  </div>
                </>)
            }
          </div>

          {/* Hamburger menu */}
          <div className={`hamburger ${menuOpen ? 'open' : ''}`} onClick={toggleMenu}>
            <span></span>
            <span></span>
            <span></span>
          </div>
        </div>
      </div>

      {
        menuOpen && (
          <nav className={`mobile-menu ${menuOpen ? 'open' : ''}`}>
            <Link to="/about">O nama</Link>
            <Link to="/about">Informacije</Link>
            <Link to="/catalog">Katalog</Link>
          </nav>
        )
      }
    </header >
  );
}

export default Header;
