package com.Stans;

import com.Stans.rabbitmq.Receive;

public class DatabaseStans {
    public static Receive receive = new Receive();
    public static void main(String[] args) {
        try{
            System.out.println(" [*] Waiting for messages..");
            receive.receiveMessagesLogin();
            receive.receiveMessagesRegis("RegisterUser","MessageRegister");
            receive.receiveRequest("ForgetPass","MessagePass");
            receive.receiveMessagesReset("ResetPass","Reseted");
        }catch (Exception e){
          e.printStackTrace();
        }
    }
}
