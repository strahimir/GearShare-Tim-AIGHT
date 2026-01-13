import { Navigate } from "react-router"
import { useAuth } from "../../hooks/useAuth"

function ProfilePage() {
  const { user } = useAuth()

  if (!user) return <Navigate to="/login" />

  if (user.role === "CLIENT") {
    return <Navigate to="/profile/client" />
  }

  if (user.role === "MERCHANT") {
    return <Navigate to="/profile/trgovac" />
  }

  if (user.role === "ADMIN") {
    return <Navigate to="/profile/admin" />
  }

  return <Navigate to="/" />
}

export default ProfilePage
