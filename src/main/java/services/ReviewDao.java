package services;

import config.AppConfig;
import models.Review;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
    // Submit a review using Review object
    public static boolean submitReview(Review review) {
        if (review == null || review.getMovieId() == null || review.getUserId() == null || 
            review.getContent() == null || review.getContent().trim().isEmpty()) {
            return false;
        }

        // Generate a new review ID if not set
        if (review.getReviewId() == null || review.getReviewId().trim().isEmpty()) {
            review.setReviewId("REV-" + System.currentTimeMillis());
        }

        try {
            String filePath = AppConfig.getRealPath(AppConfig.REVIEWS_FILE);
            File file = new File(filePath);
            if (!file.exists()) {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(review.getReviewId() + "," + review.getUserId() + "," + 
                           review.getMovieId() + "," + review.getContent() + "," + review.getRating());
                writer.newLine();
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error submitting review: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Submit a review (legacy method)
    public static boolean submitReview(String movieId, String userId, String content) {
        Review review = new Review();
        review.setMovieId(movieId);
        review.setUserId(userId);
        review.setContent(content);
        review.setRating(5); // Default rating
        return submitReview(review);
    }

    // Get reviews for a specific movie
    public static List<Review> getReviews(String movieId) {
        if (movieId == null) {
            return new ArrayList<>();
        }

        List<Review> reviews = new ArrayList<>();
        String filePath = AppConfig.getRealPath(AppConfig.REVIEWS_FILE);
        File file = new File(filePath);
        
        if (!file.exists()) {
            return reviews;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] reviewData = line.split(",");
                if (reviewData.length == 5 && reviewData[2].equals(movieId)) {
                    reviews.add(new Review(reviewData[0], reviewData[1], reviewData[2], 
                                         reviewData[3], reviewData[4]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error getting reviews: " + e.getMessage());
            e.printStackTrace();
        }
        return reviews;
    }

    // Get reviews by user ID
    public static List<Review> getUserReviews(String userId) {
        if (userId == null) {
            return new ArrayList<>();
        }

        List<Review> reviews = new ArrayList<>();
        String filePath = AppConfig.getRealPath(AppConfig.REVIEWS_FILE);
        File file = new File(filePath);
        
        if (!file.exists()) {
            return reviews;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] reviewData = line.split(",");
                if (reviewData.length == 5 && reviewData[1].equals(userId)) {
                    reviews.add(new Review(reviewData[0], reviewData[1], reviewData[2], 
                                         reviewData[3], reviewData[4]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error getting user reviews: " + e.getMessage());
            e.printStackTrace();
        }
        return reviews;
    }

    // Get review by ID
    public static Review getReviewById(String reviewId) {
        if (reviewId == null || reviewId.isEmpty()) {
            return null;
        }

        String filePath = AppConfig.getRealPath(AppConfig.REVIEWS_FILE);
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] reviewData = line.split(",");
                if (reviewData.length == 5 && reviewData[0].equals(reviewId)) {
                    return new Review(reviewData[0], reviewData[1], reviewData[2], 
                                    reviewData[3], reviewData[4]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error getting review by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Update a review with Review object
    public static boolean updateReview(Review review) {
        if (review == null || review.getReviewId() == null || 
            review.getContent() == null || review.getContent().trim().isEmpty()) {
            return false;
        }

        List<String> reviews = new ArrayList<>();
        boolean isUpdated = false;
        String filePath = AppConfig.getRealPath(AppConfig.REVIEWS_FILE);
        File file = new File(filePath);

        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] reviewData = line.split(",");
                if (reviewData[0].equals(review.getReviewId())) {
                    reviews.add(review.getReviewId() + "," + 
                              review.getUserId() + "," + 
                              review.getMovieId() + "," + 
                              review.getContent().trim() + "," + 
                              review.getRating());
                    isUpdated = true;
                } else {
                    reviews.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading reviews for update: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        if (isUpdated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                for (String rev : reviews) {
                    writer.write(rev);
                    writer.newLine();
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error writing updated review: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    // Get all reviews
    public static List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        String filePath = AppConfig.getRealPath(AppConfig.REVIEWS_FILE);
        File file = new File(filePath);
        
        if (!file.exists()) {
            return reviews;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] reviewData = line.split(",");
                if (reviewData.length == 5) {
                    reviews.add(new Review(reviewData[0], reviewData[1], reviewData[2], 
                                         reviewData[3], reviewData[4]));
                }
            }
        } catch (IOException e) {
            System.err.println("Error getting all reviews: " + e.getMessage());
            e.printStackTrace();
        }
        return reviews;
    }

    // Delete a review
    public static boolean deleteReview(String reviewId) {
        if (reviewId == null || reviewId.isEmpty()) {
            return false;
        }

        List<String> reviews = new ArrayList<>();
        boolean isDeleted = false;
        String filePath = AppConfig.getRealPath(AppConfig.REVIEWS_FILE);
        File file = new File(filePath);

        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] reviewData = line.split(",");
                if (!reviewData[0].equals(reviewId)) {
                    reviews.add(line);
                } else {
                    isDeleted = true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading reviews for deletion: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        if (isDeleted) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                for (String rev : reviews) {
                    writer.write(rev);
                    writer.newLine();
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error writing reviews after deletion: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }
}
