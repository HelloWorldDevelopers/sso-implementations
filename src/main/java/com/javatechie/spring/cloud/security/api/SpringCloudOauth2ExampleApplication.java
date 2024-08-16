package com.javatechie.spring.cloud.security.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.nimbusds.oauth2.sdk.AuthorizationRequest;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;

@SpringBootApplication
@EnableOAuth2Sso
@Controller
public class SpringCloudOauth2ExampleApplication {
	
	@Autowired
	EmployeeMasterRepo employeeMasterRepo;
	
//	@GetMapping("/")
//	public String message(Principal principal) throws ParseException, IOException {
//		 AuthCode ac=new AuthCode();
//		 
//		return "Hi "+principal.getName()+" welcome to SpringCloudOauth2ExampleApplication";
//	}
	
	 @GetMapping("/")
	    public RedirectView message() throws URISyntaxException {
	        AuthorizationRequest request = new AuthorizationRequest.Builder(
	                new ResponseType("code"), new ClientID("Ov23lilloISxjfTykY4L"))
	                .endpointURI(URI.create("https://github.com/login/oauth/authorize"))
	                .redirectionURI(new URI("http://172.20.1.174/sso/redirect"))
	                .scope(new Scope("user", "repo"))  // Adjust scopes as needed
	                .state(new State())
	                .build();

	        // Generate the authorization URI
	        String authRequestUri = request.toURI().toString();

	        // Redirect the user to the GitHub authorization endpoint
	        return new RedirectView(authRequestUri);
	    }

	@GetMapping("/code/{code}")
	public String message(@PathVariable("code") String code) throws ParseException, Exception, InterruptedException {
		 AuthCode ac=new AuthCode();
		 
		 ac.getCode();
 		return "Hi  welcome to SpringCloudOauth2ExampleApplication";
	}
	
	
	@GetMapping("/redirect")
	public String redirect(@RequestParam("code") String code, Model model) throws IOException, InterruptedException {
	    AuthCode ac = new AuthCode();
	    EmployeeMaster findByEmailId = employeeMasterRepo.findByEmailId(ac.get(code));
	    model.addAttribute("stu", findByEmailId);
	    return "dashboard";
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudOauth2ExampleApplication.class, args);
	}

}
