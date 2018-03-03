package com.ashu.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hazelcast.HazelcastKeyValueAdapter;
import org.springframework.data.hazelcast.repository.config.EnableHazelcastRepositories;
import org.springframework.data.keyvalue.core.KeyValueOperations;
import org.springframework.data.keyvalue.core.KeyValueTemplate;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.hazelcast.HazelcastLockProvider;
import net.javacrumbs.shedlock.spring.ScheduledLockConfiguration;
import net.javacrumbs.shedlock.spring.ScheduledLockConfigurationBuilder;

@Configuration
@EnableHazelcastRepositories(basePackages = { "com.ashu.repository" })
public class HazelcastConfiguration {

	@Bean
	HazelcastInstance hazelcastInstance() {
		return Hazelcast.newHazelcastInstance();
	}

	@Bean
	public HazelcastKeyValueAdapter hazelcastKeyValueAdapter(HazelcastInstance hazelcastInstance) {
		return new HazelcastKeyValueAdapter(hazelcastInstance);
	}

	@Bean
	public KeyValueOperations keyValueTemplate() {
		return new KeyValueTemplate(new HazelcastKeyValueAdapter(hazelcastInstance()));
	}

	@Bean
	public HazelcastLockProvider lockProvider(HazelcastInstance hazelcastInstance) {
		return new HazelcastLockProvider(hazelcastInstance);
	}

	@Bean
	public ScheduledLockConfiguration taskScheduler(LockProvider lockProvider) {
		return ScheduledLockConfigurationBuilder.withLockProvider(lockProvider).withPoolSize(10)
				.withDefaultLockAtMostFor(Duration.ofMinutes(10)).build();
	}

}