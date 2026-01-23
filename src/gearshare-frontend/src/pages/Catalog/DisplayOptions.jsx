
function DisplayOptions({ changeListingsContainerStyle, onFilterChange }) {
  // Promjena broja proizvoda po stranici
  const handlePageSizeChange = (e) => {
    const selectedPageSize = e.target.value;
    onFilterChange({ pageSize: selectedPageSize });
  };

  // Promjena kriterija sortiranja
  const handleSortChange = (e) => {
    const sortBy = e.target.value;
    onFilterChange({ sortBy });
  };

  // Promjena redoslijeda sortiranja (uzlazno/silazno)
  const handleSortOrderChange = (e) => {
    const sortOrder = e.target.value;
    onFilterChange({ sortOrder });
  };

  return (
    <div className="display-options-container">
      <div className="toggle-view-container">
        {/* Gumb za mijenjanje prikaza između grid-a i liste */}
        
          Promijeni prikaz
        <button 
          onClick={() => changeListingsContainerStyle("listings-grid")} 
          className="view-toggle-btn"
        >
          Grid
        </button>
        <button 
          onClick={() => changeListingsContainerStyle("listings-list")} 
          className="view-toggle-btn"
        >
          Lista
        </button>
        
      </div>

      <div className="items-per-page-container">
        {/* Dropdown za broj proizvoda po stranici */}
        <label>
          Prikaži:
          <select onChange={handlePageSizeChange} defaultValue="20">
            <option value="20">20</option>
            <option value="50">50</option>
            <option value="100">100</option>
            <option value="200">200</option>
          </select>
        </label>
        
      </div>

      <div className="sort-by-container">
        {/* Dropdown za izbor kriterija sortiranja */}
        <label>
          Poredaj po:
          <select onChange={handleSortChange} defaultValue="datePosted">
            <option value="price">Cijeni</option>
            <option value="datePosted">Datumu objave</option>
            <option value="dateEnding">Datumu isteka</option>
            <option value="rating">Ocjenama</option>
          </select>
        </label>
        
        {/* Dropdown za izbor redoslijeda sortiranja (uzlazno/silazno) */}
        <select onChange={handleSortOrderChange} defaultValue="desc">
          <option value="asc">Uzlazno</option>
          <option value="desc">Silazno</option>
        </select>
      </div>
    </div>
  );
}
export default DisplayOptions;
