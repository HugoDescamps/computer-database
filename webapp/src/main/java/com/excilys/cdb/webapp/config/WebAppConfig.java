package com.excilys.cdb.webapp.config;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.excilys.cdb.service.config.ServiceConfig;

@Configuration
@EnableWebMvc
@ComponentScan("com.excilys.cdb.webapp")
@Import({ ServiceConfig.class, SecurityConfig.class })
public class WebAppConfig extends WebMvcConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(WebAppConfig.class);

	@Bean
	public ViewResolver internalRessourceViewResolver() {
		logger.info("Initializing view resolver");
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public MessageSource messageSource() {
		logger.info("Initializing message source");
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/i18n/ressources");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		logger.info("Initializing locale resolver");
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale("en"));
		return resolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		logger.info("Initializing locale resolver");
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("locale");
		registry.addInterceptor(interceptor);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		logger.info("Initializing resource handlers");
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		logger.info("Initializing view controllers");
		registry.addRedirectViewController("/", "/dashboard");
	}
}
