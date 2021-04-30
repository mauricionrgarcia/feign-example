package com.example.feign;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import com.github.tomakehurst.wiremock.WireMockServer;

/**
 * @author mauricionrgarcia
 *
 */
public class WireMockInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
		WireMockServer wireMockServer = new WireMockServer(9099);
		wireMockServer.start();

		configurableApplicationContext.getBeanFactory().registerSingleton("wireMockServer", wireMockServer);

		configurableApplicationContext.addApplicationListener(applicationEvent -> {
			if (applicationEvent instanceof ContextClosedEvent) {
				wireMockServer.stop();
			}
		});

	}
}