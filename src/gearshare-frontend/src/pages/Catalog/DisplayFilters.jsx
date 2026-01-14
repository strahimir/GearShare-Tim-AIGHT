import { useState } from "react";

function DisplayFilters({ onFilterChange, closeFilters }) {
  const [availabilityPeriodStart, setAvailabilityPeriodStart] = useState("");
  const [availabilityPeriodEnd, setAvailabilityPeriodEnd] = useState("");
  const [minRentalDays, setMinRentalDays] = useState("");
  const [maxRentalDays, setMaxRentalDays] = useState("");
  const [minPricePerDay, setMinPricePerDay] = useState("");
  const [maxPricePerDay, setMaxPricePerDay] = useState("");
  const [seasons, setSeasons] = useState("");
  const [equipmentTypes, setEquipmentTypes] = useState("");
  const [equipmentConditions, setEquipmentConditions] = useState("");

  const handleSeasonChange = (event) => {
    setSeasons(event.target.value);
  };

  const handleEquipmentTypeChange = (event) => {
    setEquipmentTypes(event.target.value);
  };

  const handleEquipmentConditionChange = (event) => {
    setEquipmentConditions(event.target.value);
  };

  const handleSubmit = () => {
    onFilterChange({
      availabilityPeriodStart,
      availabilityPeriodEnd,
      minRentalDays,
      maxRentalDays,
      minPricePerDay,
      maxPricePerDay,
      seasons,
      equipmentTypes: equipmentTypes.split(",").map((type) => type.trim()),
      equipmentConditions,
    });
    closeFilters();  // Zatvori filtere nakon primjene
  };

  return (
    <div className="display-filters-container">
      <label>
        Dostupnost od:
        <input
          type="date"
          value={availabilityPeriodStart}
          onChange={(e) => setAvailabilityPeriodStart(e.target.value)}
        />
      </label>

      <label>
        Dostupnost do:
        <input
          type="date"
          value={availabilityPeriodEnd}
          onChange={(e) => setAvailabilityPeriodEnd(e.target.value)}
        />
      </label>

      <label>
        Minimalni broj dana najma:
        <input
          type="number"
          value={minRentalDays}
          onChange={(e) => setMinRentalDays(e.target.value)}
        />
      </label>

      <label>
        Maksimalni broj dana najma:
        <input
          type="number"
          value={maxRentalDays}
          onChange={(e) => setMaxRentalDays(e.target.value)}
        />
      </label>

      <label>
        Minimalna cijena po danu:
        <input
          type="number"
          value={minPricePerDay}
          onChange={(e) => setMinPricePerDay(e.target.value)}
        />
      </label>

      <label>
        Maksimalna cijena po danu:
        <input
          type="number"
          value={maxPricePerDay}
          onChange={(e) => setMaxPricePerDay(e.target.value)}
        />
      </label>

      <label>
        Sezona:
        <select onChange={handleSeasonChange} value={seasons}>
          <option value="summer">Ljeto</option>
          <option value="autumn">Jesen</option>
          <option value="winter">Zima</option>
          <option value="spring">Proljeće</option>
        </select>
      </label>

      <label>
        Tipovi opreme:
        <input
          type="text"
          value={equipmentTypes}
          onChange={handleEquipmentTypeChange}
          placeholder="Unesite tipove opreme (npr. bicikl, skije)"
        />
      </label>

      <label>
        Uvjeti opreme:
        <select onChange={handleEquipmentConditionChange} value={equipmentConditions}>
          <option value="new">Nova</option>
          <option value="used">Rabljena</option>
        </select>
      </label>

      <button onClick={handleSubmit}>Primijeni filtre</button>
        
      
    </div>
  );
}

export default DisplayFilters;
