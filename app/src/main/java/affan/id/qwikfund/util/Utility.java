package affan.id.qwikfund.util;

import java.util.Random;

/**
 * Created by Herfanda on 10/8/2017 AD.
 */

public class Utility {
    //format untuk pemisahan nol pada jumlah uang. misal 25.000
    public static String getFormat(String number) {
        String displayedString = "";
        if (number == null || number.length() == 0 || number.equals("null")) {
            displayedString = "0";
        } else {
            if (number.length() > 3) {
                int length = number.length();

                for (int i = length; i > 0; i -= 3) {
                    if (i > 3) {
                        String myStringPart1 = number.substring(0, i - 3);
                        String myStringPart2 = number.substring(i - 3);

                        String combinedString;

                        combinedString = myStringPart1 + ".";

                        combinedString += myStringPart2;
                        number = combinedString;

                        displayedString = combinedString;
                    }
                }
            } else {
                displayedString = number;
            }
        }
        return displayedString;
    }

    // generate random number. misal dari angka 1-100
    public static int generateNumber(){
        int minNumber = 1;
        int maxNumber = 100;

        Random random = new Random();
        int randomNumber = random.nextInt((maxNumber - minNumber) + minNumber) + minNumber;

        return randomNumber;
    }

    // cek string apakah angka atau bukan
    public static boolean isInteger( String input )
    {
        try
        {
            Integer.parseInt( input );
            return true;
        }
        catch( Exception e)
        {
            return false;
        }
    }

}
