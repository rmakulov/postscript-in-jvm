package scanner;

/**
 * The tokens returned by the scanner.
 */
public class Yytoken {
    public Tokens m_type;
    public String m_text;
    public int m_line;
    public int m_charBegin;
    public int m_charEnd;

    Yytoken(Tokens type, String text, int line, int charBegin, int charEnd) {
        m_type = type;
        m_text = text;
        m_line = line;
        m_charBegin = charBegin;
        m_charEnd = charEnd;
    }

    public String toString() {
        return "Text   : " + m_text +
                "\ntype: " + m_type +
                "\nline  : " + m_line +
                "\ncBeg. : " + m_charBegin +
                "\ncEnd. : " + m_charEnd;
    }
}

