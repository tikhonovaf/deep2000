package ru.atikhonov.deep2000.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.atikhonov.deep2000.backend.model.Demo2000User;
import ru.atikhonov.deep2000.backend.repository.Demo2000UserRepository;
import ru.atikhonov.deep2000.backend.springconfig.Sha512PasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Demo2000UserRepository migrUserRepository;

    private Sha512PasswordEncoder sha512PasswordEncoder = new Sha512PasswordEncoder();

    /**
     * Определение текущего пользователя
     * @return текущий пользователь
     */
    public Demo2000User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        SecurityContextImpl sci = (SecurityContextImpl) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        Demo2000User user;
        if (sci != null && sci.getAuthentication() != null) {
            Optional<Demo2000User> usrOptional = migrUserRepository.findByLogin(sci.getAuthentication().getName());
            if (usrOptional.isPresent()) {
                user = usrOptional.get();
                return user;
            }
        }
        return null;
    }
}