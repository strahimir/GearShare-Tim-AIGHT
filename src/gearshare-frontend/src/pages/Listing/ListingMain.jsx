import React from "react"
import "./listing.css"

function ListingMain({ listing }) {
  return (
    <div className="listing-container">
      <h1 className="listing-title">{listing.title}</h1>
      <p className="listing-description">{listing.description}</p>

      <div className="listing-details">
        <p>Cijena: {listing.pricePerMinimumPeriod} €</p>
        <p>Najmanji broj dana: {listing.minimumRentalDays}</p>
        <p>Sezona: {listing.season}</p>
        <p>Tip opreme: {listing.equipmentType}</p>
        <p>Stanje opreme: {listing.equipmentCondition}</p>
        <p>Period dostupnosti: {listing.availabilityPeriodStart} → {listing.availabilityPeriodEnd}</p>
      </div>

      {listing.address && (
        <div className="listing-address">
          <h3>Lokacija:</h3>
          <p>{listing.address.streetName} {listing.address.streetNumber} {listing.address.aptNumber || ''}</p>
          <p>{listing.address.listingPostalCode}, {listing.address.listingCountryCode}</p>
        </div>
      )}

      {listing.images && listing.images.length > 0 && (
        <div className="listing-images">
          <h3>Slike:</h3>
          <div className="image-grid">
            {listing.images.map((img, i) => (
              <img
                key={i}
                src={`data:image/jpeg;base64,${img.content}`}
                alt={`listing-${i}`}
                className="listing-image"
              />
            ))}
          </div>
        </div>
      )}
    </div>
  )
}

export default ListingMain
