import '../index.css'

function Listing({ listing, handleDelete }) {
    return (
        <div className="listing-container">
            <div className="listing-image-container">
                <img className="listing-image" src={listing.img || './src/assets/images/placeholder_img.png'} />
            </div>

            <div className="listing-title">
                {listing.title}
            </div>
            <p>
                {listing.description}
            </p>
{/*         
            <div className="listing-seller-info">
                {listing.seller} ({(listing.rating / 10).toFixed(2)})
            </div> */}

            <div className="listing-period">
                {listing.availabilityPeriodStart} - {listing.availabilityPeriodEnd}
            </div>

            <div className="listing-price">
                {listing.pricePerMinimumPeriod}€ / {listing.minimumRentalDays} dan(a)
            </div>

            <div className="listing-price">
                {listing.equipmentType}; Stanje : {listing.equipmentCondition}
            </div>
            
            <button onClick={() => handleDelete(listing.listingUUID)}>Obriši oglas</button>
        </div>
    )
}


export default Listing