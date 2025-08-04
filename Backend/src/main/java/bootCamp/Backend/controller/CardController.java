package bootCamp.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import bootCamp.Backend.DTO.ResponseDTO;
import bootCamp.Backend.DTO.CardDTO;
import bootCamp.Backend.DTO.ResponseCardDTO;
import bootCamp.Backend.service.CardService;

@RestController
@RequestMapping("/Card")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/CreateCard")
    public ResponseEntity<ResponseDTO> createCard(@RequestBody CardDTO cardDTO) {
        ResponseDTO response = cardService.createCard(cardDTO);
        
        // Manejo más robusto del estado HTTP
        if ("OK".equals(response.getStatus())) {
            return ResponseEntity.ok(response);
        } else if ("NOT_FOUND".equals(response.getStatus())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseCardDTO<?> getAllCards() {
        return cardService.getAllCards();
    }
}