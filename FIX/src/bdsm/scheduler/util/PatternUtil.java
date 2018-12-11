package bdsm.scheduler.util;

import java.util.regex.Pattern;

/**
 * 
 * @author bdsm
 */
public class PatternUtil {
    
    private String patternString;
    private Pattern pattern;
    
    /**
     * 
     * @param pattern
     */
    public PatternUtil(String pattern){
        this.patternString = pattern;
        this.pattern = Pattern.compile(this.patternString);
    }
    
    /**
     * 
     * @param input
     * @return
     */
    public boolean match(String input){
        return this.pattern.matcher(input).matches();
    }
    
}
