package bootCamp.Backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import bootCamp.Backend.DTO.CardDTO;
import bootCamp.Backend.DTO.ResponseDTO;
import bootCamp.Backend.model.Card;
import bootCamp.Backend.repository.ICard;

@Service
public class CardService {

    @Autowired

    private ICard cardRepository;

    private boolean isAnyStadisticNull(CardDTO cardDTO){
        return cardDTO.getStroke() == null ||
                cardDTO.getDefense() == null ||
                cardDTO.getAverage() == null ||
                cardDTO.getGoals() == null ||
                cardDTO.getPass() == null ||
                cardDTO.getPrice() == null;
    }

    //Crear carta
    public ResponseDTO createCard(CardDTO cardDTO){
        if (cardDTO.getCardName() == null || cardDTO.getCardName().trim().isEmpty()) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre de la carta no puede ser nulo o vacío");
        }
        if (cardDTO.getCardName().length() > 20) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "El nombre de la carta no puede tener más de 20 caracteres");
        }
        if (cardDTO.getImg() == null || cardDTO.getImg().trim().isEmpty()) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "La imagen de la carta no puede ser nula o vacía");
        }
        if (isAnyStadisticNull(cardDTO)) {
            return new ResponseDTO(HttpStatus.BAD_REQUEST.toString(), "No puede faltar ninguna estadística de la carta");
    }

    //DTO a model
    Card card = new Card();
        card.setCardName(cardDTO.getCardName());
        card.setImg(cardDTO.getImg());
        card.setStroke(cardDTO.getStroke());
        card.setDefense(cardDTO.getDefense());
        card.setAverage(cardDTO.getAverage());
        card.setGoals(cardDTO.getGoals());
        card.setPass(cardDTO.getPass());
        card.setPrice(cardDTO.getPrice());

        cardRepository.save(card);

    return new ResponseDTO(HttpStatus.OK.toString(), "carta creada correctamente");
    }
}
