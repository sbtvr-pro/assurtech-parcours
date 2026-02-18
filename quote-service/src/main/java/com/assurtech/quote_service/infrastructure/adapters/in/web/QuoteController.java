package com.assurtech.quote_service.infrastructure.adapters.in.web;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assurtech.quote_service.domain.model.Quote;
import com.assurtech.quote_service.domain.ports.in.CreateQuoteUseCase;
import com.assurtech.quote_service.domain.ports.in.ListQuotesUseCase;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {
    private final CreateQuoteUseCase createQuoteUseCase;
    private final ListQuotesUseCase listQuotesUseCase;

    public QuoteController(CreateQuoteUseCase createQuoteUseCase, ListQuotesUseCase listQuotesUseCase) {
        this.createQuoteUseCase = createQuoteUseCase;
        this.listQuotesUseCase = listQuotesUseCase;
    }

    @PostMapping
    public ResponseEntity<Quote> createQuote(@RequestBody QuoteRequest req) {
        Quote createdQuote = createQuoteUseCase.create(req.name(), req.car(), req.age(), req.claims());
        return new ResponseEntity<>(createdQuote,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Quote>> getAllQuotes() {
        return ResponseEntity.ok(listQuotesUseCase.execute());
    }

    public record QuoteRequest(String name, String car, int age, int claims) {}
    
}
