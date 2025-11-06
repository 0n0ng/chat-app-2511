package com.ll.chatApp.domain.chat.chatRoom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiChatRoomController {

    @GetMapping("/api/v1/chat/rooms")
    public String getChatRooms () {
        return "채팅방 목록 조회 완료";
    }

    @GetMapping("/api/v1/chat/rooms/{roomId}")
    public String getChatRoom (@PathVariable("roomId") Long roomId) {
        return roomId + "번 채팅방 조회 완료";
    }

    @PostMapping("/api/v1/chat/rooms/{roomId}")
    public String createChatRooms (@PathVariable("roomId") Long roomId) {
        return "채팅방 생성 완료";
    }
}
