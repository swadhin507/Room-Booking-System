package com.project.controller;

import com.project.entities.Room;
import com.project.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {


    private final RoomService roomService;
    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms(){
       List<Room> list = roomService.getAllRooms();
           return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable long id){
        Room room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @PostMapping
    public ResponseEntity<List<Room>> addRoom(@Valid @RequestBody List<Room> room){
            List<Room> r = this.roomService.addRoom(room);
            return ResponseEntity.status(HttpStatus.CREATED).body(r);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable long id){
        roomService.deleteRoom(id);
      return ResponseEntity.noContent().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){

            this.roomService.deleteAll();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@Valid @RequestBody Room room, @PathVariable long id){
            Room r = this.roomService.updateRoom(room,id);
            return ResponseEntity.ok(r);

    }
}
