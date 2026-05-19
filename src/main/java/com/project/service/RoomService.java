package com.project.service;

import com.project.repository.RoomRepository;
import com.project.entities.Room;
import com.project.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {


    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms(){
        return this.roomRepository.findAll();

    }

    public Room getRoomById(long id){
            return this.roomRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Room not found"));
    }

    public List<Room> addRoom(List<Room> rooms){
        return this.roomRepository.saveAll(rooms);
    }

    public void deleteRoom(long id){
       Room room = roomRepository.findById(id)
               .orElseThrow(()->new ResourceNotFoundException("Room not found"));
       roomRepository.delete(room);
    }

    public void deleteAll() {
        this.roomRepository.deleteAll();
    }

    public Room updateRoom(Room room,long id){
        Room existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        existingRoom.setRoomNumber(room.getRoomNumber());
        existingRoom.setType(room.getType());
        existingRoom.setStatus(room.getStatus());
        return this.roomRepository.save(existingRoom);
    }


}
