package pl.pjatk.jaz_s30576_nbp.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pjatk.jaz_s30576_nbp.service.CurrencyService;

import java.time.LocalDate;

@RestController
@RequestMapping("/calculate")
@OpenAPIDefinition(info = @Info(
        title = "Currency Service API Documentation",
        description = "This is the documentation for the sample currency service project with REST API"))
@Tag(name = "Currency Controler", description = "Basic endpoints for customer")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Operation(summary = "Get average currency rate", description = "Returns average exchange rate for a currency in given date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Average rate successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Bad request - invalid parameters", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found - no data for given parameters", content = @Content)
    })
    @GetMapping("/{currency}")
    public ResponseEntity<String> calculate(@PathVariable String currency, @RequestParam(required = true) LocalDate startDate, @RequestParam(required = true) LocalDate endDate) {
        return ResponseEntity.ok(currencyService.calculateRate(currency, startDate, endDate));
    }
}