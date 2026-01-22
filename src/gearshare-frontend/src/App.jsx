import './index.css'
import { Routes, Route } from 'react-router'
import HomePage from './pages/Home/HomePage'
import WelcomePage from './pages/Welcome/WelcomePage'
import CatalogPage from './pages/Catalog/CatalogPage'
import ListingPage from './pages/Listing/ListingPage'
import CheckoutPage from './pages/Checkout/CheckoutPage'
import ProfilePage from './pages/Profile/ProfilePage'
import CreateListingPage from './pages/Profile/Trgovac/CreateListingPage'
import AboutPage from './pages/About/AboutPage'
import InboxPage from './pages/Inbox/InboxPage'
import MapPage from './pages/Map/MapPage'
import AdminDashboardPage from './pages/Profile/Admin/AdminDashboardPage'
import RootRedirect from './RootRedirect'
import { useAuth } from './hooks/useAuth'
import { Navigate } from 'react-router'

function App() {

  const { user, loading, handleLogin, handleLogout } = useAuth()

  const BACKEND_URL = import.meta.env.VITE_BACKEND_URL


  return (
    <>
      <Routes>
        <Route
          path='/'
          element={<RootRedirect />}
        /><Route
          path='home'
          element={<HomePage />}
        />
        <Route
          path='welcome'
          element={<WelcomePage />}
        />
        {/* <Route
          path='login'
          element={<LoginPage />}
        /> */}
        <Route
          path='catalog'
          element={<CatalogPage />}
        />
        {/* <Route
          path='listing'
          element={<ListingPage />}
        /> */}
        <Route
          path='checkout'
          element={<CheckoutPage />}
        />
        <Route
          path='profile'
          element={<ProfilePage />}
        />
        {/* <Route
          path='profile2'
          element={<ProfilePageClient />}
        /> */}
        <Route
          path='profile/create-listing'
          element={<CreateListingPage />}
        />
        <Route
          path='about'
          element={<AboutPage />}
        />
        <Route
          path='profile'
          element={<ProfilePage />}
        />
        <Route
          path='inbox'
          element={<InboxPage />}
        />
        <Route
          path='map-search'
          element={<MapPage />}
        />
        <Route
          path='admin-dashboard'
          element={<AdminDashboardPage />}
        />
      </Routes>
    </>
  )
}

export default App
