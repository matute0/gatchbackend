package org.example.gatchbackend.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class LogoutList {
    private List<String> logoutList = new ArrayList<>();
}
