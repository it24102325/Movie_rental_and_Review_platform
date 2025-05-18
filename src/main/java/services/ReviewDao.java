package services;

import models.Review;
import models.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ReviewDao {

    private static final String reviewFilePath = "C:\\Users\\Muditha\\Desktop\\jacka (1)\\jacka (1)\\movie_rental_and_review_platform_05\\data\\reviews.txt";

    // Add a review for a movie
    public static boolean addReview(String movieId, String userId, String rating, String feedback) {
        String reviewId = "RV" + System.currentTimeMillis();  // Generate a unique review ID
        Review review = new Review(reviewId, movieId, userId, rating, feedback);

        // Try to append the review to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reviewFilePath, true))) {
            writer.write(review.toFileFormat() + "\n");
            return true;
        } catch (IOException e) {
            System.err.println("Error writing review: " + e.getMessage());
            return false;
        }
    }

    // Update an existing review
    public static boolean updateReview(String reviewId, String newRating, String newFeedback) {
        List<String> reviews = new ArrayList<>();
        boolean isUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(reviewFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] reviewData = line.split(",");
                if (reviewData[0].equals(reviewId)) {
                    // Update the review data
                    reviews.add(reviewId + "," + reviewData[1] + "," + reviewData[2] + "," + newRating + "," + newFeedback);
                    isUpdated = true;
                } else {
                    reviews.add(line);  // Keep the original line if not the matching review
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading review file: " + e.getMessage());
            return false;
        }

        if (isUpdated) {
            // Write the updated reviews back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reviewFilePath, false))) {
                for (String review : reviews) {
                    writer.write(review);
                    writer.newLine();
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error writing updated review: " + e.getMessage());
                return false;
            }
        }
        return false;  // Review not found to update
    }

    // Delete a review
    public static boolean deleteReview(String reviewId) {
        List<String> reviews = new ArrayList<>();
        boolean isDeleted = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(reviewFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] reviewData = line.split(",");
                if (!reviewData[0].equals(reviewId)) {
                    reviews.add(line);  // Add all lines except the one to be deleted
                } else {
                    isDeleted = true;  // Mark as deleted
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading review file: " + e.getMessage());
            return false;
        }

        if (isDeleted) {
            // Write the remaining reviews back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reviewFilePath, false))) {
                for (String review : reviews) {
                    writer.write(review);
                    writer.newLine();
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error writing updated reviews after deletion: " + e.getMessage());
                return false;
            }
        }
        return false;  // Review not found to delete
    }

    // Get all reviews with user information
    public static List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        Map<String, User> userCache = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(reviewFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] reviewData = line.split(",");
                String userId = reviewData[2];
                
                // Get user information from cache or load it
                User user = userCache.get(userId);
                if (user == null) {
                    user = UserDao.getUserByUsername(userId);
                    if (user != null) {
                        userCache.put(userId, user);
                    }
                }
                
                Review review = new Review(reviewData[0], reviewData[1], userId, reviewData[3], reviewData[4]);
                if (user != null) {
                    review.setUserName(user.getName());
                }
                reviews.add(review);
            }
        } catch (IOException e) {
            System.err.println("Error reading review file: " + e.getMessage());
        }
        return reviews;
    }

    // Get reviews for a specific movie with user information
    public static List<Review> getReviewsForMovie(String movieId) {
        List<Review> reviews = new ArrayList<>();
        Map<String, User> userCache = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(reviewFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] reviewData = line.split(",");
                if (reviewData[1].equals(movieId)) {
                    String userId = reviewData[2];
                    
                    // Get user information from cache or load it
                    User user = userCache.get(userId);
                    if (user == null) {
                        user = UserDao.getUserByUsername(userId);
                        if (user != null) {
                            userCache.put(userId, user);
                        }
                    }
                    
                    Review review = new Review(reviewData[0], reviewData[1], userId, reviewData[3], reviewData[4]);
                    if (user != null) {
                        review.setUserName(user.getName());
                    }
                    reviews.add(review);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading review file: " + e.getMessage());
        }
        return reviews;
    }
}