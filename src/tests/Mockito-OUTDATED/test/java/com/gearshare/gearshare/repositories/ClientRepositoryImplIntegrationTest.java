package com.gearshare.gearshare.repositories;

import com.gearshare.gearshare.TestDataUtil;
import com.gearshare.gearshare.domain.entities.ClientEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientRepositoryImplIntegrationTest {

    private ClientRepository underTest;

    @Autowired
    public ClientRepositoryImplIntegrationTest(ClientRepository underTest) {
        this.underTest = underTest;
    }


    @Test
    public void testThatClientCanBeCreatedAndRecalled(){
        ClientEntity client = TestDataUtil.createTestClientEntityA();
        underTest.save(client);
        Optional<ClientEntity> result = underTest.findById(client.getClientUUID());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(client);

    }

    @Test
    public void testThatMultipleClientsCanBeCreatedAndRecalled(){
        ClientEntity clientA = TestDataUtil.createTestClientEntityA();
        underTest.save(clientA);
        ClientEntity clientB = TestDataUtil.createTestClientEntityB();
        underTest.save(clientB);
        ClientEntity clientC = TestDataUtil.createTestClientEntityC();
        underTest.save(clientC);

        Iterable<ClientEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(clientA, clientB, clientC);
    }

    @Test
    public void testThatClientCanBeUpdated(){
        ClientEntity client = TestDataUtil.createTestClientEntityA();
        underTest.save(client);
        client.setUsername("new_username");
        underTest.save(client);
        Optional<ClientEntity> result = underTest.findById(client.getClientUUID());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(client);
    }

    @Test
    public void testThatClientCanBeDeleted(){
        ClientEntity client = TestDataUtil.createTestClientEntityA();
        underTest.save(client);
        underTest.deleteById(client.getClientUUID());
        Optional<ClientEntity> result = underTest.findById(client.getClientUUID());
        assertThat(result).isEmpty();
    }


}
