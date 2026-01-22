import api from './axios'

export async function createImage(file, clientUUID, listingUUID = null) {
    try {
        const formData = new FormData()
        formData.append('file', file)
        formData.append('clientUUID', clientUUID)
        if (listingUUID) formData.append('listingUUID', listingUUID)

        const response = await api.post('/images', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })

        return response.data
    } catch (error) {
        console.error('Failed to upload image:', error)
        return null
    }
}

export async function getProfilePicture(clientUUID) {
    try {
        const response = await api.get(`/images/client/${clientUUID}`)
        return response.data
    } catch (error) {
        console.error(`Failed to fetch profile picture for client ${clientUUID}:`, error)
        return null
    }
}

export async function getListingImages(listingUUID) {
    try {
        const response = await api.get(`/images/listing/${listingUUID}`)
        return response.data
    } catch (error) {
        console.error(`Failed to fetch images for listing ${listingUUID}:`, error)
        return []
    }
}

export async function deleteImage(imageUUID) {
    try {
        await api.delete(`/images/${imageUUID}`)
        return true
    } catch (error) {
        console.error(`Failed to delete image ${imageUUID}:`, error)
        return false
    }
}
