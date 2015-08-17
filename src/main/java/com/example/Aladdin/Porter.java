package com.example;

import java.util.ArrayList;

/**
 * Created by Ayush on 7/19/2015.
 */
class NewString {
    public String str;

    NewString() {
        str = "";
    }
}

public class Porter {


    boolean hasSuffix(String word, String suffix, NewString stem) {

        String tmp = "";

        if (word.length() <= suffix.length())
            return false;
        if (suffix.length() > 1)
            if (word.charAt(word.length() - 2) != suffix.charAt(suffix.length() - 2))
                return false;

        stem.str = "";

        for (int i = 0; i < word.length() - suffix.length(); i++)
            stem.str += word.charAt(i);
        tmp = stem.str;

        for (int i = 0; i < suffix.length(); i++)
            tmp += suffix.charAt(i);

        if (tmp.compareTo(word) == 0)
            return true;
        else
            return false;
    }

    int measure(String stem) {

        int i = 0, count = 0;
        int length = stem.length();

        while (i < length) {
            for (; i < length; i++) {
                if (i > 0) {
                    if (vowel(stem.charAt(i), stem.charAt(i - 1)))
                        break;
                } else {
                    if (vowel(stem.charAt(i), 'a'))
                        break;
                }
            }

            for (i++; i < length; i++) {
                if (i > 0) {
                    if (!vowel(stem.charAt(i), stem.charAt(i - 1)))
                        break;
                } else {
                    if (!vowel(stem.charAt(i), '?'))
                        break;
                }
            }
            if (i < length) {
                count++;
                i++;
            }
        } //while

        return (count);
    }

    boolean vowel(char ch, char prev) {
        switch (ch) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            case 'y': {

                switch (prev) {
                    case 'a':
                    case 'e':
                    case 'i':
                    case 'o':
                    case 'u':
                        return false;

                    default:
                        return true;
                }
            }

            default:
                return false;
        }
    }

    boolean containsVowel(String word) {

        for (int i = 0; i < word.length(); i++)
            if (i > 0) {
                if (vowel(word.charAt(i), word.charAt(i - 1)))
                    return true;
            } else {
                if (vowel(word.charAt(0), 'a'))
                    return true;
            }

        return false;
    }

    boolean cvc(String str) {
        int length = str.length();

        if (length < 3)
            return false;

        if ((!vowel(str.charAt(length - 1), str.charAt(length - 2)))
                && (str.charAt(length - 1) != 'w') && (str.charAt(length - 1) != 'x') && (str.charAt(length - 1) != 'y')
                && (vowel(str.charAt(length - 2), str.charAt(length - 3)))) {

            if (length == 3) {
                if (!vowel(str.charAt(0), '?'))
                    return true;
                else
                    return false;
            } else {
                if (!vowel(str.charAt(length - 3), str.charAt(length - 4)))
                    return true;
                else
                    return false;
            }
        }

        return false;
    }

    private String step1(String str) {

        NewString stem = new NewString();

        if (str.charAt(str.length() - 1) == 's')
        {
            if ((hasSuffix(str, "sses", stem)) || (hasSuffix(str, "ies", stem))) {
                String tmp = "";
                for (int i = 0; i < str.length() - 2; i++) tmp += str.charAt(i);
                str = tmp;
            } else {
                if ((str.length() == 1) && (str.charAt(str.length() - 1) == 's')) {
                    str = "";
                    return str;
                }
                if (str.charAt(str.length() - 2) != 's') {
                    String tmp = "";
                    for (int i = 0; i < str.length() - 1; i++)
                        tmp += str.charAt(i);
                    str = tmp;
                }
            }
        }

        if (hasSuffix(str, "eed", stem))
        {
            if (measure(stem.str) > 0) {
                String tmp = "";
                for (int i = 0; i < str.length() - 1; i++)
                    tmp += str.charAt(i);
                str = tmp;
            }
        }
        else
        {
            if ((hasSuffix(str, "ed", stem)) || (hasSuffix(str, "ing", stem)))
            {
                if (containsVowel(stem.str)) {

                    String tmp = "";
                    for (int i = 0; i < stem.str.length(); i++)
                        tmp += str.charAt(i);
                    str = tmp;
                    if (str.length() == 1)
                        return str;

                    if ((hasSuffix(str, "at", stem)) || (hasSuffix(str, "bl", stem)) || (hasSuffix(str, "iz", stem))) {
                        str += "e";

                    } else {
                        int length = str.length();
                        if ((str.charAt(length - 1) == str.charAt(length - 2))
                                && (str.charAt(length - 1) != 'l') && (str.charAt(length - 1) != 's') && (str.charAt(length - 1) != 'z')) {

                            tmp = "";
                            for (int i = 0; i < str.length() - 1; i++)
                                tmp += str.charAt(i);
                            str = tmp;
                        } else if (measure(str) == 1) {
                            if (cvc(str))
                                str += "e";
                        }
                    }
                }
            }
        }

        if (hasSuffix(str, "y", stem))
            if (containsVowel(stem.str)) {
                String tmp = "";
                for (int i = 0; i < str.length() - 1; i++)
                    tmp += str.charAt(i);
                str = tmp + "i";
            }
        return str;
    }

    private String step2(String step1_op) {
        String[][] suffixes = {{"ational", "ate"},
                {"tional", "tion"},
                {"enci", "ence"},
                {"anci", "ance"},
                {"izer", "ize"},
                {"iser", "ize"},
                {"abli", "able"},
                {"alli", "al"},
                {"entli", "ent"},
                {"eli", "e"},
                {"ousli", "ous"},
                {"ization", "ize"},
                {"isation", "ize"},
                {"ation", "ate"},
                {"ator", "ate"},
                {"alism", "al"},
                {"iveness", "ive"},
                {"fulness", "ful"},
                {"ousness", "ous"},
                {"aliti", "al"},
                {"iviti", "ive"},
                {"biliti", "ble"}};
        NewString stem = new NewString();


        for (int index = 0; index < suffixes.length; index++) {
            if (hasSuffix(step1_op, suffixes[index][0], stem)) {
                if (measure(stem.str) > 0) {
                    step1_op = stem.str + suffixes[index][1];
                    return step1_op;
                }
            }
        }

        return step1_op;
    }

    private String step3(String step2_op) {
        String[][] suffixes = {{"icate", "ic"},
                {"ative", ""},
                {"alize", "al"},
                {"alise", "al"},
                {"iciti", "ic"},
                {"ical", "ic"},
                {"ful", ""},
                {"ness", ""}};
        NewString stem = new NewString();

        for (int index = 0; index < suffixes.length; index++) {
            if (hasSuffix(step2_op, suffixes[index][0], stem))
                if (measure(stem.str) > 0) {
                    step2_op = stem.str + suffixes[index][1];
                    return step2_op;
                }
        }
        return step2_op;
    }

    private String step4(String step3_op) {
        String[] suffixes = {"al", "ance", "ence", "er", "ic", "able", "ible", "ant", "ement", "ment", "ent", "sion", "tion",
                "ou", "ism", "ate", "iti", "ous", "ive", "ize", "ise"};

        NewString stem = new NewString();

        for (int index = 0; index < suffixes.length; index++) {
            if (hasSuffix(step3_op, suffixes[index], stem)) {

                if (measure(stem.str) > 1) {
                    step3_op = stem.str;
                    return step3_op;
                }
            }
        }
        return step3_op;
    }

    private String step5(String step4_op) {
        if (step4_op.charAt(step4_op.length() - 1) == 'e') {
            if (measure(step4_op) > 1) {/* measure(str)==measure(stem) if ends in vowel */
                String tmp = "";
                for (int i = 0; i < step4_op.length() - 1; i++)
                    tmp += step4_op.charAt(i);
                step4_op = tmp;
            } else if (measure(step4_op) == 1) {
                String stem = "";
                for (int i = 0; i < step4_op.length() - 1; i++)
                    stem += step4_op.charAt(i);

                if (!cvc(stem))
                    step4_op = stem;
            }
        }

        if (step4_op.length() == 1)
            return step4_op;
        if ((step4_op.charAt(step4_op.length() - 1) == 'l') && (step4_op.charAt(step4_op.length() - 2) == 'l') && (measure(step4_op) > 1))
            if (measure(step4_op) > 1) {/* measure(str)==measure(stem) if ends in vowel */
                String tmp = "";
                for (int i = 0; i < step4_op.length() - 1; i++)
                    tmp += step4_op.charAt(i);
                step4_op = tmp;
            }
        return step4_op;
    }

    public ArrayList<String> start(ArrayList<String> tokenizedtext,boolean[] tokens) {
        String tempstring = null;
        for (int i = 0; i < tokenizedtext.size(); ++i) {
            tempstring = tokenizedtext.get(i);
            if(tokens[i] == false) continue;
            tempstring = step1(tempstring);
            if (tempstring.length() >= 1)
                tempstring = step2(tempstring);
            if (tempstring.length() >= 1)
                tempstring = step3(tempstring);
            if (tempstring.length() >= 1)
                tempstring = step4(tempstring);
            if (tempstring.length() >= 1)
                tempstring = step5(tempstring);

            tokenizedtext.set(i, tempstring);
        }

        return tokenizedtext;
    }

}
