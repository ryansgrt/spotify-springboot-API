package com.enigma.sepotipi.services.impl;

import com.enigma.sepotipi.entity.Account;
import com.enigma.sepotipi.entity.Profile;
import com.enigma.sepotipi.repository.AccountRepository;
import com.enigma.sepotipi.repository.ProfileRepository;
import com.enigma.sepotipi.services.ProfileServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfileServicesDBImplTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProfileServices profileServices;

    @Autowired
    ProfileRepository profileRepository;

    @BeforeEach
    public void cleanUp(){
        profileRepository.deleteAll();
    }

    @Test
    void saveProfile_shouldSaveOneProfile_whenInvoked() {
        Account account = new Account();
        account.setId("aa");
        account.setActive(true);
        account = accountRepository.save(account);

        Profile profile = new Profile();
        profile.setAccountId("aa");
        profile.setFirstName("a");
//        profile.setAccount(account);
        profile.setEmail("aa@aa.com");
        profileServices.saveProfile(profile);
        assertEquals(1, profileRepository.findAll().size());
    }

    @Test
    void getProfile() {
    }

    @Test
    void searchProfiles() {
    }

    @Test
    void deleteProfile() {
    }
}