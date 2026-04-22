package edu.test.LotteryResultManager.repositoy;

import edu.test.LotteryResultManager.entity.LotteryResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface LotteryResultRepository extends JpaRepository<LotteryResult , Integer> {
    // Find by lottery name
    List<LotteryResult> findByLotteryName(String lotteryName);

    // Find by draw number
    List<LotteryResult> findByDrawNumber(String drawNumber);
}
