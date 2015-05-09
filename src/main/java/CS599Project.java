/**
 * Created by JeffWang ZhengLu on 4/28/15.
 */


import org.apache.hadoop.util.hash.Hash;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.io.*;
import java.util.HashSet;


/**
 16.
 * Twitter application using Twitter4J
 17.
 */

public class CS599Project {
    //private final Logger logger = Logger.getLogger(TwitterApplication.class.getName());

    public static void main(String[] args)  {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer("HYp3lKQtNOYqWcfj49MLOLa3X", "ROY0v4SHUCF1TgkP4pQA8Au4zumDZeVNGCj5pFGbZyJPtheBvF");
        twitter.setOAuthAccessToken(new AccessToken("3178501088-tvziiwhBPjit9Hj9LBW94KVkFhHXylxjkIsMHge", "ESzyWMxBrXBCY1iQ8E4sDPK9cejmqcEEOGatLa7kJEDnQ"));
        try{
            ResponseList<Status> myStatus = twitter.getUserTimeline(new Paging(1, 5));

            for(Status status : myStatus){
                System.out.println(status.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        Query query = new Query("Google stock");
        query.setCount(100);
        query.setLang("en");
        query.setSince("2014-01-01");


        try {
            QueryResult result = twitter.search(query);
            do{

                for(Status tweet: result.getTweets()){
                    System.out.println(tweet.getUser().getScreenName() + "  " + tweet.getText());
                }
                query=result.nextQuery();
                if(query != null)
                    result = twitter.search(query);
            }while(query != null);
        } catch (TwitterException e) {
            e.printStackTrace();
        }



        //pull test

    }



}