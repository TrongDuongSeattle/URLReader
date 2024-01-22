/**
 * CS320 - Programming Languages
 * Trong Duong
 * 01/18/2024
 * Worksheet 2
 * A webscraper gather final exam times
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.*;

public class URLReaderStart {
    public static void examReader(String text) {
        //<tr><td>()</td><td>()<td></td>
        //<tr><td>(.*?)</td><td (.*?)*>(<a h.*?>)*(.*?)(</a></td></tr><tr>)* captures see note row, doesn't leave a blank
        //"<tr><td>(.*?)</td><td (.*?)*>((<a h.*?>)*(.*?)(</a></td></tr><tr>))*<td>(.*?)(</td><td>)*</tr>"
            Pattern pattern = Pattern.compile("<tr><td>(.*?)</td>" +
                    "<td .*?>(<a h.*?>)*(.*?)(</a>)*</td>" +
                    "(<td>(.*?)</td>)*" +
                    "</tr>");
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while(matcher.find()) {
            //return according to format
            count++;

            System.out.println(matcher.groupCount());
            System.out.println("Class Time: " + matcher.group(1));
            System.out.println("Exam Day: " + matcher.group(3));
            System.out.println("Exam time: " + matcher.group(6));
            //System.out.println(matcher.group());
            System.out.println("++++++++++++++++++++++++++++++");
        }
        System.out.println("Matches: " + count);
    }
    //<tr><td>(.*?)
    //</td><td .*?>
    // (<a h.*?>)*
    // (.*?)
    // (</a)*
    // </td>
    // (<td>(.*?)</td>)*?
    // </tr>
    // <tr><t
    // <tr><td>(.*?)</td><td .*?>(<a h.*?>)*(.*?)(</a)*</td>(<td>(.*?)</td>)*?</tr>
    public static void testReader (String text) {

        Pattern pattern = Pattern.compile("<tr><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td></tr>");
        //  get a matcher object
        //Matcher matcher = pattern.matcher("<tr><td>Cell1 info</td><td>Cell2 info</td><td>Cell3 info</td></tr>");
        Matcher matcher = pattern.matcher(text);
        boolean found = false;

        while(matcher.find()) {
            System.out.format("I found the text" +
                            " \"%s\" starting at " +
                            "index %d and ending at index %d.%n",
                    matcher.group(),
                    matcher.start(),
                    matcher.end());
            found = true;
        }
        if(!found){
            System.out.format("No match found.%n");
        }

    }
    //<tr><td>(.*?)
    //</td><td .*?>
    // (<a h.*?>)*
    // (.*?)
    // (</a)*
    // </td>
    // (<td>(.*?)</td>)*?
    // </tr>
    // <tr><td>(.*?)</td><td .*?>(<a h.*?>)*(.*?)(</a)*</td>(<td>(.*?)</td>)*?</tr>

    public static void main(String[] args) throws Exception {
        // opens URL from website - URLConnection object?
        URLConnection bc = new URL("https://www.bellevuecollege.edu/courses/exams/").openConnection();
        //It's because the site uses SSL.
        //You just need to set user agent header for it to work:
        //SSL (secure socket layer) is a method of ensuring security of data passed back and forth between a client and server.
        // An SSL endpoint is a regular URL, but with https instead of http.
        // Using SSL is more complicated than regular http because there needs to be handshaking between the client and server.
        bc.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

        BufferedReader in = new BufferedReader(new InputStreamReader(bc.getInputStream()));

        String inputLine = "";

        String text = "";
        while ((inputLine = in.readLine()) != null) {
            text += inputLine + "\n";

        }
        //System.out.println(text);
        in.close();
        //testReader(text);
        examReader(text);

    /* Add your own code here to 
    Read Final Exam Schedule from BC website using regex in java
    <tr>
    <td>Cell1 info</td>
    <td>Cell2 info</td>
    <td>Cell3 info</td>
    </tr>

    TODO
    1. need a pattern
        Pattern pattern = Pattern.compile("")
    2.  Matcher
        Matcher matcher = patter.matcher(text);
        matches against pattern
    3.  6:30 time has note that affects HTML
        - adding a wildcard to second matcher group with reluctnat kleene star
            - now href pops up
    */
        //parens denote matching groups
        //using reluctant behavior because it'll grab too much otherwise
        //<tr><td>(.*?)
        //</td><td .*?>
        // (<a h.*?>)*
        // (.*?)
        // (</a)*
        // </td>
        // (<td>(.*?)</td>)*?
        // </tr>
        // <tr><td>(.*?)</td><td .*?>(<a h.*?>)*(.*?)(</a)*</td>(<td>(.*?)</td>)*?</tr>


    }

}
