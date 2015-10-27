package com.dubovskiy.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import redis.clients.jedis.Jedis;

import java.util.concurrent.atomic.AtomicInteger;


@SpringBootApplication
public class Application {



    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        AtomicInteger integer = new AtomicInteger(0);
        Jedis jedis = new Jedis("localhost");

        while (true){

            String message = jedis.rpoplpush("queue","queue1");
            System.out.println( integer.incrementAndGet()+"    "+message );
            if(message == null){
                break;
            }

         //  System.out.println(message);

        }


    }
}
