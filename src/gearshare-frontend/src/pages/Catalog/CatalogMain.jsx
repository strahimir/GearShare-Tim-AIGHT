import React, { useState, useEffect } from "react";
import DisplayOptions from "./DisplayOptions";
import DisplayFilters from "./DisplayFilters";
import Listings from "./Listings";
import "../../assets/styles/catalog.css"; // Putanja do CSS datoteke

function CatalogMain() {
  const [listingsContainerStyle, setListingsContainerStyle] = useState("listings-grid");
  const [pageSize, setPageSize] = useState(20);
  const [sortBy, setSortBy] = useState("datePosted");
  const [sortOrder, setSortOrder] = useState("desc");
  const [products, setProducts] = useState([]);
  const [filters, setFilters] = useState({});
  const [isFiltersOpen, setIsFiltersOpen] = useState(false); // Dodano stanje za filtere

  const changeListingsContainerStyle = () => {
    setListingsContainerStyle((prevStyle) =>
      prevStyle === "listings-grid" ? "listings-list" : "listings-grid"
    );
  };

  const handleFilterChange = (newFilters) => {
    setFilters(prevFilters => ({ ...prevFilters, ...newFilters }));
  };

  const closeFilters = () => {
    setIsFiltersOpen(false);  // Zatvori sidebar filtere
  };

  useEffect(() => {
    const fetchFilteredProducts = async () => {
      const response = await fetch("/api/products", {
        method: "POST", // Preporučljivo koristiti POST za velike filtere
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(filters),
      });
      const data = await response.json();
      setProducts(data.content);
    };

    fetchFilteredProducts();
  }, [filters]);

  // Funkcija za otvaranje/zatvaranje filtera
  const toggleFilters = () => {
    setIsFiltersOpen(prevState => !prevState);
  };

  return (
    <div className="catalog-main-container">
      <div className="filters-options-container">
        {/* Gumb za otvaranje/zatvaranje filtera */}
        {!isFiltersOpen && (
          <button className="toggle-filters-btn" onClick={toggleFilters}>
           Odaberi filtere
        </button>
        )}
        {/* Ako su filteri otvoreni, prikaži filtere */}
        {isFiltersOpen && (
          <div className="filters-sidebar open">
            <DisplayOptions changeListingsContainerStyle={changeListingsContainerStyle} onFilterChange={handleFilterChange} />
            <DisplayFilters onFilterChange={handleFilterChange} closeFilters={closeFilters} />
          </div>
        )}
      </div>

      {/* Prikazivanje oglasa */}
      <div className="listings-container">
        <Listings products={products} listingsContainerStyle={listingsContainerStyle} />
      </div>
    </div>
  );
}

export default CatalogMain;
