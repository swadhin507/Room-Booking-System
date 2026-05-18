package com.project.controller;

import com.project.entities.Guest;
import com.project.services.GuestServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guest")
public class GuestController {

    @Autowired
    private GuestServices guestServices;

    @GetMapping
    public ResponseEntity<List<Guest>> getAllGuest(){
        List<Guest> guests = guestServices.getAll();
        if(guests.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(guests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guest> getById(@PathVariable long id){
        Guest guest = this.guestServices.getById(id);
            return ResponseEntity.ok(guest);
    }
    @PostMapping
    public ResponseEntity<Guest> addGuest(@Valid @RequestBody Guest guest){
        try {
            Guest g = this.guestServices.addGuest(guest);
            return ResponseEntity.status(HttpStatus.CREATED).body(g);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
        try{
            this.guestServices.deleteAll();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
        try{
            this.guestServices.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(@RequestBody Guest guest, @PathVariable long id){
        try{
            Guest g = this.guestServices.updateGuest(guest,id);
            return ResponseEntity.ok(g);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
