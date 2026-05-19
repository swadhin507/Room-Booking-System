package com.project.service;

import com.project.repository.GuestRepository;
import com.project.entities.Guest;
import com.project.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {


    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository){
        this.guestRepository = guestRepository;
    }

    public List<Guest> getAll(){

        return this.guestRepository.findAll();
    }

    public Guest getById(long id){
            return this.guestRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Guest not found"));

    }
    public List<Guest> addGuest(List<Guest> guest){
        return this.guestRepository.saveAll(guest);
    }
    public void deleteAll(){
        this.guestRepository.deleteAll();
    }
    public void deleteById(long id){
        Guest guest = guestRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Guest not found"));
        guestRepository.delete(guest);
    }

    public Guest updateGuest(Guest guest, long id){
        Guest existingGuest = guestRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Guest not found"));

        existingGuest.setGuestName(guest.getGuestName());
        existingGuest.setLocation(guest.getLocation());
        existingGuest.setNumber(guest.getNumber());
        return this.guestRepository.save(existingGuest);
    }
}
