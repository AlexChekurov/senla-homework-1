package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.CardDTO;
import com.alex.homework4example.entity.Card;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-09T10:23:07+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class CardMapperImpl implements CardMapper {

    @Override
    public Card toEntity(CardDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Card card = new Card();

        card.setId( dto.getId() );
        card.setCardNumber( dto.getCardNumber() );
        card.setCardType( dto.getCardType() );
        card.setExpirationDate( dto.getExpirationDate() );
        card.setCvv( dto.getCvv() );
        if ( dto.getCreatedAt() != null ) {
            card.setCreatedAt( dto.getCreatedAt().toLocalDate() );
        }

        return card;
    }

    @Override
    public CardDTO toDto(Card entity) {
        if ( entity == null ) {
            return null;
        }

        CardDTO cardDTO = new CardDTO();

        cardDTO.setId( entity.getId() );
        cardDTO.setCardNumber( entity.getCardNumber() );
        cardDTO.setCardType( entity.getCardType() );
        cardDTO.setExpirationDate( entity.getExpirationDate() );
        cardDTO.setCvv( entity.getCvv() );
        if ( entity.getCreatedAt() != null ) {
            cardDTO.setCreatedAt( entity.getCreatedAt().atStartOfDay() );
        }

        return cardDTO;
    }
}
