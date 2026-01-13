import api from './axios'

// ----- GET -----
export async function getAdmins() {
    try {
        const response = await api.get('/admins')
        return response.data
    } catch (error) {
        console.error('Failed to fetch admins:', error)
        return []
    }
}

export async function getAdminByUUID(adminUUID) {
    try {
        const response = await api.get(`/admins/${adminUUID}`)
        return response.data
    } catch (error) {
        console.error(`Failed to fetch admin with UUID ${adminUUID}:`, error)
        return null
    }
}

export async function getAdminByUsername(username) {
    try {
        const response = await api.get(`/admins/profiles/${username}`)
        return response.data
    } catch (error) {
        console.error(`Failed to fetch admin with username ${username}:`, error)
        return null
    }
}

// ----- CREATE -----
export async function createAdmin(adminDto) {
    try {
        const response = await api.post('/admins', adminDto)
        return response.data
    } catch (error) {
        console.error('Failed to create admin:', error)
        return null
    }
}

// ----- UPDATE -----
export async function fullUpdateAdmin(adminUUID, adminDto) {
    try {
        const response = await api.put(`/admins/${adminUUID}`, adminDto)
        return response.data
    } catch (error) {
        console.error(`Failed to update admin ${adminUUID}:`, error)
        return null
    }
}

export async function partialUpdateAdmin(adminUUID, adminDto) {
    try {
        const response = await api.patch(`/admins/${adminUUID}`, adminDto)
        return response.data
    } catch (error) {
        console.error(`Failed to partially update admin ${adminUUID}:`, error)
        return null
    }
}

// ----- DELETE -----
export async function deleteAdmin(adminUUID) {
    try {
        await api.delete(`/admins/${adminUUID}`)
        return true
    } catch (error) {
        console.error(`Failed to delete admin ${adminUUID}:`, error)
        return false
    }
}
