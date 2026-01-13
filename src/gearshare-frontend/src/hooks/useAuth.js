import { useCallback, useEffect, useState } from "react";
import { getCurrentUser, loginWithGoogle, logout } from "../services/authService";


export function useAuth() {

    const [user, setUser] = useState(null)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const fetchUser = async () => {
            console.log("useAuth: fetching /api/me");
            const data = await getCurrentUser()
            console.log("useAuth: /api/me returned:", data);
            //tu radis promjenu
            if (data) {
        setUser(data);  // Ako postoji stvarni korisnik, postavi ga
        } else {
            // Ako nema stvarnog korisnika, postavi simuliranog klijenta
            setUser({
            username: "klijent",
            email: "klijent@example.com",
            role: "client",  // Simuliraj korisnika s ulogom 'klijent'
            });
        }
            // inace samo setUser(data)
            setLoading(false)
        }
        fetchUser();
    }, [])

    const handleLogin = useCallback(() => {
        loginWithGoogle()
    }, [])

    const handleLogout = useCallback(async () => {
        await logout()
        setUser(null)
        // window.location.href = '/welcome'
    }, [])

    return { user, setUser, loading, setLoading, handleLogin, handleLogout }
}