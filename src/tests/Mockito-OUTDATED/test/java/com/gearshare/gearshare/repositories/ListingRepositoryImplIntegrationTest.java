package com.gearshare.gearshare.repositories;

import com.gearshare.gearshare.TestDataUtil;
import com.gearshare.gearshare.domain.entities.ClientEntity;
import com.gearshare.gearshare.domain.entities.ListingEntity;
import org.apache.catalina.Store;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith( SpringExtension.class )
@DirtiesContext( classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ListingRepositoryImplIntegrationTest {

    private ListingRepository underTest;
    private ClientRepository clientRepository;

    @Autowired
    public ListingRepositoryImplIntegrationTest(ClientRepository clientRepository, ListingRepository underTest) {
        this.clientRepository = clientRepository;
        this.underTest = underTest;
    }

    @Test
    public void testThatListingCanBeCreatedAndRecalled(){
        ClientEntity testClientA = TestDataUtil.createTestClientEntityA();
        clientRepository.save(testClientA);
        ListingEntity listing = TestDataUtil.createTestListingEntityA(testClientA);
        underTest.save(listing);
        Optional<ListingEntity> result = underTest.findById(listing.getListingUUID());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(listing);

    }

    @Test
    public void testThatMultipleListingsCanBeCreatedAndRecalled(){
        ClientEntity testClientA = TestDataUtil.createTestClientEntityA();
        clientRepository.save(testClientA);
        ListingEntity listingA = TestDataUtil.createTestListingEntityA(testClientA);
        underTest.save(listingA);

        ClientEntity testClientB = TestDataUtil.createTestClientEntityB();
        clientRepository.save(testClientB);
        ListingEntity listingB = TestDataUtil.createTestListingEntityB(testClientB);
        underTest.save(listingB);

        ClientEntity testClientC = TestDataUtil.createTestClientEntityC();
        clientRepository.save(testClientC);
        ListingEntity listingC = TestDataUtil.createTestListingEntityC(testClientC);
        underTest.save(listingC);

        Iterable<ListingEntity> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(listingA, listingB, listingC);


    }

    @Test
    public void testThatListingCanBeUpdated(){
        ClientEntity testClientA = TestDataUtil.createTestClientEntityA();
        clientRepository.save(testClientA);
        ListingEntity listing = TestDataUtil.createTestListingEntityA(testClientA);
        underTest.save(listing);
        listing.setTitle("new title");
        underTest.save(listing);
        Optional<ListingEntity> result = underTest.findById(listing.getListingUUID());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(listing);
    }

    @Test
    public void testThatListingCanBeDeleted(){
        ClientEntity testClientA = TestDataUtil.createTestClientEntityA();
        clientRepository.save(testClientA);
        ListingEntity listing = TestDataUtil.createTestListingEntityA(testClientA);
        underTest.save(listing);
        underTest.deleteById(listing.getListingUUID());
        Optional<ListingEntity> result = underTest.findById(listing.getListingUUID());
        assertThat(result).isEmpty();
    }

}
