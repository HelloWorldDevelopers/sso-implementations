package com.javatechie.spring.cloud.security.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

import com.nimbusds.oauth2.sdk.AuthorizationRequest;
import com.nimbusds.oauth2.sdk.ResponseType;
import com.nimbusds.oauth2.sdk.Scope;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.util.URLUtils;

public class AuthCode {
	public String getCode() throws URISyntaxException {
		 AuthorizationRequest request = new AuthorizationRequest.Builder(
		            new ResponseType("code"), new ClientID("Ov23lilloISxjfTykY4L"))
		            .endpointURI(URI.create("https://github.com/login/oauth/authorize"))
		            .redirectionURI(new URI("http://172.20.1.174/sso/redirect"))
		            .scope(new Scope("user", "repo"))  // Adjust scopes as needed
		            .state(new State())
		            .build();

 		        String authRequestUri = request.toURI().toString();
		        System.err.println(authRequestUri);
		        return "redirect:" + authRequestUri;
	}
		public String get(String receivedAuthCode) throws IOException, InterruptedException    {
//		String clientId = "Ov23lilloISxjfTykY4L";  // Replace with your actual GitHub client ID
//
//        // Create an authorization request
//        AuthorizationRequest authRequest = new AuthorizationRequest.Builder(
//            new ResponseType("code"), new ClientID(clientId))
//            .endpointURI(URI.create("https://github.com/login/oauth/authorize"))
//            .scope(new Scope("user", "repo"))  // Adjust scopes as needed
//            .state(new State())  // Generate a random state for CSRF protection
//            .build();
//
//        // Convert the request to a URI to redirect the user
//        String authRequestUri = authRequest.toURI().toString();
//
//        // Redirect the user to the GitHub authorization endpoint
//        System.out.println("Redirect the user to: " + authRequestUri);
//
//        // After redirecting the user, you'll receive an authorization code from GitHub
//        // For this example, we'll simulate receiving an authorization code
//      //  String receivedAuthCode = "auth-code-received";  // Replace with the actual authorization code received
//
//        // Step 2: Exchange the authorization code for an access token
//
//        // Your GitHub client secret
//        String clientSecret = "78663daad9dc20ce278b9615288a18c42755f6fb";  // Replace with your actual GitHub client secret
//
//        // Create a token request using the received authorization code
//        TokenRequest tokenRequest = new TokenRequest(
//            URI.create("https://github.com/login/oauth/access_token"),
//            new ClientSecretBasic(new ClientID(clientId), new Secret(clientSecret)),
//            new AuthorizationCodeGrant(new AuthorizationCode(receivedAuthCode), URI.create("http://localhost:8080/redirect"))
//        );
//
//        // Send the request and parse the token response
//        TokenResponse tokenResponse = TokenResponse.parse(tokenRequest.toHTTPRequest().send());
//
//        if (tokenResponse.indicatesSuccess()) {
//            AccessToken accessToken = ((AccessTokenResponse) tokenResponse).getTokens().getAccessToken();
//            System.out.println("Access Token: " + accessToken.getValue());
//        } else {
//            TokenErrorResponse errorResponse = (TokenErrorResponse) tokenResponse;
//            System.out.println("Error: " + errorResponse.getErrorObject().getDescription());
//        }
// 

	String clientId = "Ov23lilloISxjfTykY4L";  // Replace with your actual GitHub client ID
    String clientSecret = "78663daad9dc20ce278b9615288a18c42755f6fb";  // Replace with your actual GitHub client secret

    // The authorization code received from GitHub
   // String receivedAuthCode = "auth-code-received";  // Replace with the actual authorization code received

    // Create the token request parameters
    String requestBody = "client_id=" + clientId +
            "&client_secret=" + clientSecret +
            "&code=" + receivedAuthCode +
            "&redirect_uri=" + "http://172.20.1.174/sso/redirect";  // Replace with your actual redirect URI

    // Send the token request
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://github.com/login/oauth/access_token"))
            .header("Accept", "application/json")  // GitHub can also return JSON if requested explicitly
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    // Check if the response is successful
    if (response.statusCode() == 200) {
        // Parse the response body (which should be application/x-www-form-urlencoded)
 
        JSONObject jsonObject = new JSONObject(response.body());

        // Extract access token and token type
        String accessToken = jsonObject.getString("access_token");
        String tokenType = jsonObject.getString("token_type");

        if (accessToken != null) {
            System.out.println("Access Token: " + accessToken);
            System.out.println("Token Type: " + tokenType);
            
 
            HttpClient client1 = HttpClient.newHttpClient();
            HttpRequest request1 = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.github.com/user"))
                    .header("Authorization", "Bearer " + accessToken)
                    .header("Accept", "application/vnd.github.v3+json")
                    .build();

            HttpResponse<String> response1 = client1.send(request1, HttpResponse.BodyHandlers.ofString());

            if (response1.statusCode() == 200) {
                
            	JSONObject profileObject = new JSONObject(response1.body());

                 // Extract and print all fields
                 String login = profileObject.getString("login");
//                 int id = profileObject.getInt("id");
//                 String nodeId = profileObject.getString("node_id");
//                 String avatarUrl = profileObject.getString("avatar_url");
//                 String gravatarId = profileObject.getString("gravatar_id");
//                 String url = profileObject.getString("url");
//                 String htmlUrl = profileObject.getString("html_url");
//                 String followersUrl = profileObject.getString("followers_url");
//                 String followingUrl = profileObject.getString("following_url");
//                 String gistsUrl = profileObject.getString("gists_url");
//                 String starredUrl = profileObject.getString("starred_url");
//                 String subscriptionsUrl = profileObject.getString("subscriptions_url");
//                 String organizationsUrl = profileObject.getString("organizations_url");
//                 String reposUrl = profileObject.getString("repos_url");
//                 String eventsUrl = profileObject.getString("events_url");
//                 String receivedEventsUrl = profileObject.getString("received_events_url");
//                 String type = profileObject.getString("type");
//                 boolean siteAdmin = profileObject.getBoolean("site_admin");
//                 String name = profileObject.optString("name");
//                 String company = profileObject.optString("company");
//                 String blog = profileObject.optString("blog");
//                 String location = profileObject.optString("location");
//                 String email = profileObject.optString("email");
//                 String hireable = profileObject.optString("hireable");
//                 String bio = profileObject.optString("bio");
//                 String twitterUsername = profileObject.optString("twitter_username");
//                 String notificationEmail = profileObject.optString("notification_email");
//                 int publicRepos = profileObject.getInt("public_repos");
//                 int publicGists = profileObject.getInt("public_gists");
//                 int followers = profileObject.getInt("followers");
//                 int following = profileObject.getInt("following");
//                 String createdAt = profileObject.getString("created_at");
//                 String updatedAt = profileObject.getString("updated_at");
//                 int privateGists = profileObject.getInt("private_gists");
//                 int totalPrivateRepos = profileObject.getInt("total_private_repos");
//                 int ownedPrivateRepos = profileObject.getInt("owned_private_repos");
//                 int diskUsage = profileObject.getInt("disk_usage");
//                 int collaborators = profileObject.getInt("collaborators");
//                 boolean twoFactorAuthentication = profileObject.getBoolean("two_factor_authentication");
//                 JSONObject plan = profileObject.getJSONObject("plan");
//                 String planName = plan.getString("name");
//                 long planSpace = plan.getLong("space");
//                 int planCollaborators = plan.getInt("collaborators");
//                 int planPrivateRepos = plan.getInt("private_repos");
                 System.out.println("User Profile: " + response1.body());
            	 return login ;
            } else {
                System.out.println("Failed to retrieve user profile");
                System.out.println("Status Code: " + response1.statusCode());
                System.out.println("Response Body: " + response1.body());
            }
        }
        } else {
            System.out.println("Failed to retrieve access token");
        }
	return null;
    }  


	}

