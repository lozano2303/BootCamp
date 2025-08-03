package bootCamp.Backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;

import bootCamp.Backend.DTO.ResponseDTO;
import bootCamp.Backend.service.CardService;
import bootCamp.Backend.DTO.CardDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/Card")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/CreateCard")
    public ResponseEntity<ResponseDTO> createCard (@RequestBody CardDTO cardDTO) {
        ResponseDTO response = cardService.createCard(cardDTO);
        if (response.getStatus().equals("OK")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
