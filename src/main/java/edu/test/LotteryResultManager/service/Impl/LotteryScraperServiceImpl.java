package edu.test.LotteryResultManager.service.Impl;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import edu.test.LotteryResultManager.entity.LotteryResult;
import edu.test.LotteryResultManager.repositroy.LotteryResultRepository;
import edu.test.LotteryResultManager.service.LotteryScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LotteryScraperServiceImpl implements LotteryScraperService {

    private final LotteryResultRepository lotteryResultRepository;

    private static final String GOVISETHA_URL =
            "https://www.nlb.lk/results/govisetha";
    private static final String JAYODA_URL =
            "https://www.nlb.lk/results/jayoda";

    // Scrape Govisetha
    public List<LotteryResult> scrapeGovisetha() throws IOException {
        return scrapeLottery(GOVISETHA_URL, "Govisetha");
    }

    // Scrape Jayoda
    public List<LotteryResult> scrapeJayoda() throws IOException {
        return scrapeLottery(JAYODA_URL, "Jayoda");
    }

    // Scrape Both
    public List<LotteryResult> scrapeAll() throws IOException {
        List<LotteryResult> allResults = new ArrayList<>();
        allResults.addAll(scrapeGovisetha());
        allResults.addAll(scrapeJayoda());
        return allResults;
    }

    // Core Scraping Logic
    private List<LotteryResult> scrapeLottery(
            String url, String lotteryName) throws IOException {
        List<LotteryResult> results = new ArrayList<>();

        try (WebClient webClient = new WebClient()) {

            // WebClient Settings
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setDownloadImages(false);
            webClient.getOptions().setUseInsecureSSL(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setTimeout(15000);

            System.out.println("=== Scraping: " + lotteryName + " ===");

            // Get Page
            HtmlPage page = webClient.getPage(url);

            // Wait for JS
            webClient.waitForBackgroundJavaScript(3000);

            // Get Tables
            List<HtmlTable> tables = page.getByXPath("//table");
            System.out.println("Tables found: " + tables.size());

            for (HtmlTable table : tables) {
                List<HtmlTableRow> rows = table.getRows();

                for (HtmlTableRow row : rows) {
                    List<HtmlTableCell> cols = row.getCells();

                    if (cols.size() >= 2) {
                        LotteryResult result = new LotteryResult();
                        result.setLotteryName(lotteryName);

                        // Col 0 - Draw Number + Date combined
                        String col0 = cols.get(0)
                                .asNormalizedText().trim();
                        String[] col0Lines = col0.split("\n");

                        if (col0Lines.length >= 2) {
                            // First line = Draw Number
                            result.setDrawNumber(
                                    col0Lines[0].trim());
                            // Second line = Date
                            result.setDrawDate(
                                    col0Lines[1].trim());
                        } else {
                            result.setDrawNumber(col0.trim());
                        }

                        // Col 1 - Winning Numbers
                        result.setWinningNumbers(
                                cols.get(1).asNormalizedText().trim());

                        // Col 2 - Super Number
                        if (cols.size() >= 3) {
                            result.setSuperNumber(
                                    cols.get(2).asNormalizedText().trim());
                        }

                        // Skip header rows and empty rows
                        if (result.getDrawNumber() != null
                                && !result.getDrawNumber().isEmpty()
                                && !result.getDrawNumber()
                                .equalsIgnoreCase("draw")
                                && !result.getDrawNumber()
                                .equalsIgnoreCase("no")
                                && !result.getDrawNumber()
                                .equalsIgnoreCase("#")) {

                            // Duplicate check
                            if (!lotteryResultRepository
                                    .existsByDrawNumberAndLotteryName(
                                            result.getDrawNumber(),
                                            result.getLotteryName())) {

                                lotteryResultRepository.save(result);
                                results.add(result);

                                System.out.println(
                                        "Draw: " + result.getDrawNumber()
                                                + " | Date: " + result.getDrawDate()
                                                + " | Numbers: "
                                                + result.getWinningNumbers());
                            }
                        }
                    }
                }
            }

            System.out.println("Total scraped: "
                    + results.size() + " records for " + lotteryName);
        }

        return results;
    }

    // Get All from DB
    public List<LotteryResult> getAllResults() {
        return lotteryResultRepository.findAll();
    }

    // Get by Lottery Name from DB
    public List<LotteryResult> getByLotteryName(String name) {
        return lotteryResultRepository.findByLotteryName(name);
    }
}