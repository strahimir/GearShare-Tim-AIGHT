import { useState } from "react"
import { createListing } from "../../../api/listingService"
import { createImage } from '../../../api/imageService'
import { useAuth } from '../../../hooks/useAuth'
import '../../../assets/styles/profile.css'

function CreateListingPage() {
  const { user } = useAuth()

  const [title, setTitle] = useState("")
  const [description, setDescription] = useState("")
  const [dayCount, setDayCount] = useState("")
  const [price, setPrice] = useState("")
  const [startDate, setStartDate] = useState("")
  const [endDate, setEndDate] = useState("")
  const [season, setSeason] = useState("")
  const [equipmentType, setEquipmentType] = useState("")
  const [equipmentCondition, setEquipmentCondition] = useState("")
  const [streetName, setStreetName] = useState("")
  const [streetNumber, setStreetNumber] = useState("")
  const [aptNumber, setAptNumber] = useState("")
  const [postalCode, setPostalCode] = useState("")
  const [countryCode, setCountryCode] = useState("")
  const [images, setImages] = useState([])

  const formatDateTime = (input) => {
    if (!input) return ""
    const date = new Date(input)
    const pad = (n) => String(n).padStart(2, "0")
    return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`
  }

  const handleAddImage = (e) => {
    const file = e.target.files[0]
    if (!file) return
    if (images.length >= 5) {
      alert("Nije moguće odabrati više od 5 slika.")
      return
    }
    setImages(prev => [...prev, file])
  }

  const handleRemoveImage = (index) => {
    setImages(prev => prev.filter((_, i) => i !== index))
  }

  const handleSubmit = async (e) => {
  e.preventDefault()
  const listingData = {
    title,
    description,
    availabilityPeriodStart: formatDateTime(startDate),
    availabilityPeriodEnd: formatDateTime(endDate),
    minimumRentalDays: dayCount,
    pricePerMinimumPeriod: parseFloat(price).toFixed(2),
    season,
    equipmentType,
    equipmentCondition
  }
  const addressData = {
    streetName,
    streetNumber,
    aptNumber,
    listingPostalCode: postalCode,
    listingCountryCode: countryCode
  }

  const result = await createListing(user.clientUUID, listingData, addressData)

  if (result) {
    const listingUUID = result.listingUUID
    for (const file of images) {
      await createImage(file, user.clientUUID, listingUUID)
    }
    window.location.href = "/profile"
  } else {
    console.error("Failed to create listing.")
  }
}




    return (
      <div className="profile-container">
        <h1 className="profile-title">Kreiraj novi oglas</h1>
        <form className="create-listing-form" onSubmit={handleSubmit}>

          <label>
            Naziv proizvoda:
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
            />
          </label>

          <label>
            Opis proizvoda:
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              required
            />
          </label>

          <label>
            Najmanji mogući broj dana za iznajmljivanje:
            <input
              type="number"
              min={1}
              max={30}
              value={dayCount}
              onChange={(e) => setDayCount(e.target.value)}
              required
            />
          </label>

          <label>
            Cijena:
            <input
              type="number"
              min={0.00}
              step={0.50}
              value={price}
              onChange={(e) => setPrice(e.target.value)}
              required
            />
          </label>

          <label>
            Oglas vrijedi od:
            <input
              type="date"
              value={startDate}
              onChange={(e) => setStartDate(e.target.value)}
              required
            />
          </label>

          <label>
            Oglas vrijedi do:
            <input
              type="date"
              value={endDate}
              onChange={(e) => setEndDate(e.target.value)}
              required
            />
          </label>

          <label>
            Sezona:
            <select value={season} onChange={(e) => setSeason(e.target.value)}>
              <option value="winter">Zima</option>
              <option value="summer">Ljeto</option>
            </select>
          </label>

          <label>
            Tip opreme:
            <input
              type="text"
              value={equipmentType}
              onChange={(e) => setEquipmentType(e.target.value)}
            />
          </label>

          <label>
            Stanje opreme:
            <input
              type="text"
              value={equipmentCondition}
              onChange={(e) => setEquipmentCondition(e.target.value)}
            />
          </label>

        <fieldset>
          <legend>Dodaj slike (maksimalno 5):</legend>
        </fieldset>
          <label>
            Odaberi datoteke:
            <input type="file" accept="image/*" onChange={handleAddImage} />
          </label>

          {images.length > 0 && (
            <div className="image-preview-container">
              {images.map((img, i) => (
                <div key={i} className="image-preview">
                  <img src={URL.createObjectURL(img)} alt={`preview-${i}`} />
                  <button type="button" onClick={() => handleRemoveImage(i)}>X</button>
                </div>
              ))}
            </div>
          )}

          <fieldset>
            <legend>Vaša lokacija:</legend>

            <label>
              Ulica:
              <input
                type="text"
                value={streetName}
                onChange={(e) => setStreetName(e.target.value)}
                required
              />
            </label>

            <label>
              Kućni broj:
              <input
                type="text"
                value={streetNumber}
                onChange={(e) => setStreetNumber(e.target.value)}
                required
              />
            </label>

            <label>
              Broj stana(neobavezno):
              <input
                type="text"
                value={aptNumber}
                onChange={(e) => setAptNumber(e.target.value)}
              />
            </label>

            <label>
              Poštanski broj:
              <input
                type="text"
                value={postalCode}
                onChange={(e) => setPostalCode(e.target.value)}
                required
              />
            </label>

            <label>
              Država:
              <input
                type="text"
                value={countryCode}
                onChange={(e) => setCountryCode(e.target.value)}
                required
              />
            </label>
          </fieldset>
          <button type="submit" className="submit-ad-button">
            Kreiraj oglas
          </button>
        </form>
      </div>
    )
  }


export default CreateListingPage