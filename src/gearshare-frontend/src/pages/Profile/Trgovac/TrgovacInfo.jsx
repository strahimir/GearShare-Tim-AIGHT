import { useState } from 'react'
import placeholderImg from '../../../assets/images/placeholder_img.png'
import { partialUpdateMerchant } from '../../../api/trgovacService'
import { useAuth } from '../../../hooks/useAuth'
import '../../../assets/styles/profile.css'
import { useNavigate } from 'react-router'


function TrgovacInfo() {

      const navigate = useNavigate();

  
    const { user, setUser } = useAuth()

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
                
            </div>
        </div>
    )
}

export default TrgovacInfo
