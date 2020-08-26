package com.enigma.sepotipi.controller;

import com.enigma.sepotipi.entity.Profile;
import com.enigma.sepotipi.services.ProfileServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/profile")
public class ProfileController {

    @Autowired
    ProfileServices profileServices;

    @PostMapping
    public Profile saveProfile(@RequestBody Profile profile){
        return profileServices.saveProfile(profile);
    }

    @GetMapping("/{id}")
    public Profile getProfile(@PathVariable String id){
        return profileServices.getProfile(id);
    }

    @GetMapping("/search")
    public Page<Profile> searchProfiles(@RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size, @RequestBody Profile searchForm){
        Pageable pageable = PageRequest.of(page-1, size);
        return profileServices.searchProfiles(pageable, searchForm);
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable String id){
        profileServices.deleteProfile(id);
    }
}
