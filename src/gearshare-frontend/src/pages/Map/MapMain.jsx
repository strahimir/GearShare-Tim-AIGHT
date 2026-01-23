import React, { useState } from "react"
import RadarMap from "../../Components/RadarMap"
import SearchByCountryThenCity from "./SearchComponents/SearchByCountryThenCity"
import SearchByUserLocation from "./SearchComponents/SearchByUserLocation"

function MapMain() {
    const [listings, setListings] = useState([])
    const [loading, setLoading] = useState(false)
    const [searchOption, setSearchOption] = useState(true)

    const toggleSearchOption = () => {
        setListings([])
        setSearchOption(prev => !prev)
    }

    const renderSearchComponent = () => {
        return searchOption
            ? (
                 
                <SearchByCountryThenCity
                    setListings={setListings}
                    setLoading={setLoading}
                />
            )
            : (
                <SearchByUserLocation
                    setListings={setListings}
                    setLoading={setLoading}
                />
            )
    }

    return (
        <div>
            <h1>Pretraži oglase po lokaciji!</h1>

            <button onClick={toggleSearchOption}>
                {searchOption
                    ? "Pretraži oglase blizu Vaše lokacije"
                    : "Pretraži oglase po gradovima"}
            </button>

            {renderSearchComponent()}

            <RadarMap listings={listings} loading={loading} />
        </div>
    )
}

export default MapMain
