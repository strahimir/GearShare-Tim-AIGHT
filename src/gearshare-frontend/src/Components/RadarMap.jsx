import { useEffect, useRef } from "react"
import Radar from "radar-sdk-js"
import { LngLatBounds } from "maplibre-gl"
import "radar-sdk-js/dist/radar.css"

function RadarMap({ listings }) {

    listings = Array.isArray(listings) ? listings : []

    const mapRef = useRef(null)
    const mapInstance = useRef(null)
    const markersRef = useRef([])

    useEffect(() => {
        Radar.initialize(import.meta.env.VITE_RADAR_API_KEY)

        mapInstance.current = Radar.ui.map({
            container: mapRef.current,
            style: "radar-default-v1",
            center: [-98.5795, 39.8283],
            zoom: 4,
        })

        return () => {

            markersRef.current.forEach(m => m.remove())
            mapInstance.current?.remove()
        }
    }, [])

    useEffect(() => {
        if (!mapInstance.current) return

        markersRef.current.forEach(m => m.remove())
        markersRef.current = []

        if (listings.length === 0) return

        const bounds = new LngLatBounds()
        let hasValidMarkers = false

        listings.forEach(listing => {
            const lng = Number(listing.coordinates?.longitude)
            const lat = Number(listing.coordinates?.latitude)


            if (!Number.isFinite(lng) || !Number.isFinite(lat)) {
                console.warn("Skipping listing with invalid coordinates:", listing)
                return
            }

            const marker = Radar.ui.marker({ text: listing.title })
                .setLngLat([lng, lat])
                .addTo(mapInstance.current)

            markersRef.current.push(marker)
            bounds.extend([lng, lat])
            hasValidMarkers = true
        })

        
        if (hasValidMarkers) {
            mapInstance.current.fitBounds(bounds, {
                padding: 60,
                maxZoom: 14,
            })
        }
    }, [listings])

    return <div ref={mapRef} style={{ height: 500, width: "100%" }} />
}

export default RadarMap
