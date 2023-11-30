package com.erp.api.utility;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilityFunctions {

    public static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
    
    public static Boolean validateTrueFalse(String s)
    {
        if(s.isEmpty())
        {
            return false;
        }
        List<String> idType = Arrays.asList("yes","no","Yes","No","YES","NO");
        return idType.contains(s);
    }

    public static String getTimeStamp()
    {
        Instant instant = Instant.now();
        long timeStampSeconds = instant.toEpochMilli();
        return String.valueOf(timeStampSeconds);
    }

    public static Boolean validateAlphaNumeric(String word)
    {
        if(word.isEmpty())
        {
            return false;
        }
        Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
        Matcher m = p.matcher(word);

        return m.matches();
    }

    public static Boolean validateAlphaNumericWithSpace(String word)
    {
        if(word.isEmpty())
        {
            return false;
        }
        Pattern p = Pattern.compile("^[0-9a-zA-Z ]+$");
        Matcher m = p.matcher(word);

        return m.matches();
    }

    public static Boolean validateDeviceSrNo(String word)
    {
        if(word.isEmpty())
        {
            return false;
        }
        Pattern p = Pattern.compile("^[a-zA-Z0-9/-]+$");
        Matcher m = p.matcher(word);

        return m.matches();
    }
    
    public static Boolean validateUrl(String word)
    {
        if(word.isEmpty())
        {
            return false;
        }
        Pattern p = Pattern.compile("^(http(s)?:\\/\\/.)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)$");
        Matcher m = p.matcher(word);

        return m.matches();
    }
    

    public static Boolean validateAlpha(String word)
    {
        if(word.isEmpty())
        {
            return false;
        }
        Pattern p = Pattern.compile("^[a-zA-Z ]+$");
        Matcher m = p.matcher(word);

        return m.matches();
    }

    public static Boolean validateEmail(String word)
    {
        if(word.isEmpty())
        {
            return false;
        }
        Pattern p = Pattern.compile("^(([a-zA-Z0-9._]{1,50})|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]{2,50}\\.)+[a-zA-Z]{2,20}))$");
        Matcher m = p.matcher(word);

        return m.matches();
    }

    public static Boolean validateIdType(String s)
    {
        if(s.isEmpty())
        {
            return false;
        }
        List<String> idType = Arrays.asList("Permanent Voters Card","pvc","PVC","DL","dl","drivers_license","International Passport","passport","bvn","BVN");
        return idType.contains(s);
    }

    public static String getTxnRefNo(String s1)
    {
        StringBuilder s = new StringBuilder();
        s.append(s1);
        s.append(getAlphaNumericString(4));
        s.append(getTimeStamp());

        return s.toString();
    }

	public static boolean validateMobile(String phone) {
        if(phone.isEmpty())
        {
            return false;
        }
        Pattern p = Pattern.compile("((^091)([0-9]{8}$))|((^090)([0-9]{8}$))|((^070)([0-9]{8}))|((^080)([0-9]{8}$))|((^081)([0-9]{8}$))");
        Matcher m = p.matcher(phone);

        return m.matches();
    }

	public static boolean validateFirstName(String stringCellValue) {
        if(stringCellValue.isEmpty())
        {
            return false;
        }
        Pattern p = Pattern.compile("^[a-zA-Z-']{3,50}+$");
        Matcher m = p.matcher(stringCellValue);

        return m.matches();
    }

	public static boolean validateAddress(String address) {
        if(address.isEmpty())
        {
            return false;
        }
        Pattern p = Pattern.compile("^[a-zA-Z0-9(!@#$&-+:',./) ]+$");
        Matcher m = p.matcher(address);

        return m.matches();
    }

	public static boolean validateIntegerValue(String state) {
        if(state.isEmpty())
        {
            return false;
        }
        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher m = p.matcher(state);

        return m.matches();
    }

	public static boolean validateNormalText(String stringCellValue) {
        if(stringCellValue.isEmpty())
        {
            return false;
        }
        Pattern p = Pattern.compile("^[a-zA-Z0-9&/-]+$");
        Matcher m = p.matcher(stringCellValue);

        return m.matches();
    }

	public static boolean validateDoubleValue(double numericCellValue) {
       
        Pattern p = Pattern.compile("^[0-9.]+$");
        Matcher m = p.matcher(Double.toString(numericCellValue));

        return m.matches();
    }
}
