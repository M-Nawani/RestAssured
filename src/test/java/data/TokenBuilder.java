package data;


public class TokenBuilder {

    public TokenCreds tokenbuilder(){
        return TokenCreds.builder()
                .username("admin")
                .password("password123")
                .build();
    }
}
