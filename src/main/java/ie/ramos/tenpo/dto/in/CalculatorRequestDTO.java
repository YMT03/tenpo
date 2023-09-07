package ie.ramos.tenpo.dto.in;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculatorRequestDTO {
    private @NotNull BigDecimal numberOne;
    private @NotNull BigDecimal numberTwo;
}
