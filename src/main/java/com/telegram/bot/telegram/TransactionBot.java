package com.telegram.bot.telegram;

import com.telegram.bot.domain.category.model.Category;
import com.telegram.bot.domain.category.repository.CategoryRepository;
import com.telegram.bot.domain.paymentMethod.model.PaymentMethod;
import com.telegram.bot.domain.paymentMethod.repository.PaymentMethodRepository;
import com.telegram.bot.domain.transactions.model.Transaction;
import com.telegram.bot.domain.transactions.repository.TransactionRepository;
import com.telegram.bot.exceptions.exceptionHandler.TelegramException;
import com.telegram.bot.telegram.domain.InscritosTelegramBot;
import com.telegram.bot.telegram.repository.InscritosTelegramBotRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TransactionBot implements LongPollingSingleThreadUpdateConsumer, SpringLongPollingBot {
    @Autowired
    private InscritosTelegramBotRepository inscritosTelegramBotRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    private Map<Long, States> userStates = new HashMap<>();
    private final String SECRET_KEY = "senha do bot vai aqui";
    private final TelegramClient telegramClient;

    public TransactionBot(){
        telegramClient = new OkHttpTelegramClient(getBotToken());
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        sendMsg(message);
    }

    public void sendForceReplyMessage(Long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(new ForceReplyKeyboard(true, true, null))
                .build();

        sendMsg(message);
    }

    public void sendCategoryOptions(long chatId){
        List<Category> categories = categoryRepository.findAll();
        List<InlineKeyboardButton> buttonsList = new ArrayList<>();

        for (Category category : categories){
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(category.getName())
                    .callbackData(category.getName())
                    .build();
            buttonsList.add(button);
        }

        List<InlineKeyboardRow> rows = new ArrayList<>();
        for (InlineKeyboardButton button : buttonsList){
            InlineKeyboardRow inlineKeyboardButtons = new InlineKeyboardRow(button);
            rows.add(inlineKeyboardButtons);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(rows);

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Escolha uma das categorias abaixo:")
                .replyMarkup(inlineKeyboardMarkup)
                .build();

       sendMsg(message);
    }

    private void handleNewUser(long chatId, String messageText) {
        States userState = userStates.getOrDefault(chatId, new States());

        if (messageText.equals("/start")) {
            sendMessage(chatId, "Por favor, forneça a chave secreta para acessar o bot.");
            userState.setCurrentState(States.State.ESPERANDO_CHAVE_SECRETA);
            userStates.put(chatId, userState);
        } else if (userState.getCurrentState() == States.State.ESPERANDO_CHAVE_SECRETA) {
            if (messageText.equals(SECRET_KEY)) {
                InscritosTelegramBot allowedUser = new InscritosTelegramBot();
                allowedUser.setChatId(chatId);
                inscritosTelegramBotRepository.save(allowedUser);
                sendMessage(chatId, "Chave secreta correta! Você agora tem acesso ao bot.");
                sendMessage(chatId, "Bem-vindo! Como posso ajudar você hoje?");
                userState.setCurrentState(States.State.START);
                userStates.put(chatId, userState);
            } else {
                sendMessage(chatId, "Chave secreta incorreta. Tente novamente.");
            }
        } else {
            sendMessage(chatId, "Você não tem permissão para usar este bot. Por favor, forneça a chave secreta.");
        }
    }

    public void sendPaymentMethodOptions(long chatId){
        List<KeyboardRow> keyboard = new ArrayList<>();
        List<PaymentMethod> allPaymentMethod = paymentMethodRepository.findAll();

        for (PaymentMethod paymentMethod : allPaymentMethod){
            KeyboardRow row = new KeyboardRow();
            row.add(paymentMethod.getName());
            keyboard.add(row);
        }

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(keyboard);
        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Escolha a forma de pagamento: ")
                .replyMarkup(replyKeyboardMarkup)
                .build();

        sendMsg(message);
    }

    public void processUserReply(Long chatId, String originalMessage, String userReply){
        States states = userStates.get(chatId); //pegando o estado atual do usuario

        if (states.getCurrentState() == States.State.AGUARDANDO_DESCRICAO){
            states.setDescription(userReply);
            sendForceReplyMessage(chatId, "Valor? Por favor, digite apenas números (exemplo: 10.00)");
            states.setCurrentState(States.State.AGUARDANDO_VALOR);
        } else if (states.getCurrentState() == States.State.AGUARDANDO_VALOR) {
            states.setValue(new BigDecimal(userReply));
            sendPaymentMethodOptions(chatId);
            states.setCurrentState(States.State.AGUARDANDO_FORMAPAGAMENTO);
        } else if (states.getCurrentState() == States.State.AGUARDANDO_FORMAPAGAMENTO){
            states.setPaymentMethod(userReply);
            sendCategoryOptions(chatId);
            states.setCurrentState(States.State.AGUARDANDO_CATEGORIA);
        } else if (states.getCurrentState() == States.State.AGUARDANDO_CATEGORIA) {
            states.setCategory(userReply);
            createExpense(chatId, states.getDescription(), states.getValue(), states.getPaymentMethod(), states.getCategory());
            afterRegisterExpenseMessage(chatId);
            userStates.remove(chatId);
        }
    }

    public void afterRegisterExpenseMessage(long chatId){
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Use o comando /novo_gasto para quando quiser registrar um gasto novamente.")
                .replyMarkup(ReplyKeyboardRemove.builder()
                        .removeKeyboard(true)
                        .selective(true)
                        .build())
                .build();

        sendMsg(message);
    }

    public void createExpense(long chatId, String description, BigDecimal value, String formaDePagamento, String categoria){
        PaymentMethod paymentMethod = paymentMethodRepository.findByName(formaDePagamento)
                .orElseThrow(() -> new EntityNotFoundException("Payment method not found."));

        Category category = categoryRepository.findByName(categoria)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));


        Transaction transaction = Transaction.builder()
                .description(description)
                .value(value)
                .paymentMethod(paymentMethod)
                .category(category)
                .subcategory(null)
                .date(LocalDate.now())
                .build();

        transactionRepository.save(transaction);

        ReplyKeyboardRemove removeKeyboard = new ReplyKeyboardRemove(true, true);

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Gasto registrado com sucesso.")
                .replyMarkup(removeKeyboard)
                .build();

        sendMsg(message);
    }

    private void sendMsg(SendMessage message) {
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new TelegramException("Erro no bot.");
        }
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            String mensagem = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (!inscritosTelegramBotRepository.existsByChatId(chatId)) {
                handleNewUser(chatId, mensagem);
                return;
            }

            userStates.putIfAbsent(chatId, new States());

            if (mensagem.equals("/start")){
                sendMessage(chatId, "Bem-vindo! Como posso ajudar você hoje?");
            } else if (mensagem.equals("/novo_gasto")) {
                sendForceReplyMessage(chatId, "Por favor, escreva a descrição do gasto.");
                userStates.get(chatId).setCurrentState(States.State.AGUARDANDO_DESCRICAO);
            } else if (update.getMessage().isReply()) {
                String msgOriginal = update.getMessage().getReplyToMessage().getText();
                processUserReply(chatId, msgOriginal, mensagem);
            } else if (update.hasMessage()){
                processUserReply(chatId, null, mensagem);
            } else {
                SendMessage message = SendMessage.builder()
                        .chatId(chatId)
                        .text("Comando desconhecido. Por favor, use /chamado para iniciar um novo chamado.")
                        .build();
                sendMsg(message);
            }
        } else if (update.hasCallbackQuery()){
            String callbackData = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            processUserReply(chatId, null, callbackData);
        } else {
            Long chatId = update.getMessage().getChatId();
            SendMessage message = SendMessage.builder()
                    .chatId(chatId)
                    .text("Comando desconhecido. Por favor, use /chamado para iniciar um novo chamado.")
                    .build();
            sendMsg(message);
        }
    }
    @Override
    public String getBotToken() {
        return "token do bot vai aqui";
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }
}
