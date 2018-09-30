package com.guiguchat;

import com.guiguchat.netty.WSServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GuiguchatApplication implements CommandLineRunner {


	@Autowired
	WSServer wsServer;
	public static void main(String[] args) {
		SpringApplication.run(GuiguchatApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		wsServer.start();
	}
}
