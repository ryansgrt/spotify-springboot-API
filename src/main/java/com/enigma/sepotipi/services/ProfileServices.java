package com.enigma.sepotipi.services;

import com.enigma.sepotipi.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfileServices {

    public Profile saveProfile(Profile profile);
    public Profile getProfile(String id);
    public Page<Profile> searchProfiles(Pageable pageable, Profile searchForm);
    public void deleteProfile(String id);
}
