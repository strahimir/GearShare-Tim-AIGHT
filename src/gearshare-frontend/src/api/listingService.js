import api from "./axios"

export async function createListing(listingDto) {
  try {
    const response = await api.post("/listings", listingDto)
    return response.data
  } catch (error) {
    console.error("Failed to create listing:", error)
    return null
  }
}

export async function getListingsBySeller(sellerUUID) {
  try {
    const response = await api.get(`/listings/seller/${sellerUUID}`)
    return response.data
  } catch (error) {
    console.error(`Failed to fetch listings for seller ${sellerUUID}:`, error)
    return []
  }
}

export async function getAllListings() {
  try {
    const page = await getListingsPageable({ pageNo: 1, listingCount: 1000 })
    return page?.content ?? []
  } catch (error) {
    console.error("Failed to fetch all listings:", error)
    return []
  }
}

export async function getListingsPageable(options = {}) {
  try {
    const params = {
      availabilityStart: options.availabilityStart,
      availabilityEnd: options.availabilityEnd,
      minRentalDays: options.minRentalDays,
      maxRentalDays: options.maxRentalDays,
      minPricePerDay: options.minPricePerDay,
      maxPricePerDay: options.maxPricePerDay,
      seasons: options.seasons,
      equipmentTypes: options.equipmentTypes,
      equipmentConditions: options.equipmentConditions,
      pageNo: options.pageNo ?? 1,
      listingCount: options.listingCount ?? 50,
      sortBy: options.sortBy ?? ["availabilityPeriodStart,DESC"],
    }

    Object.keys(params).forEach((k) => {
      if (params[k] === undefined || params[k] === null) delete params[k]
    })

    const response = await api.get("/listings/filtered", { params })
    return response.data
  } catch (error) {
    console.error("Failed to fetch pageable listings:", error)
    return null
  }
}

export async function getListingByUUID(listingUUID) {
  try {
    const response = await api.get(`/listings/${listingUUID}`)
    return response.data
  } catch (error) {
    console.error(`Failed to fetch listing ${listingUUID}:`, error)
    return null
  }
}

export async function fullUpdateListing(listingUUID, listingDto) {
  try {
    const response = await api.put(`/listings/${listingUUID}`, listingDto)
    return response.data
  } catch (error) {
    console.error(`Failed to fully update listing ${listingUUID}:`, error)
    return null
  }
}

export async function partialUpdateListing(listingUUID, listingDto) {
  try {
    const response = await api.patch(`/listings/${listingUUID}`, listingDto)
    return response.data
  } catch (error) {
    console.error(`Failed to partially update listing ${listingUUID}:`, error)
    return null
  }
}

export async function deleteListing(listingUUID) {
  try {
    await api.delete(`/listings/${listingUUID}`)
    return true
  } catch (error) {
    console.error(`Failed to delete listing ${listingUUID}:`, error)
    return false
  }
}
