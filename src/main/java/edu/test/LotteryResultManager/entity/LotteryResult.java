package edu.test.LotteryResultManager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lottery_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotteryResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lottery_name", nullable = false)
    private String lotteryName;

    @Column(name = "draw_number")
    private String drawNumber;

    @Column(name = "draw_date")
    private String drawDate;

    @Column(name = "winning_numbers", length = 500)
    private String winningNumbers;

    @Column(name = "super_number")
    private String superNumber;

    @Column(name = "scraped_at")
    private LocalDateTime scrapedAt;

    @PrePersist
    protected void onCreate() {
        scrapedAt = LocalDateTime.now();
    }
}
