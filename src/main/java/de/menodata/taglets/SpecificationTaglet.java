
package de.menodata.taglets;

import com.sun.javadoc.Tag;
import com.sun.tools.doclets.Taglet;
import java.util.Map;


/**
 * <p>Taglet zur Dokumentation einer Spezifikation. </p>
 *
 * @author  Meno Hochschild
 */
public class SpecificationTaglet
    implements Taglet {

    //~ Statische Felder/Initialisierungen --------------------------------

    private static final String NAME = "doctags.spec";
    private static final String HEADER = "Specification:";

    //~ Methoden ----------------------------------------------------------

    public static void register(Map<String, Taglet> tagletMap) {
       Taglet tag = new SpecificationTaglet();
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
        sb.append(tag.text());
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
            sb.append(tags[i].text());
        }

        sb.append("</DD>\n");
        return sb.toString();
    }

}
