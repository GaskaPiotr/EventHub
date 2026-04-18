package com.github.gaskapiotr.eventhub;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

@SpringBootTest
class EventHubApplicationTests extends AbstractIntegrationTest {
	ApplicationModules applicationModules = ApplicationModules.of(EventHubApplication.class);

	@Test
	void contextLoads() {
	}

	@Test
	void verifyModulithArchitecture() {
		applicationModules.verify();
	}

	@Test
	void writeDocumentationSnippets() {
		new Documenter(applicationModules)
				.writeModulesAsPlantUml()
				.writeIndividualModulesAsPlantUml();
	}

}
