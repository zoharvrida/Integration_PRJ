package bdsm.util;

import java.util.regex.Pattern;

public class PatternUtil {
    
    private String patternString;
    private Pattern pattern;
    
    public PatternUtil(String pattern){
        this.patternString = pattern;
        this.pattern = Pattern.compile(this.patternString);
    }
    
    public boolean match(String input){
        return this.pattern.matcher(input).matches();
    }
    
}
