import { useNavigate } from "react-router";
import "../../../assets/styles/profile.css";

function ProfilePageTrgovacMain() {
  const navigate = useNavigate();

  const handleAddAd = () => {
    navigate("/profile/create-listing");
  };
  const handleCatalog= () => {
    navigate("/catalog");
  };

  return (
    <div className="profile-container">
      <h1 className="profile-title">Trenutno aktivni oglasi</h1>

      <div className="ads-container">
        <div className="no-ads">Trenutno nema aktivnih oglasa.</div>
      </div>

      <button className="add-ad-button" onClick={handleAddAd}>
        Dodaj novi oglas
      </button>
      <button className="add-ad-button" onClick={handleCatalog}>
        Katalog
      </button>
    </div>
  );
}

export default ProfilePageTrgovacMain;
