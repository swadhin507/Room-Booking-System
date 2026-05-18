package com.project.controller;

import com.project.entities.Room;
import com.project.services.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms(){
       List<Room> list = roomService.getAllRooms();
       if(list.isEmpty()){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
           return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable long id){
        Room room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    @PostMapping
    public ResponseEntity<Room> addRoom(@Valid @RequestBody Room room){
        try{
            Room r = this.roomService.addRoom(room);
            return ResponseEntity.status(HttpStatus.CREATED).body(r);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Room> deleteRoom(@PathVariable long id){
      return ResponseEntity.ok(roomService.getRoomById(id));
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteAll(){
        try{
            this.roomService.deleteAll();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@RequestBody Room room, @PathVariable long id){
        try {
            Room r = this.roomService.updateRoom(room,id);
            return ResponseEntity.ok(r);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
