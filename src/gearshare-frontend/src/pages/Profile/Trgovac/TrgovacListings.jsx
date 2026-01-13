import { useState, useEffect } from "react"
import { useAuth } from '../../../hooks/useAuth'
import { getListingsBySeller, deleteListing } from '../../../api/listingService'
import Listing from "../../../Components/Listing"
import '../../../index.css'

function TrgovacListings() {
    const { user } = useAuth()
    const [listings, setListings] = useState([])

    useEffect(() => {
        async function fetchListings() {
            if (user?.clientUUID) {
                const data = await getListingsBySeller(user.clientUUID)
                setListings(data)
            }
        }
        fetchListings()
    }, [user])

    async function handleDelete(listingUUID) {
        const success = await deleteListing(listingUUID)
        if (success) {
            setListings(prev => prev.filter(l => l.listingUUID !== listingUUID))
        }
    }

    return (
        <div className="user-listings-container">
            {listings.map((listing, index) => (
                <Listing
                    key={index}
                
                    listing={listing}
                    
                    handleDelete={handleDelete} 
                  />
            ))}
        </div>
    )
}

export default TrgovacListings
