package kuit.springbasic.auth;


public record JwtInfo(
        String accessToken, String refreshToken
) {
}
