import { useState } from 'react'
import placeholderImg from '../../assets/images/placeholder_img.png'
import { partialUpdateClient } from '../../api/clientService'
import { useAuth } from '../../hooks/useAuth'
import '../../assets/styles/profile.css'
import { useNavigate } from 'react-router'
import axios from "axios"



function UserInfo() {

    const navigate = useNavigate();

    const handleAddAd = () => {
        navigate("/profile/create-ad");
    };
    const { user, setUser } = useAuth()


    const API_BASE =
  import.meta.env.VITE_API_BASE_URL;

    const becomeClient = async () => {
    try {
        const res = await axios.patch(
        `${API_BASE}/clients/me/role/client`,
        null,
        { withCredentials: true }
        );
        setUser(res.data); // backend returns updated ClientDto
    } catch (err) {
        console.error("Failed to become CLIENT:", err);
        alert("Ne mogu promijeniti ulogu na CLIENT. Jesi li prijavljen?");
    }
    };

    const becomeSeller = async () => {
    try {
        const res = await axios.patch(
        `${API_BASE}/clients/me/role/seller`,
        null,
        { withCredentials: true }
        );
        setUser(res.data);
    } catch (err) {
        console.error("Failed to become SELLER:", err);
        alert("Ne mogu promijeniti ulogu na SELLER. Jesi li prijavljen?");
    }
    };

    const becomeAdmin = async () => {
    try {
        const res = await axios.patch(
        `${API_BASE}/clients/me/role/admin`,
        null,
        { withCredentials: true }
        );
        setUser(res.data);
    } catch (err) {
        console.error("Failed to become ADMIN:", err);
        alert("Ne mogu promijeniti ulogu na ADMIN. Jesi li prijavljen?");
    }
    };

    return (
        <div className="user-profile-container">
            <div className="profile-picture-container">
                <img src={placeholderImg} alt="" className="profile-picture" />
            </div>
            <div className="user-information-container">
                <div className="edit-profile-button-container">
                    {/* <button className="edit-profile-button" onClick={() => setIsEditing(true)}>
                        Uredi
                    </button> */}
                </div>
                {user &&
                    <>
                        <h2 className="username">@{user.username}</h2>
                        <h3 className="name">{user.firstName + (user.lastName ? ` ${user.lastName}` : '')}</h3>
                        <div className="contacts">
                            <h2>Kontakt:</h2>
                            <h4 className="email">E-mail: {user.email}</h4>
                            {user.phoneNumber && <h4 className="tel">Br. telefona: {user.phoneNumber}</h4>}
                        </div>
                    </>}

                <button className="add-ad-button" onClick={handleAddAd}>
                    Dodaj novi oglas
                </button>

                <button className="add-ad-button" onClick={becomeClient}>
                    Postani Klijent
                </button>

                <button className="add-ad-button" onClick={becomeSeller}>
                    Postani Prodavač
                </button>

                <button className="add-ad-button" onClick={becomeAdmin}>
                    Postani Administrator
                </button>

            </div>
        </div>
    )
}

export default UserInfo
