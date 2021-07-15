package com.Stans.rabbitmq;

import com.Stans.model.User;
import com.Stans.repository.UserRepo;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.concurrent.TimeoutException;

public class Receive {

    Send sendapi = new Send();

    public void receiveMessagesLogin() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare("LoginUser", true, false, false, null);
        channel.basicQos(1);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                User user = new Gson().fromJson(message, User.class);
                String username = user.getUsername();
                String pass = user.getPassword();
                System.out.println(username);
                User user1 = UserRepo.validateUser(username,pass);
                if(user1 != null){
                    sendapi.sendToRestApi(new Gson().toJson(user1),"MessageFromUSer");
                }else{
                    sendapi.sendToRestApi("Error","MessageFromUSer");
                }
            } catch (TimeoutException e) {
                e.printStackTrace();

            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume("LoginUser", false, deliverCallback, consumerTag -> { });
        delay();
    }
    public void receiveMessagesRegis(String QueueReceive, String QueueSend) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QueueReceive, true, false, false, null);
        channel.basicQos(1);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                User user = new Gson().fromJson(message, User.class);
                String username = user.getUsername();
                String pass = user.getPassword();
                System.out.println(username);
                User user1 = UserRepo.validateRegis(username,pass);
                if(user1 != null){
                    sendapi.sendToRestApi("Error",QueueSend);
                }else{
                    sendapi.sendToRestApi(new Gson().toJson(user1),QueueSend);
                    UserRepo repo = new UserRepo();
                    repo.regisUser(user);
                }
            } catch (TimeoutException e) {
                e.printStackTrace();

            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QueueReceive, false, deliverCallback, consumerTag -> { });
        delay();
    }
    public void receiveRequest(String QueueReceive, String QueueSend) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QueueReceive, true, false, false, null);
        channel.basicQos(1);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                User user = new Gson().fromJson(message, User.class);
                String username = user.getUsername();
                String pass = user.getPassword();
                System.out.println(username);
                User user1 = UserRepo.validateRegis(username,pass);
                if(user1 != null){
                    sendapi.sendToRestApi(new Gson().toJson(user1),QueueSend);
                }else{
                    sendapi.sendToRestApi("Error",QueueSend);
                }
            } catch (TimeoutException e) {
                e.printStackTrace();

            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QueueReceive, false, deliverCallback, consumerTag -> { });
        delay();
    }
    public void receiveMessagesReset(String QueueReceive, String QueueSend) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QueueReceive, true, false, false, null);
        channel.basicQos(1);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                User user = new Gson().fromJson(message, User.class);
                String username = user.getUsername();
                String pass = user.getPassword();
                System.out.println(username);
                User user1 = UserRepo.validateRegis(username,pass);
                if(user1 == null){
                    sendapi.sendToRestApi("Error",QueueSend);
                }else{
                    sendapi.sendToRestApi("Success",QueueSend);
                    UserRepo repo = new UserRepo();
                    repo.resetPass(user);
                }
            } catch (TimeoutException e) {
                e.printStackTrace();

            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QueueReceive, false, deliverCallback, consumerTag -> { });
        delay();
    }
    private static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
