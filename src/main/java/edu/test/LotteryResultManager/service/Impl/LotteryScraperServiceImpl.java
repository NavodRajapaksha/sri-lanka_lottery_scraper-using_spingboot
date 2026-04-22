package edu.test.LotteryResultManager.service.Impl;

import edu.test.LotteryResultManager.entity.LotteryResult;
import edu.test.LotteryResultManager.repositoy.LotteryResultRepository;
import edu.test.LotteryResultManager.service.LotteryScraperService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LotteryScraperServiceImpl implements LotteryScraperService {

    private final LotteryResultRepository lotteryResultRepository;

    private static final String NLB_BASE_URL = "https://www.nlb.lk/results/";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";

    // Scrape Govisetha Lottery
    public List<LotteryResult> scrapeGovisetha() throws IOException {
        return scrapeLottery("govisetha", "Govisetha");
    }

    // Scrape Jayoda Lottery
    public List<LotteryResult> scrapeJayoda() throws IOException {
        return scrapeLottery("jayoda", "Jayoda");
    }

    // Scrape Both Lotteries
    public List<LotteryResult> scrapeAll() throws IOException {
        List<LotteryResult> allResults = new ArrayList<>();
        allResults.addAll(scrapeGovisetha());
        allResults.addAll(scrapeJayoda());
        return allResults;
    }

    // Core Scraping Logic
    private List<LotteryResult> scrapeLottery(String lotteryPath, String lotteryName) throws IOException {
        List<LotteryResult> results = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(NLB_BASE_URL + lotteryPath)
                    .userAgent(USER_AGENT)
                    .timeout(10000)
                    .get();

            // Print HTML to console for debugging
            System.out.println("=== Scraping: " + lotteryName + " ===");

            // Try to find result table
            Elements tables = doc.select("table");

            for (Element table : tables) {
                Elements rows = table.select("tr");

                for (Element row : rows) {
                    Elements cols = row.select("td");

                    if (cols.size() >= 2) {
                        LotteryResult result = new LotteryResult();
                        result.setLotteryName(lotteryName);

                        // Extract data based on column count
                        if (cols.size() >= 4) {
                            result.setDrawNumber(cols.get(0).text().trim());
                            result.setDrawDate(cols.get(1).text().trim());
                            result.setWinningNumbers(cols.get(2).text().trim());
                            result.setSuperNumber(cols.get(3).text().trim());
                        } else if (cols.size() >= 3) {
                            result.setDrawNumber(cols.get(0).text().trim());
                            result.setDrawDate(cols.get(1).text().trim());
                            result.setWinningNumbers(cols.get(2).text().trim());
                        } else {
                            result.setDrawNumber(cols.get(0).text().trim());
                            result.setWinningNumbers(cols.get(1).text().trim());
                        }

                        // Skip empty rows
                        if (!result.getDrawNumber().isEmpty()) {
                            // Save to DB
                            lotteryResultRepository.save(result);
                            results.add(result);

                            // Print to console (Q2 requirement)
                            System.out.println("Draw: " + result.getDrawNumber()
                                    + " | Date: " + result.getDrawDate()
                                    + " | Numbers: " + result.getWinningNumbers());
                        }
                    }
                }
            }

            System.out.println("Total scraped: " + results.size() + " records for " + lotteryName);

        } catch (IOException e) {
            System.err.println("Error scraping " + lotteryName + ": " + e.getMessage());
            throw e;
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
