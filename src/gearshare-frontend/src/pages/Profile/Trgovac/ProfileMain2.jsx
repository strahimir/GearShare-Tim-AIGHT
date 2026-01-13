import UserInfo from "./TrgovacInfo"
import UserListings from "./TrgovacListings"

function ProfileMain2(){

    return(
        <div className="profile-main-container">
            <TrgovacInfo />
            <TrgovacListings />
        </div>
    )
}

export default ProfileMain2