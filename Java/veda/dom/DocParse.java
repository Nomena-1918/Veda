package veda.dom;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class DocParse{
    private Document document;
    private Document getDocument() {
        return document;
    }
    private void setDocument(Document document) {
        this.document = document;
    }
    public DocParse(File file) throws Exception{
        setDocument(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file));
    }
    public String getMainNodeName(){
        return getDocument().getDocumentElement().getNodeName();
    }
    public NodeList getNodeList(String nodeName){
        return getDocument().getElementsByTagName(nodeName);
    }
}
