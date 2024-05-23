package com.ahmet.controller;

import com.ahmet.dto.request.DoLoginRequestDto;
import com.ahmet.dto.request.RegisterRequestDto;
import com.ahmet.exception.AuthServiceException;
import com.ahmet.exception.EErrorType;
import com.ahmet.repository.entity.Auth;
import com.ahmet.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.ahmet.constants.EndPoints.*;

@RestController
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<Auth> register(@RequestBody @Valid RegisterRequestDto dto) {
        if(!dto.getPassword().equals(dto.getRepassword())) { // eşleşmiyorsa (password ve repassword)
            throw new AuthServiceException(EErrorType.REGISTER_ERROR_PASSWORD_MISMATCH);
        }
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping(DOLOGIN)
    public ResponseEntity<String> doLogin(@RequestBody DoLoginRequestDto dto) {
        return ResponseEntity.ok(authService.doLogin(dto));
    }

    @GetMapping(GETALL)
    public ResponseEntity<List<Auth>> findAll(String token) {
        return ResponseEntity.ok(authService.findAll(token));
    }

    @GetMapping("/message")
    public ResponseEntity<String> getMessage() {
        return ResponseEntity.ok("Genel bir mesajjj");
    }
}
