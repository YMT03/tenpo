package ie.ramos.tenpo.endpoint;


import ie.ramos.tenpo.dto.in.CalculatorRequestDTO;
import ie.ramos.tenpo.dto.out.CalculatorResponseDTO;
import ie.ramos.tenpo.service.CalculatorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController
@AllArgsConstructor
public class CalculatorEndpoint {

    private final CalculatorService calculatorService;

    @PostMapping("/api/calculator")
    public CalculatorResponseDTO get(@RequestBody @Valid CalculatorRequestDTO request) {
        return buildResponse(calculatorService.getSumWithPercentage(request.getNumberOne(), request.getNumberTwo()));
    }

    private CalculatorResponseDTO buildResponse(BigDecimal result) {
        return CalculatorResponseDTO.builder()
                .result(result)
                .build();
    }
}
