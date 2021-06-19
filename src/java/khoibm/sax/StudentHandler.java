package khoibm.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StudentHandler extends DefaultHandler {

    private String username, password;
    private boolean foundUsername, foundPassword, found;
    private String currentTagName;
    private String fullname;

    public StudentHandler(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isFound() {
        return found;
    }

    public String getFullname() {
        return fullname;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //To change body of generated methods, choose Tools | Templates.
        if (!found) {
            if (qName.equals("student")) {
                String id = attributes.getValue("id");
                if (id.equals(username)) {
                    this.foundUsername = true;
                }
            }
            this.currentTagName = qName;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.currentTagName = "";

        if (qName.equals("student")) {
            this.foundUsername = false;
            this.foundPassword = false;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (!found) {
            String str = new String(ch, start, length);
            if (foundUsername) {
                if (this.currentTagName.equals("lastname")) {
                    this.fullname = str.trim();
                } else if (this.currentTagName.equals("middlename")) {
                    this.fullname = this.fullname + str.trim();
                } else if (this.currentTagName.equals("firstname")) {
                    this.fullname = this.fullname + str.trim();
                } else if (this.currentTagName.equals("password")) {
                    if (str.trim().equals(password)) {
                        foundPassword = true;
                    }
                }
            } // end of foundUsername

            if (foundPassword) {
                if (this.currentTagName.equals("status")) {
                    if (!str.trim().equals("Dropout")) {
                        this.found = true;
                    }

                }
            }
        }
    }

}
