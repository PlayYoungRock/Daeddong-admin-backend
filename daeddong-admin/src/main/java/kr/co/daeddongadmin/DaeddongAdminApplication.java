package kr.co.daeddongadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableAspectJAutoProxy
public class DaeddongAdminApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DaeddongAdminApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DaeddongAdminApplication.class);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("https://localhost:3001", "https://daeddong-admin-dev.web.app")
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowedHeaders("Authorization", "Content-Type") // 필요한 헤더만 허용하도록 수정
						.exposedHeaders("Authorization") // 클라이언트에 노출할 헤더 추가
						.allowCredentials(true) // 인증 정보 (쿠키, 인증 헤더 등)을 허용
						.maxAge(3600); // preflight 요청 결과를 캐싱할 시간 설정 (초 단위)
			}
		};
	}
}

