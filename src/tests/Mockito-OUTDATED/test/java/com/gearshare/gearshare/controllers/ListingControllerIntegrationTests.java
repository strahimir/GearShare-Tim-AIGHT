package com.gearshare.gearshare.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gearshare.gearshare.TestDataUtil;
import com.gearshare.gearshare.config.TestSecurityConfig;
import com.gearshare.gearshare.domain.dto.ListingDto;
import com.gearshare.gearshare.domain.entities.ClientEntity;
import com.gearshare.gearshare.domain.entities.ListingEntity;
import com.gearshare.gearshare.services.ClientService;
import com.gearshare.gearshare.services.ListingService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc( addFilters = false )
public class ListingControllerIntegrationTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private ClientService clientService;
    private ListingService listingService;

    @Autowired
    public ListingControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper,ClientService clientService, ListingService listingService) {
        this.mockMvc = mockMvc;
        this.listingService = listingService;
        this.clientService = clientService;
        this.objectMapper = objectMapper;
    }


    @Test
    public void testThatCreateListingSuccessfullyReturnsHttpStatus201Created() throws Exception {

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        clientService.createOrUpdateClient(client);
        ListingDto listingDto = TestDataUtil.createTestListingDtoA(null);
        String listingJson = objectMapper.writeValueAsString(listingDto);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/listings/" + client.getClientUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(listingJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateListingSuccessfullyReturnsSavedListing() throws Exception {

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        clientService.createOrUpdateClient(client);
        ListingDto listingDto = TestDataUtil.createTestListingDtoA(null);
        String listingJson = objectMapper.writeValueAsString(listingDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/listings/" + client.getClientUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(listingJson))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.listingUUID").exists()
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.title").value("ListingDto A")
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.seller.clientUUID").value(client.getClientUUID().toString())
                );

    }


    @Test
    public void testThatGetAllListingsSuccessfullyReturnsHttp200OK() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/listings")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAllListingsSuccessfullyReturnsSavedListings() throws Exception {
        ClientEntity client = TestDataUtil.createTestClientEntityA();
        clientService.createOrUpdateClient(client);
        ListingEntity listing = TestDataUtil.createTestListingEntityA(null);
        listingService.createOrUpdateListing(listing, client.getClientUUID());
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/listings")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].listingUUID").exists()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].title").value("ListingEntity A")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].seller.clientUUID").exists()
        );
    }

    @Test
    public void testThatGetListingWithUUIDReturnsHttp200OKWhenListingExists() throws Exception {
        ClientEntity client = TestDataUtil.createTestClientEntityA();
        clientService.createOrUpdateClient(client);
        ListingEntity listing = TestDataUtil.createTestListingEntityA(null);
        listingService.createOrUpdateListing(listing, client.getClientUUID());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/listings/" + listing.getListingUUID())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetListingWithUUIDReturnsHttp404NotFoundWhenListingDoesNotExist() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/listings/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetListingWithUUIDReturnsListing() throws Exception {

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        clientService.createOrUpdateClient(client);
        ListingEntity listing = TestDataUtil.createTestListingEntityA(null);
        listingService.createOrUpdateListing(listing, client.getClientUUID());

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/listings/" + listing.getListingUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        ListingEntity responseListing = objectMapper.readValue(response, ListingEntity.class);

        assertEquals(responseListing.getListingUUID(), listing.getListingUUID());
        assertEquals(responseListing.getTitle(), listing.getTitle());
        assertEquals(responseListing.getSeller().getClientUUID(), client.getClientUUID());
    }

    @Test
    public void testThatFullUpdateListingSuccessfullyReturnsHttp200OKWhenListingExists() throws Exception {

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        clientService.createOrUpdateClient(client);

        ListingEntity listing = TestDataUtil.createTestListingEntityA(null);
        listingService.createOrUpdateListing(listing, client.getClientUUID());

        ListingEntity newListing = TestDataUtil.createTestListingEntityA(client);
        String newListingJson = objectMapper.writeValueAsString(newListing);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/listings/" + listing.getListingUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newListingJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());


    }

    @Test
    public void testThatFullUpdateListingSuccessfullyReturnsHttp404NotFoundWhenListingDoesNotExist() throws Exception {

        ListingEntity listing = TestDataUtil.createTestListingEntityA(null);
        String newListingJson = objectMapper.writeValueAsString(listing);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/listings/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newListingJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatFullUpdateListingSuccessfullyUpdatesExistingListing() throws Exception {

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        ClientEntity savedClient = clientService.createOrUpdateClient(client);

        ListingEntity listing = TestDataUtil.createTestListingEntityA(null);
        ListingEntity savedListing = listingService.createOrUpdateListing(listing, savedClient.getClientUUID());

        ListingEntity newListing = TestDataUtil.createTestListingEntityA(savedClient);
        String newListingJson = objectMapper.writeValueAsString(newListing);

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders.put("/listings/" + savedListing.getListingUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(newListingJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        ListingEntity responseListing = objectMapper.readValue(response, ListingEntity.class);

        assertEquals(responseListing.getListingUUID(), listing.getListingUUID());
        assertEquals(responseListing.getTitle(), newListing.getTitle());
        assertEquals(responseListing.getSeller().getClientUUID(), savedClient.getClientUUID());
    }

    @Test
    public void testThatPartialUpdateListingWithUUIDSuccessfullyReturnsHttp200OKWhenListingExists() throws Exception {
        ClientEntity client = TestDataUtil.createTestClientEntityA();
        clientService.createOrUpdateClient(client);

        ListingEntity listing = TestDataUtil.createTestListingEntityA(null);
        ListingEntity savedListingEntity = listingService.createOrUpdateListing(listing, client.getClientUUID());


        String partialUpdateJson = "{ \"title\": \"patchedTitle\" }";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .patch("/listings/" + savedListingEntity.getListingUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUpdateJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdateListingWithUUIDReturnsHttp404NotFoundWhenListingDoesNotExist() throws Exception {

        String partialUpdateJson = "{ \"title\": \"patchedTitle\" }";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .patch("/listings/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUpdateJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatPartialUpdateListingWithUUIDReturnsUpdatedListing() throws Exception {
        ClientEntity client = TestDataUtil.createTestClientEntityA();
        clientService.createOrUpdateClient(client);

        ListingEntity listing = TestDataUtil.createTestListingEntityA(null);
        ListingEntity savedListingEntity = listingService.createOrUpdateListing(listing, client.getClientUUID());

        String partialUpdateJson = "{ \"title\": \"patchedTitle\" }";

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .patch("/listings/" + savedListingEntity.getListingUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(partialUpdateJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        ListingEntity responseListing = objectMapper.readValue(response, ListingEntity.class);

        assertEquals(responseListing.getListingUUID(), savedListingEntity.getListingUUID());
        assertEquals(responseListing.getTitle(), "patchedTitle");
    }

    @Test
    public void testThatDeleteListingWithUUIDSuccessfullyReturnsHttp200OK() throws Exception {
        ClientEntity client = TestDataUtil.createTestClientEntityA();
        clientService.createOrUpdateClient(client);
        ListingEntity listing = TestDataUtil.createTestListingEntityA(null);
        ListingEntity savedListing = listingService.createOrUpdateListing(listing, client.getClientUUID());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/listings/" + savedListing.getListingUUID())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteListingWithUUIDReturnsHttp404NotFoundIfClientDoesNotExist() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/listings/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatDeleteClientWithUUIDSuccessfullyDeletesClient() throws Exception {

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        ClientEntity savedClient = clientService.createOrUpdateClient(client);
        ListingEntity listing = TestDataUtil.createTestListingEntityA(null);
        ListingEntity savedListing = listingService.createOrUpdateListing(listing, savedClient.getClientUUID());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/listings/" + savedListing.getListingUUID())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());


        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/listings/" + savedListing.getListingUUID())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}