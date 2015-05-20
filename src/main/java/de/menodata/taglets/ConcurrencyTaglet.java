
package de.menodata.taglets;

import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;
import java.util.Map;


/**
 * <p>Taglet zur Dokumentation des Verhaltens einer Klasse bei Verwendung
 * von mehreren Threads gleichzeitig. </p>
 *
 * @author  Meno Hochschild
 */
public class ConcurrencyTaglet
    implements Taglet {

    //~ Statische Felder/Initialisierungen --------------------------------

    private static final String NAME = "doctags.concurrency";
    private static final String HEADER = "Concurrency:";

    //~ Methoden ----------------------------------------------------------

    public static void register(Map<String, Taglet> tagletMap) {
       Taglet tag = new ConcurrencyTaglet();
       Taglet t = tagletMap.get(tag.getName());

       if (t != null) {
           tagletMap.remove(tag.getName());
       }

       tagletMap.put(tag.getName(), tag);
    }

    @Override
    public boolean inField() {
        return false;
    }

    @Override
    public boolean inConstructor() {
        return false;
    }

    @Override
    public boolean inMethod() {
        return false;
    }

    @Override
    public boolean inOverview() {
        return false;
    }

    @Override
    public boolean inPackage() {
        return false;
    }

    @Override
    public boolean inType() {
        return true;
    }

    @Override
    public boolean isInlineTag() {
        return false;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String toString(Tag tag) {
        StringBuilder sb = new StringBuilder();
        sb.append("<DT><B>");
        sb.append(HEADER);
        sb.append("</B></DT><DD>");
        sb.append(translate(tag.text()));
        sb.append("</DD>\n");
        return sb.toString();
    }

    @Override
    public String toString(Tag[] tags) {
        if (tags.length == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<DT><B>");
        sb.append(HEADER);
        sb.append("</B></DT><DD>");

        for (int i = 0; i < tags.length; i++) {
            if (i > 0) {
                sb.append(" ");
            }
            sb.append(translate(tags[i].text()));
        }

        sb.append("</DD>\n");
        return sb.toString();
    }

    private static String translate(String word) {
        String w = word.trim();

        if (w.equals("{immutable}")) {
            return "This class is immutable and "
                + "can be used by multiple threads in parallel.";
        } else if (w.equals("{threadsafe}")) {
            return "This class is thread-safe and "
                + "can be used by multiple threads in parallel.";
        } else if (w.equals("{stateless}")) {
            return "This class is stateless and "
                + "can be used by multiple threads in parallel.";
        } else if (w.equals("{mutable}")) {
            return "This class is mutable and "
                + "hence intended for use in a single thread only.";
        } else {
            return w;
        }
    }

}
