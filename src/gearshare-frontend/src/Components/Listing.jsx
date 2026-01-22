import { useState } from 'react'
import ListingDialog from '../pages/Listing/ListingDialog'
import '../index.css'
import '../pages/Listing/listing.css'

function Listing({ listing, handleDelete }) {
    const [open, setOpen] = useState(false)

    function handleOpen() {
        setOpen(true)
    }

    function handleClose() {
        setOpen(false)
    }

    function handleDeleteClick(e) {
        e.stopPropagation()
        handleDelete(listing.listingUUID)
    }

    return (
        <>
            <div className="listing-container" onClick={handleOpen}>
                <div className="listing-image-container">
                    <img
                        className="listing-image"
                        src={listing.img || './src/assets/images/placeholder_img.png'}
                    />
                </div>

                <div className="listing-title">
                    {listing.title}
                </div>

                <p>
                    {listing.description}
                </p>

                <div className="listing-period">
                    {listing.availabilityPeriodStart} - {listing.availabilityPeriodEnd}
                </div>

                <div className="listing-price">
                    {listing.pricePerMinimumPeriod}€ / {listing.minimumRentalDays} dan(a)
                </div>

                <div className="listing-price">
                    {listing.equipmentType}; Stanje : {listing.equipmentCondition}
                </div>

                <button onClick={handleDeleteClick}>
                    Obriši oglas
                </button>
            </div>

            <ListingDialog
                open={open}
                listing={listing}
                onClose={handleClose}
            />
        </>
    )
}

export default Listing
