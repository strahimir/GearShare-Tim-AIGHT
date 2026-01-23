import Listing from '../../Components/Listing';

function Listings({ products, listingsContainerStyle }) {
  if (!products || products.length === 0) {
    return <div>No products found</div>; // Ako nema proizvoda
  }

  const displayListings = products.map((product) => (
    <Listing key={product.id} product={product} /> // Prosljeđujemo svaki proizvod u Listing komponentu
  ));

  return <div className={listingsContainerStyle}>{displayListings}</div>;
}

export default Listings;
