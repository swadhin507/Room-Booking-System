package com.project.controller;

import com.project.dto.BookingRequestDto;
import com.project.entities.Booking;
import com.project.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

     private final BookingService bookingService;
    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAll(){
        List<Booking> list = this.bookingService.getAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable long id){
        Booking booking = this.bookingService.getById(id);
        return ResponseEntity.ok(booking);
    }
    @PostMapping
    public ResponseEntity<Booking> addBooking(@Valid @RequestBody BookingRequestDto dto){
            Booking b=this.bookingService.addBooking(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(b);
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
            this.bookingService.deleteAll();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
            this.bookingService.deleteById(id);
            return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@Valid @RequestBody BookingRequestDto dto,@PathVariable long id){
            Booking b = this.bookingService.updateBooking(dto,id);
            return ResponseEntity.ok(b);
    }


}
