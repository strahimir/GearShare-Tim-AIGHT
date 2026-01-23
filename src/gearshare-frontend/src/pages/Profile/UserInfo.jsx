import { useState, useEffect } from 'react'
import profilePicture from '../../assets/images/default_pfp.png'
import { partialUpdateClient } from '../../api/clientService'
import { createImage } from '../../api/imageService'
import { useAuth } from '../../hooks/useAuth'
import '../../assets/styles/profile.css'
import { useNavigate } from 'react-router'
import axios from "axios"



function UserInfo() {
    const navigate = useNavigate()
    const { user, setUser } = useAuth()

    const [isEditing, setIsEditing] = useState(false)
    const [formData, setFormData] = useState({
        firstName: user?.firstName || '',
        lastName: user?.lastName || '',
        username: user?.username || '',
        email: user?.email || '',
        phoneNumber: user?.phoneNumber || ''
    })
    const [selectedImage, setSelectedImage] = useState(null)
    const [isSaving, setIsSaving] = useState(false)

    useEffect(() => {
        return () => {
            if (selectedImage) URL.revokeObjectURL(selectedImage)
        }
    }, [selectedImage])

    const handleImageChange = (e) => {
        const file = e.target.files[0]
        if (file) setSelectedImage(file)
    }

    const handleUserInfo = (e) => {
        const { name, value } = e.target
        setFormData(prev => ({ ...prev, [name]: value }))
    }

    const handleUserInfoUpdate = async () => {
        if (!user) return
        setIsSaving(true)

        const clientDto = {
            firstName: formData.firstName,
            lastName: formData.lastName,
            email: formData.email,
            phoneNumber: formData.phoneNumber
        }

        try {
            const updated = await partialUpdateClient(user.clientUUID, clientDto)

            if (updated) {
                if (selectedImage) {
                    await createImage(selectedImage, user.clientUUID)
                }
                setUser(updated)
                setIsEditing(false)
                setSelectedImage(null)
            } else {
                console.error('Failed to update user info, please try again')
            }
        } catch (error) {
            console.error('Failed to update user info, please try again', error)
        } finally {
            setIsSaving(false)
        }
    }

    
    const handleMaps = () => navigate('/map-search')


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
    const handleAddAd = () => {
    navigate("/profile/create-listing");
  };

    return (
        <div className="user-profile-container">
            <div className="profile-picture-container">
                <img
                    src={selectedImage ? URL.createObjectURL(selectedImage) : profilePicture}
                    alt="Profile"
                    className="profile-picture"
                />
            </div>

            {user && (
                <>
                    {!isEditing ? (
                        <div className="user-information-container">
                            <h2 className="username">@{user.username}</h2>
                            <h3 className="name">
                                {user.firstName + (user.lastName ? ` ${user.lastName}` : '')}
                            </h3>
                            <div className="contacts">
                                <h2>Kontaktni podaci:</h2>
                                <h4 className="email">E-mail: {user.email}</h4>
                                {user.phoneNumber && <h4 className="tel">Br. telefona: {user.phoneNumber}</h4>}
                            </div>
                            <div className="edit-profile-button-container">
                                <button
                                    className="edit-profile-button"
                                    onClick={() => setIsEditing(true)}
                                >
                                    Uredi profil
                                </button>
                            </div>
                        </div>
                    ) : (
                        <div className="edit-form">
                            <fieldset>
                                <legend>Uredi svoje podatke:</legend>

                                <label>
                                    Promijeni sliku profila:
                                    <input type="file" accept="image/*" onChange={handleImageChange} />
                                </label>

                                <label>
                                    Ime:
                                    <input
                                        type="text"
                                        name="firstName"
                                        value={formData.firstName}
                                        onChange={handleUserInfo}
                                    />
                                </label>

                                <label>
                                    Prezime:
                                    <input
                                        type="text"
                                        name="lastName"
                                        value={formData.lastName}
                                        onChange={handleUserInfo}
                                    />
                                </label>

                                <label>
                                    Korisničko ime:
                                    <input
                                        type="text"
                                        name="username"
                                        value={formData.username}
                                        onChange={handleUserInfo}
                                        disabled
                                    />
                                </label>

                                <label>
                                    Broj telefona:
                                    <input
                                        type="tel"
                                        name="phoneNumber"
                                        value={formData.phoneNumber}
                                        onChange={handleUserInfo}
                                    />
                                </label>
                            </fieldset>

                            <div className="edit-profile-button-container">
                                <button
                                    className="save-profile-button"
                                    onClick={handleUserInfoUpdate}
                                    disabled={isSaving}
                                >
                                    {isSaving ? 'Spremanje...' : 'Spremi promjene'}
                                </button>
                                <button
                                    className="cancel-profile-button"
                                    onClick={() => {
                                        setIsEditing(false)
                                        setSelectedImage(null)
                                        setFormData({
                                            firstName: user.firstName,
                                            lastName: user.lastName || '',
                                            username: user.username,
                                            email: user.email,
                                            phoneNumber: user.phoneNumber || ''
                                        })
                                    }}
                                >
                                    Odustani
                                </button>
                            </div>
                        </div>
                    )}
                </>
            )}

                

                <button className="add-ad-button" onClick={becomeClient}>
                    Postani Klijent
                </button>

                <button className="add-ad-button" onClick={becomeSeller}>
                    Postani Prodavač
                </button>

                <button className="add-ad-button" onClick={becomeAdmin}>
                    Postani Administrator
                </button>
                <button className="add-ad-button" onClick={handleAddAd}>
                    Dodaj novi oglas
                </button>
                <button className="add-ad-button" onClick={handleMaps}>
                    Mape
                </button>
                

            </div>
    )
}

export default UserInfo
