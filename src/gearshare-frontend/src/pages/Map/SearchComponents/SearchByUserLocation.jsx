import { useEffect, useState } from "react"
import InfoDialog from "./InfoDialog"
import { getAddressWithinRadiusByCoords, getAddressWithinRadiusByAddress } from "../../../api/listingService"
import "../../../assets/styles/searchByLocation.css"; 

function SearchByUserLocation({ setListings }) {

    const [showInfoDialog, setShowInfoDialog] = useState(true)
    const [radius, setRadius] = useState(null)

    const [streetName, setStreetName] = useState("")
    const [streetNumber, setStreetNumber] = useState("")
    const [postalCode, setPostalCode] = useState("")
    const [countryCode, setCountryCode] = useState("")

    const [coords, setCoords] = useState(null) // { lat, lng } !!!!!

    useEffect(() => { // componentDidMount
        setShowInfoDialog(true)
    }, [])

    useEffect(() => {
        if (!navigator.geolocation) return

        navigator.permissions
            .query({ name: "geolocation" })
            .then(result => {
                if (result.state === "granted" || result.state === "prompt") {
                    navigator.geolocation.getCurrentPosition(
                        position => {
                            setCoords({
                                lat: position.coords.latitude,
                                lng: position.coords.longitude,
                            })
                        },
                        error => {
                            console.warn("Geolocation denied:", error)
                            setCoords(null)
                        },
                        {
                            enableHighAccuracy: true,
                            timeout: 5000,
                            maximumAge: 0,
                        }
                    )
                }
            })
    }, [])

    useEffect(() => {
        if (!radius) {
            setListings([])
            return
        }

        const hasCoords = coords?.lat != null && coords?.lng != null
        const hasManualAddress =
            streetName &&
            streetNumber &&
            postalCode &&
            countryCode

        if (!hasCoords && !hasManualAddress) {
            setListings([])
            return
        }

        const timeout = setTimeout(async () => {
            let data = []

            try {
                if (hasCoords) {
                    data = await getAddressWithinRadiusByCoords(
                        coords.lat,
                        coords.lng,
                        radius
                    )
                } else {
                    data = await getAddressWithinRadiusByAddress(
                        {
                            streetName,
                            streetNumber,
                            postalCode,
                            countryCode,
                        },
                        radius
                    )
                }
            } catch (err) {
                console.error("Failed to fetch listings:", err)
                data = []
            }

            setListings(data ?? [])
        }, 400)

        return () => clearTimeout(timeout)

    }, [
        radius,
        coords,
        streetName,
        streetNumber,
        postalCode,
        countryCode,
        setListings,
    ])

    return (
        <div>
            <InfoDialog
                open={showInfoDialog}
                onClose={() => setShowInfoDialog(false)}
            />

            <form className="search-byuserlocation-form">
                <label>
                    Udaljenost od Vas:
                    <input
                        type="number"
                        min={1}
                        value={radius ?? ""}
                        onChange={e => setRadius(e.target.valueAsNumber)}
                        required
                    />
                </label>

                <fieldset>
                    <legend className="legendclass">Vaša lokacija:</legend>

                    <label>
                        Ulica:
                        <input
                            type="text"
                            value={streetName}
                            onChange={e => setStreetName(e.target.value)}
                            required
                        />
                    </label>

                    <label>
                        Kućni broj:
                        <input
                            type="text"
                            value={streetNumber}
                            onChange={e => setStreetNumber(e.target.value)}
                            required
                        />
                    </label>

                    <label>
                        Poštanski broj:
                        <input
                            type="text"
                            value={postalCode}
                            onChange={e => setPostalCode(e.target.value)}
                            required
                        />
                    </label>

                    <label>
                        Država:
                        <input
                            type="text"
                            value={countryCode}
                            onChange={e => setCountryCode(e.target.value)}
                            required
                        />
                    </label>
                </fieldset>
            </form>
        </div>
    )
}

export default SearchByUserLocation
