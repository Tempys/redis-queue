package com.dubovskiy.test;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.Jedis;

@SpringBootApplication
public class Application {


	/*@Bean
	Jedis createJedis(){
		Jedis = new Jedis()
	}
*/



	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	@Bean
	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
	//	container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

		return container;
	}



	@Bean
	StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}

	public static void main(String[] args) throws InterruptedException {

		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
       //  JedisConnectionFactory jedis = ctx.getBean(JedisConnectionFactory.class);
		Jedis jedis = new Jedis("localhost");

		LOGGER.info("Sending message...");
		long startTime = System.currentTimeMillis();
       for(int i = 1; i<=1000000;i++) {
		  // template.convertAndSend("chat", ""+i);
		   jedis.lpush("queue",""+i);
	   }
		long finishTime = System.currentTimeMillis();
		long time = (finishTime - startTime);
		LOGGER.info("finish updated by:" + time);
	}
}
