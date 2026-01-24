package com.gearshare.gearshare.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gearshare.gearshare.TestDataUtil;
import com.gearshare.gearshare.domain.entities.ClientEntity;
import com.gearshare.gearshare.services.ClientService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest //(classes = GearshareApplicationTests.class)
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc( addFilters = false )
public class ClientControllerIntegrationTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private ClientService clientService;


    @Autowired
    public ClientControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper,ClientService clientService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.clientService = clientService;
    }


    @Test
    public void testThatCreateClientSuccessfullyReturnsHttp201Created() throws Exception {
        ClientEntity client = TestDataUtil.createTestClientEntityA();
        client.setClientUUID(null);
        String clientJson = objectMapper.writeValueAsString(client);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated());
    }


    @Test
    public void testThatCreateClientSuccessfullyReturnsSavedClient() throws Exception {
        ClientEntity client = TestDataUtil.createTestClientEntityA();
        client.setClientUUID(null);
        String clientJson = objectMapper.writeValueAsString(client);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.clientUUID").exists()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.username").value("testclientA")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.email").value("testA@client.com")
        );
    }

    @Test
    public void testThatGetAllClientsSuccessfullyReturnsHttp200OK() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAllClientsSuccessfullyReturnsSavedClients() throws Exception {


        ClientEntity client0 = TestDataUtil.createTestClientEntityA();
        clientService.createOrUpdateClient(client0);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].clientUUID").exists()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].username").value("testclientA")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].email").value("testA@client.com")
        );
    }

    @Test
    public void testThatGetClientWithUUIDReturnsHttp200OKWhenClientExists() throws Exception {

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        clientService.createOrUpdateClient(client);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/clients/" + client.getClientUUID())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetClientWithUUIDReturnsHttp404NotFoundKWhenClientDoesNotExist() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/clients/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetClientWithUUIDSuccessfullyReturnsClient() throws Exception { // AAA test

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        clientService.createOrUpdateClient(client);

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/clients/" + client.getClientUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(
                        MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ClientEntity responseClient = objectMapper.readValue(response, ClientEntity.class);

        assertEquals(responseClient.getClientUUID(), client.getClientUUID());
        assertEquals(responseClient.getUsername(), "testclientA");
        assertEquals(responseClient.getEmail(), "testA@client.com");
    }

    @Test
    public void testThatGetClientWithUsernameSuccessfullyReturns200OKWhenClientExists() throws Exception{

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        clientService.createOrUpdateClient(client);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/clients/profiles/" + client.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

    }

    @Test
    public void testThatGetClientWithUsernameReturns404NotFoundWhenNoUsernameIsFound() throws Exception{


        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/clients/profiles/nonExistentUser")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );

    }

    @Test
    public void testThatGetClientWithUsernameSuccessfullyReturnsClientWithUsername() throws Exception{

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        ClientEntity savedClient = clientService.createOrUpdateClient(client);

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/clients/profiles/" + savedClient.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andReturn();

        String response = result.getResponse().getContentAsString();
        ClientEntity responseEntity = objectMapper.readValue(response, ClientEntity.class);

        assertEquals(responseEntity.getClientUUID(), savedClient.getClientUUID());
        assertEquals(responseEntity.getUsername(), savedClient.getUsername());
        assertEquals(responseEntity.getEmail(), savedClient.getEmail());

    }

    @Test
    public void testThatFullUpdateClientSuccessfullyReturnsHttp200OKWhenClientExists() throws Exception {

        ClientEntity clientA = TestDataUtil.createTestClientEntityA();
        ClientEntity savedClientA = clientService.createOrUpdateClient(clientA);

        ClientEntity newClientA = TestDataUtil.createTestClientEntityA();
        String newClientAJson = objectMapper.writeValueAsString(newClientA);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put("/api/clients/" + savedClientA.getClientUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newClientAJson)
        )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatFullUpdateClientReturnsHttp404NotFoundWhenClientDoesNotExist() throws Exception {

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        String clientJson = objectMapper.writeValueAsString(client);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/clients/" + UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(clientJson)
                ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    public void testThatFullUpdateClientSuccessfullyUpdatesExistingClient() throws Exception {

        ClientEntity clientA = TestDataUtil.createTestClientEntityA();
        ClientEntity savedClientA = clientService.createOrUpdateClient(clientA);

        ClientEntity clientB = TestDataUtil.createTestClientEntityB();
        String clientBJson = objectMapper.writeValueAsString(clientB);

        MvcResult result = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/api/clients/" + savedClientA.getClientUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(clientBJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        ClientEntity responseClient = objectMapper.readValue(response, ClientEntity.class);

        assertEquals(responseClient.getClientUUID(), clientA.getClientUUID());
        assertEquals(responseClient.getUsername(), clientB.getUsername());
        assertEquals(responseClient.getEmail(), clientB.getEmail());
    }

    @Test
    public void testThatPartialUpdateClientSuccessfullyReturnsHttp200OK() throws Exception{

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        ClientEntity savedClient = clientService.createOrUpdateClient(client);

        String partialUpdateJson = "{ \"username\": \"patchedUsername\" }";

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/clients/" + savedClient.getClientUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(partialUpdateJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThatPartialUpdateClientReturnsHttp404NotFoundIfClientDoesNotExist() throws Exception{

        ClientEntity client = TestDataUtil.createTestClientEntityA();

        String partialUpdateJson = "{ \"username\": \"patchedUsername\" }";

        mockMvc.perform(
                MockMvcRequestBuilders
                        .patch("/api/clients/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(partialUpdateJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());


    }

    @Test
    public void testThatPartialUpdateClientSuccessfullyReturnsUpdatedClient() throws Exception{

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        ClientEntity savedClient = clientService.createOrUpdateClient(client);

        String partialUpdateJson = "{ \"username\": \"patchedUsername\" }";

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/clients/" + savedClient.getClientUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(partialUpdateJson)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        ClientEntity responseClient = objectMapper.readValue(response, ClientEntity.class);

        assertEquals(responseClient.getClientUUID(), savedClient.getClientUUID());
        assertEquals(responseClient.getUsername(), "patchedUsername");

    }

    @Test
    public void testThatDeleteClientWithUUIDSuccessfullyReturnsHTTP204NoContent() throws Exception {

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        ClientEntity savedClient = clientService.createOrUpdateClient(client);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/clients/" + savedClient.getClientUUID())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteClientWithUUIDReturnsHttp404NotFoundIfClientDoesNotExist() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/clients/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatDeleteClientWithUUIDSuccessfullyDeletesClient() throws Exception {

        ClientEntity client = TestDataUtil.createTestClientEntityA();
        ClientEntity savedClient = clientService.createOrUpdateClient(client);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/clients/" + savedClient.getClientUUID())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());


        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/clients/" + savedClient.getClientUUID())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
