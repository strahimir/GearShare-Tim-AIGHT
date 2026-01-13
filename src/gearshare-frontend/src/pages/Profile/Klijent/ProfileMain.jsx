import UserInfo from "./UserInfo"
import MyReservations from "./MyReservations"

function ProfileMain(){

    return(
        <div className="profile-main-container">
            <UserInfo />
            <MyReservations />
        </div>
    )
}

export default ProfileMain