package org.api.Seguridad;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.Duration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserService userService;

    public static class LoginRequest {
        public String username;
        public String password;
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest req) {
        if (req == null || req.username == null || req.password == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "username y password requeridos"))
                    .build();
        }

        User user = userService.findByUsername(req.username);
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", "Credenciales inválidas"))
                    .build();
        }

        if (!org.mindrot.jbcrypt.BCrypt.checkpw(req.password, user.passwordHash)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("error", "Credenciales inválidas"))
                    .build();
        }

        Set<String> groups = new HashSet<>(user.roles);

        String token = Jwt.issuer("https://apitienda.local")
                .upn(user.username)
                .groups(groups)
                .expiresIn(Duration.ofHours(8).getSeconds())
                .sign();

        return Response.ok(Map.of("token", token)).build();
    }
}
