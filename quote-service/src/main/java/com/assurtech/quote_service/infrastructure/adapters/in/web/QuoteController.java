package com.assurtech.quote_service.infrastructure.adapters.in.web;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assurtech.quote_service.domain.model.Quote;
import com.assurtech.quote_service.domain.ports.in.CreateQuoteUseCase;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {
    private final CreateQuoteUseCase createQuoteUseCase;

    public QuoteController(CreateQuoteUseCase createQuoteUseCase) {
        this.createQuoteUseCase = createQuoteUseCase;
    }

    @PostMapping
    public Quote createQuote(@RequestBody QuoteRequest req) {
        return createQuoteUseCase.create(req.name(), req.car(), req.age(), req.claims());
    }

    public record QuoteRequest(String name, String car, int age, int claims) {}
    
}
