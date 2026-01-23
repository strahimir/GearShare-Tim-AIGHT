import Header from "../../Components/Header"
import Footer from "../../Components/Footer"
import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"
import { getListingByUUID } from "../../api/listingService"
import ListingMain from "./ListingMain"

function ListingPage() {
    const { listingUUID } = useParams()
    const [listing, setListing] = useState(null)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const fetchListing = async () => {
            const data = await getListingByUUID(listingUUID)
            setListing(data)
            setLoading(false)
        }
        fetchListing()
    }, [listingUUID])

    if (loading) return <p>Loading...</p>
    if (!listing) return <p>Listing not found</p>


    return (
        <>
            <Header></Header>
            <ListingMain listing={listing} />
            <Footer></Footer>
        </>
    )
}

export default ListingPage

