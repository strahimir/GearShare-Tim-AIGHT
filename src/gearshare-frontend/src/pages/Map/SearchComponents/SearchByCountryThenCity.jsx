import { useEffect, useState } from "react"
import countryData from "../../../assets/data/countryData"
import { getAddressByCityInCountry } from "../../../api/listingService"
import "../../../assets/styles/searchByCountry.css"; 

function SearchByCountryThenCity({ setListings }) {

    const [city, setCity] = useState("")
    const [country, setCountry] = useState("")

    const selectedCountry = countryData.find(c => c.ISO === country)


    useEffect(() => {

        if (!city || !country) return;

        async function fetchListingsByCity() {

            const data = await getAddressByCityInCountry(city, country)
            setListings(data)

        }
        fetchListingsByCity()

    }, [city, country, setListings])

    //     // getListings

    //     // send radar rq

    //     // get data

    //     // pass data to parent component

    //     // render radar map in parent component


    const renderCountryOptions = () => {

        return (
            <select
                name="countryOptions"
                id="countryOptions"
                value={country}
                onChange={e => {
                    setCountry(e.target.value)
                    setCity("")
                }}
            >

                <option value="" disabled>
                    Odaberi državu:
                </option>
                {
                    countryData.map(country =>
                        <option key={country.ISO} value={country.ISO}>{country.countryName}</option>)
                }
            </select>
        )
    }

    const renderCityOptions = () => {

        return (
            <select
                name="cityOptions"
                id="cityOptions"
                value={city}
                onChange={e => setCity(e.target.value)}
            >

                <option value="" disabled>
                    Odaberi grad:
                </option>
                {
                    selectedCountry.cities.map(city =>
                        <option key={city.postalCode} value={city.postalCode}>{city.cityName}</option>)
                }
            </select>
        )
    }

    return (
        <div>
            <form
            //onSubmit={loadListingsFromCityInCountry}
            className="search-bycountrythencity-form">
                {renderCountryOptions()}
                {country === "" ? null : renderCityOptions(selectedCountry)}

                {/* <button
                    type="submit"
                    disabled={country === "" || city === "" ? true : false}
                >Pretraži oglase!</button> */}
            </form>
        </div>
    )
}

export default SearchByCountryThenCity