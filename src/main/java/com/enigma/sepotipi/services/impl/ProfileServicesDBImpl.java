package com.enigma.sepotipi.services.impl;
import com.enigma.sepotipi.entity.Profile;
import com.enigma.sepotipi.exception.AccountAlreadyExistException;
import com.enigma.sepotipi.exception.DeactivatedAccountException;
import com.enigma.sepotipi.exception.EmailNotValidException;
import com.enigma.sepotipi.exception.ResourceNotFoundException;
import com.enigma.sepotipi.repository.ProfileRepository;
import com.enigma.sepotipi.services.AccountServices;
import com.enigma.sepotipi.services.ProfileServices;
import com.enigma.sepotipi.specification.ProfileSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProfileServicesDBImpl implements ProfileServices {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    AccountServices accountServices;
    @Override
    public Profile saveProfile(Profile profile) {
        if(profile.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            System.out.println("Email is valid.");
            List<Profile> profiles = profileRepository.findAll();
            for(Profile p: profiles){
                if(p.getEmail().equals(profile.getEmail())){
                    throw new AccountAlreadyExistException();
                }
            }
        } else throw new EmailNotValidException();
        profile.setAccount(accountServices.getAccount(profile.getAccountId()));
        if(profile.getAccount().getActive() == false){
            throw new DeactivatedAccountException();
        }
        return profileRepository.save(profile);
    }
    @Override
    public Profile getProfile(String id) {
        Profile profile = null;
        if(profileRepository.findById(id).isPresent()){
            profile = profileRepository.findById(id).get();
        } else throw new ResourceNotFoundException();
        return profile;
    }
    @Override
    public Page<Profile> searchProfiles(Pageable pageable, Profile searchForm) {
        Page<Profile> profiles = profileRepository.findAll(ProfileSpecification.findByCriteria(searchForm),pageable);
        return profiles;
    }
    @Override
    public void deleteProfile(String id) {
        profileRepository.deleteById(id);
    }
}