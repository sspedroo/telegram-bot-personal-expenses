package com.telegram.bot.telegram;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class States {
    public enum State {
        START,
        AGUARDANDO_DESCRICAO,
        AGUARDANDO_VALOR,
        AGUARDANDO_FORMAPAGAMENTO,
        AGUARDANDO_CATEGORIA,
        ESPERANDO_CHAVE_SECRETA;
    }

    private State currentState;
    private String description;
    private BigDecimal value;
    private String paymentMethod;
    private String category;
    private String subcategory;

    public States(){
        this.currentState = State.START;
    }

}
