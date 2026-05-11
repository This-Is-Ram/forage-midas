package com.jpmc.midascore;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KafkaTransactionListener {

    private final DatabaseConduit databaseConduit;

    private final RestTemplate restTemplate = new RestTemplate();

    public KafkaTransactionListener(DatabaseConduit databaseConduit) {
        this.databaseConduit = databaseConduit;
    }

    @KafkaListener(
            topics = "${general.kafka-topic}",
            groupId = "midas-group"
    )
    public void listen(Transaction transaction) {

        UserRecord sender =
                databaseConduit.findUserById(transaction.getSenderId());

        UserRecord recipient =
                databaseConduit.findUserById(transaction.getRecipientId());

        if (sender == null || recipient == null) {
            return;
        }

        if (sender.getBalance() < transaction.getAmount()) {
            return;
        }

        Incentive incentive = restTemplate.postForObject(
                "http://localhost:8080/incentive",
                transaction,
                Incentive.class
        );

        float incentiveAmount = 0;

        if (incentive != null) {
            incentiveAmount = incentive.getAmount();
        }

        sender.setBalance(
                sender.getBalance() - transaction.getAmount()
        );

        recipient.setBalance(
                recipient.getBalance()
                        + transaction.getAmount()
                        + incentiveAmount
        );

        databaseConduit.saveUser(sender);
        databaseConduit.saveUser(recipient);

        TransactionRecord record = new TransactionRecord(
                transaction.getSenderId(),
                transaction.getRecipientId(),
                transaction.getAmount(),
                incentiveAmount
        );

        databaseConduit.saveTransaction(record);
        System.out.println(sender.getName() + " : " + sender.getBalance());
        System.out.println(recipient.getName() + " : " + recipient.getBalance());
    }
}