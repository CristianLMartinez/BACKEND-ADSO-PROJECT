package edu.menueasy.adso.infra.security;

import edu.menueasy.adso.domain.role.Role;
import edu.menueasy.adso.domain.user.User;
import edu.menueasy.adso.domain.user.UserDto;
import edu.menueasy.adso.domain.user.UserRepository;
import edu.menueasy.adso.dto.AuthenticationResponse;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JWTService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public AuthenticationResponse register(UserDto userDto){
        User user = User.builder()
                .name(userDto.name())
                .lastname(userDto.lastname())
                .username(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .cellphone(userDto.cellphone())
                .identification(userDto.identification())
                .role(Role.ADMIN)
                .build();

        user = userRepository.save(user);

        String token = jwtService.generateToken(user);
        Date expiresIn = jwtService.extractClaims(token, Claims::getExpiration);

        return new AuthenticationResponse(token, new UserDto(user), expiresIn);
    }

    public AuthenticationResponse authenticate(User request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println("0000000000000000000000000000000000000000000000");
        System.out.println(user);
        String token = jwtService.generateToken(user);

        Date expiresIn = jwtService.extractClaims(token, Claims::getExpiration);

        return new AuthenticationResponse(token, new UserDto(user), expiresIn);
    }

}
