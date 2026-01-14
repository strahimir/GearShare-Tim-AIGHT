import Header from "../../Components/Header"
import CatalogMain from "./CatalogMain"
import Footer from "../../Components/Footer"
import "../../assets/styles/catalog.css";  // Putanja do CSS datoteke


function CatalogPage() {
    return (
        <>
            <Header />
            <CatalogMain />
            <Footer />
        </>
    )
}

export default CatalogPage