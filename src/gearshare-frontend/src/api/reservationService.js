import api from './axios'

// Dohvat svih rezervacija za klijenta
export async function getReservationsByClient(clientUUID) {
  try {
    const response = await api.get(`/reservations/client/${clientUUID}`)
    return response.data
  } catch (error) {
    console.error(
      `Failed to fetch reservations for client ${clientUUID}:`,
      error
    )
    return []
  }
}
