package com.project.services;

import com.project.dao.GuestRepository;
import com.project.entities.Guest;
import com.project.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServices {


    private final GuestRepository guestRepository;

    @Autowired
    public GuestServices(GuestRepository guestRepository){
        this.guestRepository = guestRepository;
    }

    public List<Guest> getAll(){

        return this.guestRepository.findAll();
    }

    public Guest getById(long id){
            return this.guestRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Guest not found"));

    }
    public Guest addGuest(Guest guest){
        return this.guestRepository.save(guest);
    }
    public void deleteAll(){
        this.guestRepository.deleteAll();
    }
    public void deleteById(long id){
        Guest guest = guestRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Guest not found"));
    }

    public Guest updateGuest(Guest guest, long id){
        guest.setId(id);
        return this.guestRepository.save(guest);
    }
}
