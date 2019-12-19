package cl.duoc.sigloxxi.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.GenericFilterBean;

import cl.duoc.sigloxxi.controller.AuthController;
import cl.duoc.sigloxxi.entity.Permiso;
import cl.duoc.sigloxxi.repository.PermisoRepository;
import cl.duoc.sigloxxi.util.SpringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class JWTFilter extends GenericFilterBean {

    private String url;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORITIES_KEY = "roles";

    
	PermisoRepository permisoRepository;
    
    public JWTFilter(String url) {
        this.setUrl(url);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
        FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        AntPathMatcher urlMatch = new AntPathMatcher();
        if (urlMatch.match(this.url, request.getRequestURI()
            .substring(request.getContextPath().length()))) {
            String authHeader = request.getHeader(AUTHORIZATION_HEADER);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                ((HttpServletResponse) res).sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Error Token -> Usuario inválido, debe iniciar sesión. -> Token vacío");
            } else {
                try {
                    String token = authHeader.substring(7);
                    Claims claims = getHeaders(res, request);

                    if (new Date().after(claims.get("iat", Date.class))) {
                        ((HttpServletResponse) res).sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            "La sesión ha caducado, debe iniciar sesión nuevamente.");
                    }
                    
                    Long idUsuario =
                        Long.parseLong(claims.get("sub").toString());
                    String rutaIngreso = request.getRequestURI()
                        .substring(request.getContextPath().length());
                    String[] rutaIngresoArray = rutaIngreso.split("/");
                    String rutaIngresoFormattedFinal =
                        rutaIngresoArray[0] + "/" + rutaIngresoArray[1] + "/"
                            + rutaIngresoArray[2] + "/" + rutaIngresoArray[3];

                    AuthController authController =
                            (AuthController) SpringUtils.ctx
                                .getBean(AuthController.class);

                    System.out.println("idUsuario : " +  idUsuario);
                    System.out.println("rutaIngresoFormattedFinal : " +  rutaIngresoFormattedFinal);
                    
                    
                    Permiso tienePermiso = authController.validaPermiso(idUsuario, rutaIngresoFormattedFinal);
                    
                    if (tienePermiso == null) {
                        ((HttpServletResponse) res).sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            "Error de Permisos -> Los roles que usted posee no tienen permisos para acceder a esta función");
                    }else {
                        request.setAttribute("claims", claims);
                        SecurityContextHolder.getContext()
                            .setAuthentication(getAuthentication(claims));
                        filterChain.doFilter(req, res);                    	
                    }

                } catch (SignatureException e) {
                    ((HttpServletResponse) res).sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Error Token -> Usuario inválido, debe iniciar sesión. -> Error token");
                }
            }
        } else {
            filterChain.doFilter(req, res);
        }

    }

    public Authentication getAuthentication(Claims claims) {
        List<SimpleGrantedAuthority> authorities =
            new ArrayList<SimpleGrantedAuthority>();

        authorities
            .add(new SimpleGrantedAuthority("" + claims.get(AUTHORITIES_KEY)));

        User principal = new User(claims.getSubject(), "", authorities);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(
                principal, "", authorities);
        return usernamePasswordAuthenticationToken;
    }

    public static Claims getHeaders(ServletResponse res, ServletRequest req)
        throws IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        String token = authHeader.substring(7);

        Claims claims = Jwts.parser().setSigningKey("secret")
            .parseClaimsJws(token).getBody();
        return claims;
    }
}
