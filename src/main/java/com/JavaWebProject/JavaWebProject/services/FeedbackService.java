/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.JavaWebProject.JavaWebProject.services;

import com.JavaWebProject.JavaWebProject.models.Feedback;
import com.JavaWebProject.JavaWebProject.repositories.FeedbackRepository;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    private static final List<String> positiveKeywords = Arrays.asList(
    "excellent", "outstanding", "wonderful", "fantastic", "awesome", "remarkable","easy","love",
    "superb", "impressive", "brilliant", "delightful", "splendid", "stellar","great","good",
    "exceptional", "ideal", "amazing", "sensational", "remarkable", "pleasing",
    "phenomenal", "fabulous", "thrilling", "spectacular", "terrific", "perfect",
    "first-class", "top-notch", "high-quality", "exceptional", "commendable",
    "satisfactory", "pleasurable", "helpful", "reliable", "effective", "efficient",
    "friendly", "courteous", "professional", "responsive", "attentive", "beneficial",
    "valuable", "innovative", "creative", "imaginative", "resourceful", "adaptive",
    "trustworthy", "dependable", "consistent", "dependable", "convenient", "streamlined",
    "time-saving", "user-friendly", "intuitive", "accessible", "well-designed", "well-organized"
);

private static final List<String> negativeKeywords = Arrays.asList(
    "terrible", "awful", "horrendous", "dreadful", "atrocious", "disastrous","bad",
    "appalling", "abysmal", "deplorable", "unacceptable", "disappointing", "unsatisfactory",
    "inferior", "subpar", "poor", "inadequate", "deficient", "unreliable", "untrustworthy",
    "ineffective", "inefficient", "substandard", "faulty", "unusable", "displeasing",
    "unhelpful", "unresponsive", "inattentive", "unprofessional", "unfriendly", "unpleasant",
    "annoying", "irritating", "frustrating", "aggravating", "troublesome", "cumbersome",
    "complicated", "confusing", "difficult", "challenging", "problematic", "hassle",
    "disruptive", "time-consuming", "inconvenient", "cumbersome", "clumsy", "awkward",
    "lacking", "limited", "restricted", "obsolete", "outdated", "deprecated", "obsolete"
);


    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public void save(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    public Feedback findById(int id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public void deleteById(int id) {
        feedbackRepository.deleteById(id);
    }

    public int getNewFeedbackByDay(LocalDate date) {
        return feedbackRepository.countByFeedbackDate(date);
    }

    public int getNumFeedbackGapByDay(LocalDate date) {
        return getNewFeedbackByDay(date) - getNewFeedbackByDay(date.minusDays(1));
    }

    public int getNumPositiveFeedbackGapByDay(LocalDate date) {
        int currentPositiveCount = countPositiveFeedbackByDate(date);
        int previousPositiveCount = countPositiveFeedbackByDate(date.minusDays(1));

        // Handle potential negative gap (avoid division by zero)
        return Math.max(currentPositiveCount - previousPositiveCount, 0);
    }

    public int countPositiveFeedbackByDate(LocalDate date) {
        int count = 0;
        List<Feedback> feedbackList = feedbackRepository.findByFeedbackDate(date);
        for (Feedback feedback : feedbackList) {
            String feedbackDetails = feedback.getFeedbackDetails();
            int positiveKeywordCount = countKeywordOccurrences(positiveKeywords, feedbackDetails);
            int negativeKeywordCount = countKeywordOccurrences(negativeKeywords, feedbackDetails);

            if (positiveKeywordCount > 0 && negativeKeywordCount == 0) {
                count++;
            }
        }
        return count;
    }

    public float getGapPercentFeedbackByDay(LocalDate date) {
        float currentValue = getNewFeedbackByDay(date);
        float oldValue = getNewFeedbackByDay(date.minusDays(1));
        if (oldValue == 0) {
            if (currentValue == 0) {
                return 0;
            }
            return 100;
        }
        return ((currentValue / oldValue) - 1) * 100;
    }

    public int getNewFeedbackByMonth(Month month) {
        int year = LocalDate.now().getYear();
        if (month.getValue() > LocalDate.now().getMonthValue()) {
            year--;
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();
        return feedbackRepository.countByFeedbackDateBetween(startDate, endDate);
    }

    public int getNewFeedbackByYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        return feedbackRepository.countByFeedbackDateBetween(startDate, endDate);
    }

    public int countPositiveFeedback() {
        int count = 0;
        for (Feedback feedback : feedbackRepository.findAll()) {
            String feedbackDetails = feedback.getFeedbackDetails();
            int positiveKeywordCount = countKeywordOccurrences(positiveKeywords, feedbackDetails);
            int negativeKeywordCount = countKeywordOccurrences(negativeKeywords, feedbackDetails);

            if (positiveKeywordCount > 0 && negativeKeywordCount == 0) {
                count++;
            }
        }
        return count;
    }

    public int countNegativeFeedback() {
        int count = 0;
        for (Feedback feedback : feedbackRepository.findAll()) {
            String feedbackDetails = feedback.getFeedbackDetails();
            int positiveKeywordCount = countKeywordOccurrences(positiveKeywords, feedbackDetails);
            int negativeKeywordCount = countKeywordOccurrences(negativeKeywords, feedbackDetails);

            if (negativeKeywordCount > 0 && positiveKeywordCount == 0) {
                count++;
            }
        }
        return count;
    }

    private int countKeywordOccurrences(List<String> keywords, String text) {
        int count = 0;
        String lowerCaseText = text.toLowerCase(); // Chuyển đoạn văn bản về chữ thường
        for (String keyword : keywords) {
            String lowerCaseKeyword = keyword.toLowerCase(); // Chuyển từ khóa về chữ thường
            if (lowerCaseText.contains(lowerCaseKeyword)) {
                count++;
            }
        }
        return count;
    }

    public int getNumNegativeFeedbackGapByDay(LocalDate date) {
        int currentNegativeCount = countNegativeFeedbackByDate(date);
        int previousNegativeCount = countNegativeFeedbackByDate(date.minusDays(1));

        // Handle potential negative gap (avoid division by zero)
        return Math.max(currentNegativeCount - previousNegativeCount, 0);
    }

    private int countNegativeFeedbackByDate(LocalDate date) {
        int count = 0;
        for (Feedback feedback : feedbackRepository.findByFeedbackDate(date)) { // Use findByDate for specific date
            String feedbackDetails = feedback.getFeedbackDetails();
            int positiveKeywordCount = countKeywordOccurrences(positiveKeywords, feedbackDetails);
            int negativeKeywordCount = countKeywordOccurrences(negativeKeywords, feedbackDetails);

            if (negativeKeywordCount > 0 && positiveKeywordCount == 0) {
                count++;
            }
        }
        return count;
    }

    private LocalDate convertToLocalDate(Date dateToConvert) {
        if (dateToConvert instanceof java.sql.Date) {
            return ((java.sql.Date) dateToConvert).toLocalDate();
        } else {
            return dateToConvert.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
    }

    public int countNewCustomerFeedback(LocalDate date) {
        int count = 0;
        Set<String> existingEmails = new HashSet<>();

        // Load all feedbacks before the given date to populate existing emails
        List<Feedback> allFeedbacks = feedbackRepository.findAll();
        LocalDate localDate = date.minusDays(1); // Previous day
        for (Feedback feedback : allFeedbacks) {
            if (convertToLocalDate(feedback.getFeedbackDate()).isBefore(date)) {
                existingEmails.add(feedback.getEmail());
            }
        }

        // Load feedbacks for the given date and count new customer feedbacks
        for (Feedback feedback : feedbackRepository.findAll()) {
            if (convertToLocalDate(feedback.getFeedbackDate()).isEqual(date)) {
                String customerEmail = feedback.getEmail();
                if (!existingEmails.contains(customerEmail)) {
                    count++;
                    existingEmails.add(customerEmail);
                }
            }
        }
        return count;
    }

    public int getNewCustomerFeedbackGapByDay(LocalDate date) {
        int currentCount = countNewCustomerFeedback(date);
        int previousCount = countNewCustomerFeedback(date.minusDays(1));
        return Math.max(currentCount - previousCount, 0);
    }
}
