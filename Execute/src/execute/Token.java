package execute;

import java.util.regex.*;
import java.util.regex.Pattern;

public enum Token {
    
    /*
    TK_MINUS ("-"), 
    TK_PLUS ("\\+"), 
    TK_MUL ("\\*"), 
    TK_DIV ("/"), 
    TK_NOT ("~"), 
    TK_AND ("&"),  
    TK_OR ("\\|"),  
    TK_LESS ("<"),
    TK_LEG ("<="),
    TK_GT (">"),
    TK_GEQ (">="), 
    TK_EQ ("=="),
    TK_ASSIGN ("="),
    TK_OPEN ("\\("),
    TK_CLOSE ("\\)"), 
    TK_SEMI (";"), 
    TK_COMMA (","), 
    TK_KEY_DEFINE ("define"), 
    TK_KEY_AS ("as"),
    TK_KEY_IS ("is"),
    TK_KEY_IF ("if"), 
    TK_KEY_THEN ("then"), 
    TK_KEY_ELSE ("else"), 
    TK_KEY_ENDIF ("endif"),
    DIFFERENT ("<>"),
    */
    //newline
    //tab
    HOLE ("HOLE"),
    FOLL ("FOLLOW"),
    //length
    //subs
    DESC_DATA ("DESC"),
    INIT_DATA ("INITIAL"),
    STAT_DATA ("STAT"),
    FRAC_DATA ("FRAC"),
    NAME_DATA ("NAME"),
    DESC_VAR ("DESVAR "),
    INITIAL_VAR ("INIVAR"),
    STAT_VAR ("STVAR"),
    FRAC_VAR ("FRVAR"),
    NAME_VAR ("NMVAR"),
    FRAC_LIT ("(\\d+)\\.\\d+"),
    STAT_LIT ("\\d+"),
    LOC ("LOCAL"),
    DESC ("DESC"),
    PATHMAIN_PATH ("PATH"),
    PATHMAIN_ALTPATH ("ALTPATH"),
    PATHMAIN_ALT ("ALT"),
    PLAYER ("PLAYER"),
    INPUT ("INPUT"),
    OUTPUT ("OUTPUT"),
    //combo_F
    //combo_w
    //combo_N
    CHAIN ("CHAIN"),
    //condition
    ENDLINE(";"),
    ASSIGNMENT("::"),
    RELOP_GT (">>"),
    RELOP_GTE (">>="),
    RELOP_LT ("<<"),
    RELOP_LTE ("<<="),
    RELOP_EQL ("=="),
    RELOP_NOTEQL ("!="),
    //game_start
    GAME ("GAME"),
    L_PAR ("\\("),
    R_PAR ("\\)"),
    ART_DOT_H ("DHEAL"),
    ART_DOT_D ("DDAM"),
    ART_SCALE ("SCALE"),
    ART_ENLARGE ("ENLRG"),
    ART_SHRINK ("SHNK"),
    ART_REM ("REM"),
    ART_HEAL ("HL"),
    ART_DAMAGE ("DAM"),
    ART_ROUNDUP ("UPGR"),
    ART_ROUNDDOWN ("DNGR"),
    LOG_NOT ("NOT"),
    LOG_AND ("AND"),
    LOG_NAND ("NAND"),
    LOG_OR ("OR"),
    LOG_NOR ("NOR"),
    LOG_XOR ("XOR"),
    RIGHTBRACE ("\\{"),
    LEFTBRACE ("\\}"),
    ARROPEN  ("\\["),
    ARRCLOSE  ("\\]"),
    COMBOLOOP ("COMBO"),
    COMMA (","),
    CONDITIONOPEN  ("<"),
    CONDITIONCLOSE  (">"),
    INIT_LIT_TRUE ("TRUE"),
    INIT_LIT_FALSE ("FALSE"),
    NAME_LIT ("\"[^\"]+\""),
    ENDGAME ("ENDGAME"),
    END ("END"),
    COMMENT ("//"),
    IDENTIFIER ("\\w+");
   

  
    
    private final Pattern pattern;

    Token(String regex) {
        pattern = Pattern.compile("^" + regex);
    }

    int endOfMatch(String s) {
        Matcher m = pattern.matcher(s);

        if (m.find()) {
            return m.end();
        }
        return -1;
    }
}