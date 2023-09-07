package ie.ramos.tenpo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculatorRequestDTO {
    private @NotNull BigDecimal numberOne;
    private @NotNull BigDecimal numberTwo;
}
