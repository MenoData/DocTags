
package de.menodata.taglets;

import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;

import java.util.Map;


/**
 * <p>Taglet zur Dokumentation des experimentellen Status. </p>
 *
 * @author  Meno Hochschild
 */
public class ExperimentalTaglet
    implements Taglet {

    //~ Statische Felder/Initialisierungen --------------------------------

    private static final String NAME = "doctags.experimental";
    private static final String HEADER = "Experimental:";
    private static final String DEFAULT_TEXT = "Public API can change in future without any notice.";

    //~ Methoden ----------------------------------------------------------

    public static void register(Map<String, Taglet> tagletMap) {
       Taglet tag = new ExperimentalTaglet();
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
        sb.append(toString(tag.text()));
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
            sb.append(toString(tags[i].text()));
        }

        sb.append("</DD>\n");
        return sb.toString();
    }

    private static String toString(String text) {
        return ((text == null) || text.isEmpty() ? DEFAULT_TEXT : text);
    }

}
