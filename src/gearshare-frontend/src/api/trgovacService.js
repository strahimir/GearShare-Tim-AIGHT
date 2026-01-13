import api from './axios'

// ----- GET -----
export async function getMerchants() {
    try {
        const response = await api.get('/merchants')  
        return response.data
    } catch (error) {
        console.error('Failed to fetch merchants:', error)
        return []
    }
}

export async function getMerchantByUUID(merchantUUID) {
    try {
        const response = await api.get(`/merchants/${merchantUUID}`)
        return response.data
    } catch (error) {
        console.error(`Failed to fetch merchant with UUID ${merchantUUID}:`, error)
        return null
    }
}

export async function getMerchantByUsername(username) {
    try {
        const response = await api.get(`/merchants/profiles/${username}`)
        return response.data
    } catch (error) {
        console.error(`Failed to fetch merchant with username ${username}:`, error)
        return null
    }
}

// ----- CREATE -----
export async function createMerchant(merchantDto) {
    try {
        const response = await api.post('/merchants', merchantDto)
        return response.data
    } catch (error) {
        console.error('Failed to create merchant:', error)
        return null
    }
}

// ----- UPDATE -----
export async function fullUpdateMerchant(merchantUUID, merchantDto) {
    try {
        const response = await api.put(`/merchants/${merchantUUID}`, merchantDto)
        return response.data
    } catch (error) {
        console.error(`Failed to update merchant ${merchantUUID}:`, error)
        return null
    }
}

export async function partialUpdateMerchant(merchantUUID, merchantDto) {
    try {
        const response = await api.patch(`/merchants/${merchantUUID}`, merchantDto)
        return response.data
    } catch (error) {
        console.error(`Failed to partially update merchant ${merchantUUID}:`, error)
        return null
    }
}

// ----- DELETE -----
export async function deleteMerchant(merchantUUID) {
    try {
        await api.delete(`/merchants/${merchantUUID}`)
        return true
    } catch (error) {
        console.error(`Failed to delete merchant ${merchantUUID}:`, error)
        return false
    }
}
