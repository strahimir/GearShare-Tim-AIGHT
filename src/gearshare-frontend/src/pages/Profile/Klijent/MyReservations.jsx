import { useEffect, useState } from "react"
import { useAuth } from "../../../hooks/useAuth"
import { getReservationsByClient } from "../../../api/reservationService"

function MyReservations() {
  const { user } = useAuth()
  const [reservations, setReservations] = useState([])

  useEffect(() => {
    if (!user?.clientUUID) return

    async function fetchReservations() {
      const data = await getReservationsByClient(user.clientUUID)
      setReservations(data)
    }

    fetchReservations()
  }, [user])

  if (!reservations.length) {
    return <p>Nema rezervacija.</p>
  }

  return (
    <div className="my-reservations-container">
      <h2>Moje rezervacije</h2>

      {reservations.map(res => (
        <div key={res.reservationUUID} className="reservation-card">
          <h3>{res.listingName}</h3>
          <p>Trgovac: {res.merchantName}</p>
          <p>
            Period: {res.startDate} – {res.endDate}
          </p>
          <p>Status: {res.status}</p>
          <p>Cijena: {res.totalPrice} €</p>

          {res.status === "FINISHED" && (
            <button>Ostavi ocjenu</button>
          )}
        </div>
      ))}
    </div>
  )
}

export default MyReservations
