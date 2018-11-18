package org.vaadin.samples.helloworld;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;

import javax.servlet.annotation.WebServlet;

import com.mongodb.MongoClient;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Theme("valo")
@SuppressWarnings("serial")
public class HelloWorldUI extends UI {

    private int clickCounter = 0;
    private Label clickCounterLabel;
    
  private final TextField meetingpurposefield =new TextField ("Purpose of meeting");
 private final  TextField meetingpurposefieldtwo =new TextField ("Purpose of meeting two");


    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = HelloWorldUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
        
       
        layout.addComponent(new Label("Hello World!"));
        layout.addComponent(meetingpurposefield);
        layout.addComponent(meetingpurposefieldtwo);
        
        layout.addComponent(new Label("Greetings from server."));
        layout.addComponent(new Label("I have "
                + Runtime.getRuntime().availableProcessors()
                + " processors and "
                + (Runtime.getRuntime().totalMemory() / 1000000)
                + " MB total memory."));
        
      
        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                clickCounter++;
                clickCounterLabel.setValue("Clicks: " + clickCounter);
                Notification.show("Thank you for clicking.");
                String auth_user="viswa", auth_pwd = "viswa@123";
                
                
               String encoded_pwd = "";
               try {
				encoded_pwd = URLEncoder.encode(auth_pwd, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

                String client_url =
                		
                		"mongodb://" + auth_user + ":" + encoded_pwd 
                		+ "@" + "ds031902.mlab.com" + ":" + 31902 + ""
                				+ "/" + "checkvaadintest";
               

                
              //  mongodb://<dbuser>:<dbpassword>@ds031902.mlab.com:31902/checkvaadintest
                
                MongoClientURI uri = new MongoClientURI(client_url);
               
               MongoClient mongoClient = new MongoClient(uri);
              
              DB viswadbconnection = mongoClient.getDB("checkvaadintest");
              
              
              DBCollection collection = viswadbconnection.getCollection("checkone");
              /**** Insert ****/
              // create a document to store key and value
              BasicDBObject document = new BasicDBObject();
              document.put("firstName", meetingpurposefield.getValue());
              document.put("lastName", meetingpurposefieldtwo.getValue());
              collection.insert(document);
              System.out.println(collection.getCount() +"longvalue");
              Set<String> collectionname = viswadbconnection.getCollectionNames();
              
              System.out.println(collectionname +"collectionnamecollectionname");
           
                /**** Get database ****/
                // if database doesn't exists, MongoDB will create it for you
              // DB db = mongoClient.getDB("testdb");
              //  mongoClient.getDatabaseNames().forEach(System.out::println);
                /**** Get collection / table from 'testdb' ****/
                // if collection doesn't exists, MongoDB will create it for you
             //  DBCollection collection = db.getCollection("users");
                /**** Insert ****/
                // create a document to store key and value
               // BasicDBObject document = new BasicDBObject();
                document.put("firstName", "Dharam");
                document.put("lastName", "Rajput");
               // collection.insert(document);
                
                
                ///////////////
                
               
            }
        });

        layout.addComponent(button);
        layout.addComponent(clickCounterLabel = new Label("Clicks: 0"));
    }

}
