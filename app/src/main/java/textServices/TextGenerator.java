package textServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Tisa on 4/29/2016.
 */
public class TextGenerator {

    public TextGenerator (){}

    public String getGreeting() {
        List<String> phrase1Holder = new ArrayList<String>();
        phrase1Holder.add("Hey!");
        phrase1Holder.add("Hi!");
        phrase1Holder.add("Heyy!");
        phrase1Holder.add("Heyyy!");
        phrase1Holder.add("What's Up?");
        phrase1Holder.add("Helloooo");
        phrase1Holder.add("wyd?");
        Random generator = new Random();
        int i = generator.nextInt(phrase1Holder.size());

        return phrase1Holder.get(i);
    }

    public String getPetName(String gender) {
        List<String> phrase1Holder = new ArrayList<String>();
        if (gender.equals("girl")) {
            phrase1Holder.add("Beautiful");
            phrase1Holder.add("my Big Booty");
            phrase1Holder.add("Sweetie Cakes");
        } else if (gender.equals("boy")){
            phrase1Holder.add("Handsome");
            phrase1Holder.add("my Swole Dude");
            phrase1Holder.add("Schoner");
        }
            phrase1Holder.add("my Love");
            phrase1Holder.add("Bae");
            phrase1Holder.add("Babe");
            phrase1Holder.add("Sweetheart");

        Random generator = new Random();
        int i = generator.nextInt(phrase1Holder.size());

        return phrase1Holder.get(i);
    }

    public String getMorningPhrases() {
        List<String> phrase1Holder = new ArrayList<String>();
        phrase1Holder.add("Good Morning");
        phrase1Holder.add("Mornin'");
        phrase1Holder.add("I had a dream about you last night!  Morning");
        phrase1Holder.add("Just woke up, tell me about your morning");

        Random generator = new Random();
        int i = generator.nextInt(phrase1Holder.size());

        return phrase1Holder.get(i);
    }

    public String getNightPhrases() {
        List<String> phrase1Holder = new ArrayList<String>();
        phrase1Holder.add("Good Night");
        phrase1Holder.add("I'll see you soon");
        phrase1Holder.add("Going to bed");
        phrase1Holder.add("I'm tired and sleepy.  I'll talk to you tomorrow" );

        Random generator = new Random();
        int i = generator.nextInt(phrase1Holder.size());

        return phrase1Holder.get(i);
    }

    public String getRandomPhrases() {
        List<String> phrase1Holder = new ArrayList<String>();
        phrase1Holder.add("I miss you");
        phrase1Holder.add("I wish I was with you now");
        phrase1Holder.add("Just thinking about you, how's your day been,");

        Random generator = new Random();
        int i = generator.nextInt(phrase1Holder.size());

        return phrase1Holder.get(i);
    }

    public String getPunctuation() {
        List<String> phrase1Holder = new ArrayList<String>();
        phrase1Holder.add("!");
        phrase1Holder.add(".");
        phrase1Holder.add("..");

        Random generator = new Random();
        int i = generator.nextInt(phrase1Holder.size());

        return phrase1Holder.get(i);
    }

    public String getSmiley() {
        List<String> phrase1Holder = new ArrayList<String>();
        phrase1Holder.add(";)");
        phrase1Holder.add("(:");
        phrase1Holder.add(":*");
        phrase1Holder.add("");

        Random generator = new Random();
        int i = generator.nextInt(phrase1Holder.size());

        return phrase1Holder.get(i);
    }

    public String getAnniversaryPhrases () {
        List<String> phrase1Holder = new ArrayList<String>();
        phrase1Holder.add("I love you so much.  Happy anniverary");
        phrase1Holder.add("Let's do something special for our anniversary");

        Random generator = new Random();
        int i = generator.nextInt(phrase1Holder.size());

        return phrase1Holder.get(i);
    }

    public String getBirthdayPhrases () {
        List<String> phrase1Holder = new ArrayList<String>();
        phrase1Holder.add("HAPPY BIRTHDAY");

        Random generator = new Random();
        int i = generator.nextInt(phrase1Holder.size());

        return phrase1Holder.get(i);
    }


}
