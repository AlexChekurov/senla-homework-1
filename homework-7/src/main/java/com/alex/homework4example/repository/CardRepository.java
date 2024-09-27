package com.alex.homework4example.repository;

import com.alex.homework4example.entity.Card;
import org.springframework.stereotype.Repository;

@Repository
public class CardRepository extends AbstractRepository<Card> {

    public CardRepository() {
        super(Card.class);
    }

}
