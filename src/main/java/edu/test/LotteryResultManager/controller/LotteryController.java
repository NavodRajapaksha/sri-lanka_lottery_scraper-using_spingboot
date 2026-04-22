package edu.test.LotteryResultManager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/lottery")
@CrossOrigin()
@RequiredArgsConstructor
public class LotteryController {


    private final LotteryScraperService lotteryScraperService;

    // GET - All results from DB
    @GetMapping("/all")
    public ResponseEntity<List<LotteryResult>> getAllResults() {
        List<LotteryResult> results = lotteryScraperService.getAllResults();
        return ResponseEntity.ok(results);
    }

    // GET - Scrape Govisetha & Save to DB
    @GetMapping("/scrape/govisetha")
    public ResponseEntity<?> scrapeGovisetha() {
        try {
            List<LotteryResult> results = lotteryScraperService.scrapeGovisetha();
            return ResponseEntity.ok(results);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error scraping Govisetha: " + e.getMessage());
        }
    }

    // GET - Scrape Jayoda & Save to DB
    @GetMapping("/scrape/jayoda")
    public ResponseEntity<?> scrapeJayoda() {
        try {
            List<LotteryResult> results = lotteryScraperService.scrapeJayoda();
            return ResponseEntity.ok(results);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error scraping Jayoda: " + e.getMessage());
        }
    }

    // GET - Scrape All & Save to DB
    @GetMapping("/scrape/all")
    public ResponseEntity<?> scrapeAll() {
        try {
            List<LotteryResult> results = lotteryScraperService.scrapeAll();
            return ResponseEntity.ok(results);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error scraping all lotteries: " + e.getMessage());
        }
    }

    // GET - Results by Lottery Name
    @GetMapping("/by-name/{name}")
    public ResponseEntity<List<LotteryResult>> getByName(@PathVariable String name) {
        List<LotteryResult> results = lotteryScraperService.getByLotteryName(name);
        return ResponseEntity.ok(results);
    }
}
