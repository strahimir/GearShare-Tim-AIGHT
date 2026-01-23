import { useState } from "react"
import { useNavigate } from "react-router"
import { createListing } from "../../../api/listingService"
import { getClientByUUID } from "../../../api/clientService"
import { useAuth } from '../../../hooks/useAuth'

function CreateAdPage() {

  const { user, loading, handleLogin, handleLogout } = useAuth()

  
  


  const navigate = useNavigate()
  const [image, setImage] = useState(null)
  const [title, setTitle] = useState("")
  const [description, setDescription] = useState("")
  const [dayCount, setDayCount] = useState("")
  const [price, setPrice] = useState("")
  const [startDate, setStartDate] = useState("")
  const [endDate, setEndDate] = useState("")
  const [season, setSeason] = useState("")
  const [equipmentType, setEquipmentType] = useState("")
  const [equipmentCondition, setEquipmentCondition] = useState("")

  const formatDateTime = (input) => {
    if (!input) return ""
    const date = new Date(input)
    const pad = (n) => String(n).padStart(2, "0")

    const yyyy = date.getFullYear()
    const MM = pad(date.getMonth() + 1)
    const dd = pad(date.getDate())
    const HH = pad(date.getHours())
    const mm = pad(date.getMinutes())
    const SS = pad(date.getSeconds())

    return `${yyyy}-${MM}-${dd}T12:00:00`
  }

  const handleSubmit = async (e) => {
    e.preventDefault()

    const listingData = {
      title: title,
      description: description,
      availabilityPeriodStart: formatDateTime(startDate),
      availabilityPeriodEnd: formatDateTime(endDate),
      minimumRentalDays: dayCount,
      pricePerMinimumPeriod: parseFloat(price).toFixed(2),
      season: season,
      equipmentType: equipmentType,
      equipmentCondition: equipmentCondition,
    }

    
    const result = await createListing(listingData)

    if (result) {
      console.log("Listing created successfully:", result)
      
      window.location.href = "/profile"
    } else {
      console.error("Failed to create listing.")
    }
  }

  return (
    <div className="profile-container">
      <h1 className="profile-title">Kreiraj novi oglas</h1>
      <form className="create-ad-form" onSubmit={handleSubmit}>
        <label>
          Slika proizvoda:
          <input
            type="file"
            accept="image/*"
            onChange={(e) => setImage(e.target.files[0])}
          />
        </label>

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

        <button type="submit" className="submit-ad-button">
          Kreiraj oglas
        </button>
      </form>
    </div>
  )
}

export default CreateAdPage
