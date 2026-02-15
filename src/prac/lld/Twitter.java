package prac.lld;

import java.util.*;


public class Twitter {
    static class User {
        Long userId;
        Set<User> followee;
        List<Tweet> tweets;

        public User(Long userId) {
            this.userId = userId;
            this.followee = new HashSet<>();
            this.tweets = new ArrayList<>();
        }
    }

    static class Tweet {
        Long tweetId;
        long time;

        public Tweet(Long tweetId, long time) {
            this.tweetId = tweetId;
            this.time = time;
        }
    }

    private final Map<Long, User> db = new HashMap<>();
    private long timestamp = 0;

    private User getUser(Long userId) {
        return db.computeIfAbsent(userId, k -> new User(userId));
    }

    public void postTweet(Long userId, Long tweetId) {
        User user = getUser(userId);
        user.tweets.add(new Tweet(tweetId, timestamp++));
    }

    public List<Long> getFeed(Long userId) {
        User user = getUser(userId);

        PriorityQueue<Tweet> maxHeap = new PriorityQueue<>();

        // own tweets
        maxHeap.addAll(user.tweets);

        // followee tweets
        for (User f : user.followee) {
            maxHeap.addAll(f.tweets);
        }

        List<Long> feed = new ArrayList<>();
        int k = 10;
        while (!maxHeap.isEmpty() && k-- > 0) {
            feed.add(maxHeap.poll().tweetId);
        }
        return feed;
    }

    public boolean follow(Long followerId, Long followeeId) {
        if (followerId.equals(followeeId)) return false;

        User follower = getUser(followerId);
        User followee = getUser(followeeId);

        follower.followee.add(followee);
        return true;
    }

    public boolean unfollow(Long followerId, Long followeeId) {
        User follower = db.get(followerId);
        User followee = db.get(followeeId);
        if (follower == null || followee == null) return false;
        follower.followee.remove(followee);
        return true;
    }
}
