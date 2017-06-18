package com.dreamteam.police;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableJms
@EnableScheduling
@EnableAsync
public class PoliceApplication {

	@Autowired
	ConfigurableApplicationContext context;

	@Autowired
    private ConnectionFactory activeMQConnectionFactory;

	@Bean
	public JmsListenerContainerFactory<?> jmsListenerContainerQueue() {
		DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
		bean.setConnectionFactory(activeMQConnectionFactory);
		return bean;
	}

	@Bean
	public Topic stolenCarTopic() { return new ActiveMQTopic("StolenCarTopic"); }

	@Bean
	JmsListenerContainerFactory<?> myJmsContainerFactory(ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setClientId("dreamteam-police");
		factory.setPubSubDomain(true);
		factory.setConnectionFactory(activeMQConnectionFactory);

		return factory;
	}

	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(4);
		executor.setMaxPoolSize(4);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("RemoteAPILookup-");
		executor.initialize();
		return executor;
	}

	public static void main(String[] args) {
		SpringApplication.run(PoliceApplication.class, args);
	}
}
