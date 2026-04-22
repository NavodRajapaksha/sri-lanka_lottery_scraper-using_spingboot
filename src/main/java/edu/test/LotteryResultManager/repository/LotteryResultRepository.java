package edu.test.LotteryResultManager.repository;

import edu.test.LotteryResultManager.entity.LotteryResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LotteryResultRepository extends JpaRepository<LotteryResult,Integer> {

    // Find by lottery name
    List<LotteryResult> findByLotteryName(String lotteryName);

    // Find by draw number
    List<LotteryResult> findByDrawNumber(String drawNumber);
}
