package com.example.httpconnectexample;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
 
public class MainActivity extends Activity {
    ArrayList<String> data= new ArrayList<String>();
    LinearLayout ll;
    LayoutInflater layoutInflater;
    private Button getImageButton;
    private Button getTextButton;
    private ProgressDialog progressDialog;  
    private Bitmap bitmap = null;
    private String text = null;
     
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //getImageButton = (Button)findViewById(R.id.Button01);
        getTextButton = (Button)findViewById(R.id.Button02);
        ll= (LinearLayout) this.findViewById(R.id.LinearLayout01);
        layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
    	
         
        /*getImageButton.setOnClickListener( new OnClickListener() {
 
            @Override
            public void onClick(View v) {
                downloadImage("http://www.android.com/media/wallpaper/gif/android_logo.gif");
                 
            }
        });*/
         
        getTextButton.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
                downloadText("http://www.ise.eng.chula.ac.th/web/?q=news");
            }
        });
    }
     
 
    /*private void downloadImage(String urlStr) {
        progressDialog = ProgressDialog.show(this, "", "Fetching Image...");
        final String url = urlStr;
         
        new Thread() {
            public void run() {
                InputStream in = null;
                Message msg = Message.obtain();
                msg.what = 1;
                try {
                    in = openHttpConnection(url);
                    bitmap = BitmapFactory.decodeStream(in);
                    Bundle b = new Bundle();
                    b.putParcelable("bitmap", bitmap);
                    msg.setData(b);
                    in.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                messageHandler.sendMessage(msg);    
                 
            }
        }.start();
 
    }*/
     
    private void downloadText(String urlStr) {
        progressDialog = ProgressDialog.show(this, "", "Fetching Text...");
        final String url = urlStr;
         
        new Thread () {
            public void run() {
                int BUFFER_SIZE = 2000;
                InputStream in = null;
                Message msg = Message.obtain();
                msg.what=2;
                try {
                    in = openHttpConnection(url);
                     
                    InputStreamReader isr = new InputStreamReader(in);
                    int charRead;
                    int readcount = 0;
                    int readOn=0;
                    int readingid=0;
                    boolean end=false;
                    String memo1 = "";
                    String memo2 = "";
                      text = "";
                      char[] inputBuffer = new char[BUFFER_SIZE];
 
                          while (true)
                          {                    
                              //---convert the chars to a String---
                        	  charRead = isr.read(inputBuffer);
                              String readString = 
                              String.copyValueOf(inputBuffer, 0, charRead);
                              if (readcount > 5 && readcount<11){
                            	  while(readOn < readString.lastIndexOf(">")||readOn<readString.lastIndexOf("?")){
                            		  if(memo2.length()!=0){
            							  memo2 += readString.substring(0,12-memo2.length());
            							  data.add(memo2);
            							  memo2="";
            							  readOn= 12-memo2.length();
            							  readingid=2;
                            		  }  
                            		  if(memo1.length()!=0){
                            			  memo1+=readString.substring(0,readString.indexOf("<"));
                            			  data.add(memo1);
                            			  readOn = readString.indexOf("<");
                            			  memo1="";
                            			  readOn=1;
            					      }
                            		  else{
                            			  if(readString.indexOf("?",readOn) != -1)
                            			  {
                            				  if(readingid==0)
                            				  {
                            					  if(readString.indexOf("?",readOn)<BUFFER_SIZE-12){
                            					  memo2+=readString.substring(readString.indexOf("?",readOn),readString.indexOf("?",readOn)+12)+" ";
                            					  
                            					  if(memo2.indexOf("news")>-1)
                            					  { 
                            						  end=true;
                            					  }
                            					  else{
                            						  if(memo2.length()!=0){
                            						  data.add(memo2);
                            						  memo2="";
                            						  }
                            					  }
                            					  readingid=2;
                            					  readOn=readString.indexOf("?",readOn)+12;
                            					  }
                            					  else{
                            						  memo2 += readString.substring(readString.indexOf("?",readOn));
                            					  }
                            				  }
                            			  }

                            		      if(readString.indexOf("<",readString.indexOf(">",readOn))!=-1){
                            		          memo1=readString.substring(readString.indexOf(">",readOn)+1,readString.indexOf("<",readString.indexOf(">",readOn))).trim();
                            		          if(memo1.length()==1){
                            		    	       end = true;
                            		          }
                            		          else{
                            		        	  
                            		          }
                            		      }
                            		      else{
                            			      memo1=readString.substring(readString.indexOf(">",readOn)+1).trim();
                            			      break;
                            		      }
                            		      readOn = readString.indexOf("<",readString.indexOf(">",readOn));
                            		      if(memo1.length()!=0){ 
                            		    	  data.add(memo1);
                            		    	  readingid--;
                            		    	  //text+="\n";

                            		      }
                            		      memo1="";
                            	      }
                            		  if (end){
                                		  break;
                                	  }
                            	  }
                            	  
                              }
                              //HeadCount=0;
                              readOn=0;
                              inputBuffer = new char[BUFFER_SIZE];
                              readcount++;
                              if (end){
                        		  break;
                        	  }
                              if (readcount ==11){
                            	  break;
                              }
                          }
                          
                         //Bundle b = new Bundle();
                            //b.putString("text", text);
                            //msg.setData(b);
                          in.close();
                       
                }catch (IOException e) {
                    e.printStackTrace();
                }
                messageHandler.sendMessage(msg);
            }
        }.start();
           
    }
     
    private InputStream openHttpConnection(String urlStr) {
        InputStream in = null;
        int resCode = -1;
         
        try {
            URL url = new URL(urlStr);
            URLConnection urlConn = url.openConnection();
             
            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException ("URL is not an Http URL");
            }
             
            HttpURLConnection httpConn = (HttpURLConnection)urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect(); 
 
            resCode = httpConn.getResponseCode();                 
            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();                                 
            }         
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }
     
    private Handler messageHandler = new Handler() {
         
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case 1:
               // ImageView img = (ImageView) findViewById(R.id.imageview01);
                //img.setImageBitmap((Bitmap)(msg.getData().getParcelable("bitmap")));
                break;
            case 2:
            	for(int i=0;i<data.size()/3;i++){
            	RelativeLayout rl = (RelativeLayout) layoutInflater.inflate(R.layout.section_layout, null);
            	TextView text1 = (TextView)rl.findViewById(R.id.head);
            	text1.setText(data.get(3*i+1));
           	    TextView text2 = (TextView)rl.findViewById(R.id.text);
             	text2.setText(data.get(3*i+2));  
                ll.addView(rl);
            	}
                //TextView text = (TextView) findViewById(R.id.textview01);
                //text.setText("finish");
                break;
            }
            progressDialog.dismiss();
        }
    };
    
    
}
