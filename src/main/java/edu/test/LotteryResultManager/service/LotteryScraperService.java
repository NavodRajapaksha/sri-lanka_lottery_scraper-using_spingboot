package edu.test.LotteryResultManager.service;

import edu.test.LotteryResultManager.entity.LotteryResult;

import java.io.IOException;
import java.util.List;

public interface LotteryScraperService {
    List<LotteryResult> scrapeGovisetha() throws IOException;

    List<LotteryResult> scrapeJayoda() throws IOException;

    List<LotteryResult> scrapeAll() throws IOException;

    List<LotteryResult> getAllResults();

    List<LotteryResult> getByLotteryName(String name);
}
