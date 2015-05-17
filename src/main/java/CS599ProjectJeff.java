/**
 * Created by JeffWang ZhengLu on 4/28/15.
 */


//import edu.stanford.nlp.pipeline.TokenizerAnnotator;

import edu.stanford.nlp.pipeline.CleanXmlAnnotator;
import edu.stanford.nlp.pipeline.TokenizerAnnotator;
import edu.stanford.nlp.pipeline.TokensRegexAnnotator;
import edu.stanford.nlp.pipeline.TokensRegexNERAnnotator;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import com.datumbox.*;

/**
 16.
 * Twitter application using Twitter4J
 17.
 */

public class CS599Project {
    //private final Logger logger = Logger.getLogger(TwitterApplication.class.getName());

    public static void main(String[] args) {

        String[] str = new String[3];
        str[0] = "apple";
        str[1] = "http://blogs.teachersammy.com/Blogs#.VOKDD_nF91Y";
        str[2] = "bird";
        removeHttp(str);
        for(String s: str)
        {
            if(!s.equals(""))
                System.out.println(s);
        }


        System.out.println(removeRepeatedCharacter("hello"));




/*
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer("0iLCjNuS8tUUxTAVF8KfxX1uw", "jPBSw0QfNrEibSZvvmEOGVFdTWPPd29rB5XDEUykulJ4SUAujo");
        twitter.setOAuthAccessToken(new AccessToken("3178470914-sSE0W7obe4IDTirX9TUwyx5LAxtaTeB65UIHVWL", "kiyClECcMUptkNbNQb3wCAsx5aKltSgRl47ISW1xZXI30"));
        try{
            ResponseList<Status> myStatus = twitter.getUserTimeline(new Paging(1, 5));

            for(Status status : myStatus){
                System.out.println(status.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        Query query = new Query("facebook stock");
        query.setCount(100);
        query.setLang("en");
        query.since("2013-01-01");
        try {
            QueryResult result = twitter.search(query);
            for(Status otherStatus : result.getTweets()){
                System.out.println(otherStatus.getUser().getScreenName() + "      " + otherStatus.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
*/
    }

    public void retrieve() {
        //logger.info("Retrieving tweets...");
        Twitter twitter = new TwitterFactory().getInstance();

        String user = "TheJavaTutorial";

        Query query = new Query("from:"+user);

        query.count(100);

        query.setSince("2014-01-01");

        try {

            QueryResult result = twitter.search(query);

            System.out.println("Count : " + result.getTweets().size()) ;

            for (Status tweet : result.getTweets()) {

                System.out.println("text : " + tweet.getText());

            }

        } catch (TwitterException e) {
            e.printStackTrace();
        }

        //logger.info("done! ");
    }

    public static void removeHttp(String[] str)
    {
        for(int i=0; i<str.length; i++)
        {
            if(str[i].startsWith("http:"))
                str[i] = "";
        }
    }

    public static String removeRepeatedCharacter(String str)
    {
        return str.replaceAll("(.)\\1+", "$1");
    }


}