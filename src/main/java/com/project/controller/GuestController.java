package com.project.controller;

import com.project.entities.Guest;
import com.project.service.GuestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guest")
public class GuestController {


    private final GuestService guestServices;
    @Autowired
    public GuestController(GuestService guestServices) {
        this.guestServices = guestServices;
    }

    @GetMapping
    public ResponseEntity<List<Guest>> getAllGuest(){
        List<Guest> guests = guestServices.getAll();
        return ResponseEntity.ok(guests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Guest> getById(@PathVariable long id){
        Guest guest = this.guestServices.getById(id);
            return ResponseEntity.ok(guest);
    }
    @PostMapping
    public ResponseEntity<List<Guest>> addGuest(@Valid @RequestBody List<Guest> guests){

            List<Guest> g = this.guestServices.addGuest(guests);
            return ResponseEntity.status(HttpStatus.CREATED).body(g);
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
            this.guestServices.deleteAll();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
            this.guestServices.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(@Valid @RequestBody Guest guest, @PathVariable long id){

            Guest g = this.guestServices.updateGuest(guest,id);
            return ResponseEntity.ok(g);
    }
}
