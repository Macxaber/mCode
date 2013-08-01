package com.theslide.android;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
 

import android.annotation.SuppressLint;
import android.content.Context;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import android.widget.ArrayAdapter;

import android.widget.Toast;
 
import com.theslide.android.R;
import com.theslide.android.tabs.Tab1Fragment;
import com.theslide.android.tabs.Tab2Fragment;
import com.theslide.android.tabs.Tab3Fragment;
 
/**
 * The <code>ViewPagerFragmentActivity</code> class is the fragment activity hosting the ViewPager
 * @author mwho
 */
public class ViewPagerFragmentMainActivity extends FragmentActivity{
	List<Fragment> fragments = new Vector<Fragment>();
    private PagerAdapter mPagerAdapter;

     LinearLayout News;
    ArrayList<String> data= new ArrayList<String>();
     LinearLayout ll;
    LayoutInflater layoutInflater;
     ScrollView sv;
     Button getTextButton;
     Button removebutton;
    TextView etContent;
    File tempFile;
    File version;
    String memory="";
    int versc=0;
    int versn=0;
    Fragment a;
    Fragment b;
    Fragment c;


    String[] actions = new String[] {
			"All News",
			"Admission",
			"FAQs"
		};
    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	getActionBar().setDisplayShowTitleEnabled(false);
    	getActionBar().setDisplayShowHomeEnabled(false);
    	super.onCreate(savedInstanceState);
        super.setContentView(R.layout.viewpager_layout);
        this.initialisePaging();
        
        
        //Toast.makeText(getBaseContext(), kl.getChildCount()  , Toast.LENGTH_SHORT).show();
        getTextButton = (Button) findViewById(R.id.Button01);
        //getTextButton.setText("asdasd");
        
     //   layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        File cDir = getBaseContext().getCacheDir();
        
        /** Getting a reference to temporary file, if created earlier */
        tempFile = new File(cDir.getPath() + "/" + "wpta_temp_file1.txt") ;
        version= new File(cDir.getPath() + "/" + "wpta_version_file.txt") ;
        //com.theslide.android.tabs.Tab1Fragment.class.
 
        /** Getting reference to edittext et_content */
        //etContent = (TextView) findViewById(R.id.textView1);
 
        StringBuilder number = new StringBuilder();
        try {     
        	FileReader fReader = new FileReader(version);
            Scanner scReader = new Scanner(fReader);
            number.append(scReader.next());
            scReader.close();
            versc = Integer.parseInt(number.toString());
            //etContent.setText(versc +"");
            fetchText("http://www.ise.eng.chula.ac.th/web/?q=news",3);


            
        
        } catch (FileNotFoundException e) {
         e.printStackTrace();
        }
       // getTextButton.setText("0321");
        /*getTextButton.setOnClickListener(new OnClickListener() {
        	 
            @Override
            public void onClick(View v) {
            	try {     
            		StringBuilder number = new StringBuilder();
                	FileReader fReader = new FileReader(version);
                    Scanner scReader = new Scanner(fReader);
                    number.append(scReader.next());
                    scReader.close();
                    versc = Integer.parseInt(number.toString());
                    //etContent.setText(versc +"");
                    //fetchText("http://www.ise.eng.chula.ac.th/web/?q=news",3);


                    
                
                } catch (FileNotFoundException e) {
                 e.printStackTrace();

                }
            }
        });*/
        /*removebutton.setOnClickListener(new OnClickListener() {
        	 
            @Override
            
            public void onClick(View v) {
            	int j =ll.getChildCount();
            	for(int i=1;i<j;i++){
            		ll.removeViewAt(1);
            	}
            	etContent.setText("");
            }
        });*/
 
        

        
    }
   
 
    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {
 
        fragments.add(Fragment.instantiate(this, Tab1Fragment.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab2Fragment.class.getName()));
        fragments.add(Fragment.instantiate(this, Tab3Fragment.class.getName()));
        this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        final ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
        pager.setAdapter(this.mPagerAdapter);
        /** Create an array adapter to populate dropdownlist */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, actions );
        
        
        /** Enabling dropdown list navigation for the action bar */
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        
        /** Defining Navigation listener */
        ActionBar.OnNavigationListener navigationListener = new OnNavigationListener(){

			@Override
			public boolean onNavigationItemSelected(int itemPosition,long itemId) {
				pager.setCurrentItem(itemPosition);
				Toast.makeText(getBaseContext(), "You selected : " + actions[itemPosition]  , Toast.LENGTH_SHORT).show();
				return false;
			}
        	
        };
	
		/** Setting dropdown items and item navigation listener for the actionbar */
		getActionBar().setListNavigationCallbacks(adapter, navigationListener); 
		
		pager.setOnPageChangeListener(
	            new ViewPager.SimpleOnPageChangeListener() {
	                @Override
	                public void onPageSelected(int position) {
	                    // When swiping between pages, select the
	                    // corresponding tab.
	                    getActionBar().setSelectedNavigationItem(position);
	                }
	            });
        LinearLayout mlinear = (LinearLayout)fragments.get(0).getView();
        //TextView a = (TextView) mlinear.findViewById(R.id.textView1);
        //a.setText("dghdfgh");
    }
    public class PagerAdapter extends FragmentPagerAdapter {
    	 
        private List<Fragment> fragments;
        /**
         * @param fm
         * @param fragments
         */
        public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        /* (non-Javadoc)
         * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
         */
        @Override
        public Fragment getItem(int position) {
        	if(position ==0){
        	a = new Tab1Fragment();
            return a;
        	}
        	if(position ==1){
            	b = new Tab2Fragment();
                return b;
            	}
        	if(position == 2){
            	c = new Tab3Fragment();
                return c;
            	}
			return null;
        }
     
        /* (non-Javadoc)
         * @see android.support.v4.view.PagerAdapter#getCount()
         */
        @Override
        public int getCount() {
            return this.fragments.size();
        }
        
    }
    public static class Tab1Fragment extends Fragment {
    	public Tab1Fragment(){
    	}
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        
        
            if (container == null) {
                // We have different layouts, and in one of them this
                // fragment's containing frame doesn't exist.  The fragment
                // may still be created from its saved state, but there is
                // no reason to try to create its view hierarchy because it
                // won't be displayed.  Note this is not needed -- we could
                // just run the code below, where we would create and return
                // the view hierarchy; it would just never be used.
                return null;
            }  
            return (LinearLayout)inflater.inflate(R.layout.tab_frag1_layout, container, false);
        }
        
    }
    public static class Tab2Fragment extends Fragment {
        /** (non-Javadoc)
         * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
         */
    	public Tab2Fragment(){
    	}
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            if (container == null) {
                // We have different layouts, and in one of them this
                // fragment's containing frame doesn't exist.  The fragment
                // may still be created from its saved state, but there is
                // no reason to try to create its view hierarchy because it
                // won't be displayed.  Note this is not needed -- we could
                // just run the code below, where we would create and return
                // the view hierarchy; it would just never be used.
                return null;
            }
            return (LinearLayout)inflater.inflate(R.layout.tab_frag2_layout, container, false);
        }
    }
    public static class Tab3Fragment extends Fragment {
        /** (non-Javadoc)
         * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
         */
    	public Tab3Fragment(){
    	}
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            if (container == null) {
                // We have different layouts, and in one of them this
                // fragment's containing frame doesn't exist.  The fragment
                // may still be created from its saved state, but there is
                // no reason to try to create its view hierarchy because it
                // won't be displayed.  Note this is not needed -- we could
                // just run the code below, where we would create and return
                // the view hierarchy; it would just never be used.
                return null;
            }
            return (LinearLayout)inflater.inflate(R.layout.tab_frag3_layout, container, false);
        }
    }

private void fetchText(String urlStr,int modeNumber) {
    //progressDialog = ProgressDialog.show(this, "", "Fetching Text...");
    final String url = urlStr;
    final int mode = modeNumber;
    
     
    new Thread () {
        public void run() {
            int BUFFER_SIZE = 2000;
            InputStream in = null;
            Message msg = Message.obtain();
            if(mode<4){
            msg.what=mode;
            }
            else{
             msg.what=4;
            }
            try {
                in = openHttpConnection(url);
                 
                InputStreamReader isr = new InputStreamReader(in);
                int limit =60;
                if (mode>4){
                	limit = (mode - 4)*3;
                }
                int charRead;
                int readcount = 0;
                int readOn=0;
                int readingid=0;
                boolean end=false;
                boolean start=false;
                String memo1 = "";
                String memo2 = "";
                  //text = "";
                  char[] inputBuffer = new char[BUFFER_SIZE];

                      while (true)
                      {                    
                          //---convert the chars to a String---
                    	  charRead = isr.read(inputBuffer);
                          String readString = 
                          String.copyValueOf(inputBuffer, 0, charRead);
                          if(!start && readString.indexOf("views-field-title")!=-1 ){
                        	  readOn = readString.indexOf("views-field-title");
                        	  start = true;
                          }
                          if (readcount > 5 && readcount<11){
                        	  if(start){
                        	  while(readOn < readString.lastIndexOf(">")||readOn<readString.lastIndexOf("?")){
                        		  if(memo2.length()!=0){
        							  memo2 += readString.substring(0,12-memo2.length());
        							  data.add(memo2.substring(8));
        							  limit--;
        							  if(limit==0)
                    		    	  {
                    		    		  end=true;
                    		    		  break;
                    		    	  }

        							  memo2="";
        							  readOn= 12-memo2.length();
        							  readingid=2;
                        		  }  
                        		  if(memo1.length()!=0){
                        			  memo1+=readString.substring(0,readString.indexOf("<"));
                        			  data.add(memo1);
                        			  if(limit==0)
                    		    	  {
                    		    		  end=true;
                    		    		  break;
                    		    	  }

                        			  limit--;
                        			  readOn = readString.indexOf("<");
                        			  readingid--;
                        			  memo1="";
                        			  readOn=1;
        					      }
                        		  else{
                        			  if(readString.indexOf("?",readOn) != -1)
                        			  {
                        				  if(readingid==0)
                        				  {
                        					  if(readString.indexOf("?",readOn)<BUFFER_SIZE-12){
                        					  memo2+=readString.substring(readString.indexOf("?",readOn),readString.indexOf("?",readOn)+12);
                        					  if(memo2.indexOf("news")>-1)
                        					  { 
                        						  end=true;
                        					  }
                        					  else{
                        						  if(mode==3){
                        						   versn = Integer.parseInt(memo2.substring(8));
                        						   end=true;
                        						   break;
                        						  }
                        						  if(memo2.length()!=0){
                        						  data.add(memo2.substring(8));
                        						  limit--;
                        						  if(limit==0)
                                		    	  {
                                		    		  end=true;
                                		    		  break;
                                		    	  }

                        						  memo2="";
                        						  }
                        						  
                        					  }
                        					  if(end){
                    							  break;
                    						  }
                        					  readingid=2;
                        					  readOn=readString.indexOf("?",readOn)+12;
                        					  }
                        					  else{
                        						  memo2 += readString.substring(readString.indexOf("?",readOn));
                        					  }
                        				  }
                        				  if(end){
                							  break;
                						  }
                        			  }
                        			  if(end){
            							  break;
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
                        		    	  limit--;
                        		    	  readingid--;
                        		    	  if(limit==0)
                        		    	  {
                        		    		  end=true;
                        		    		  break;
                        		    	  }

                        		      }
                        		      memo1="";
                        	      }
                        		  if (end){
                            		  break;
                            	  }
                        	  }
                        	  if(end){
    							  break;
    						  }
                        	}
                        	if(end){
    							break;
    						}  
                        	  
                          }
                          if (end){
                    		  break;
                    	  }
                          //HeadCount=0;
                          readOn=0;
                          inputBuffer = new char[BUFFER_SIZE];
                          readcount++;
                          
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
 
@SuppressLint("HandlerLeak")
private Handler messageHandler = new Handler() {
     
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
        case 1:
           // ImageView img = (ImageView) findViewById(R.id.imageview01);
            //img.setImageBitmap((Bitmap)(msg.getData().getParcelable("bitmap")));
            break;
        case 2:
        	//ll.removeAllViews();
        	//String content="";
        	for(int i=0;i<data.size()/3;i++){
        	RelativeLayout rl = (RelativeLayout) layoutInflater.inflate(R.layout.section_layout, null);
        	TextView text1 = (TextView)rl.findViewById(R.id.head);
        	text1.setText(data.get(3*i+1));
       	    TextView text2 = (TextView)rl.findViewById(R.id.text);
         	text2.setText(data.get(3*i+2));  
            //ll.addView(rl);
            memory+=data.get(3*i+1)+"^"+data.get(3*i+2)+"^";
            //content +=data.get(3*i)+"\n"+data.get(3*i+1)+"\n"+data.get(3*i+2)+"\n";
        	}
         	Button toTop = new Button(getBaseContext());
         	toTop.setText("To The Top");
         	toTop.setOnClickListener(new OnClickListener() {
         		 
                @Override
                public void onClick(View v) {
                	//sv.smoothScrollTo(0, sv.getTop());
                }
            });
         	//ll.addView(toTop);
        	//etContent.setText(content);
        	//content="";
        	//memory+="e";
        	FileWriter writer=null;
            try {
                writer = new FileWriter(tempFile);


                writer.write(memory);


                writer.close();

                Toast.makeText(getBaseContext(), "Temporarily saved contents in " + tempFile.getPath(), Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                writer = new FileWriter(version);


                writer.write(data.get(0));

 
                writer.close();

                Toast.makeText(getBaseContext(), "Temporarily saved contents in " + version.getPath(), Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        case 3:
        	if(versn-versc > 0 && versc != 0){
        		//progressDialog.dismiss();
        		fetchText("http://www.ise.eng.chula.ac.th/web/?q=news",4+versn-versc);
        	}
        	else if(versn==versc && versc != 0){
        		String strLine="";
                StringBuilder text = new StringBuilder();
                String cache="";
        		try {
                    FileReader fReader = new FileReader(tempFile);
                    BufferedReader bReader = new BufferedReader(fReader);
         
                    
                    while( (strLine=bReader.readLine()) != null  ){
                        text.append(strLine);
                    }
                    bReader.close();
                    fReader.close();
                    cache= text.toString();
                    layouter(cache);
                    
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
        		
        	}
        	else{
        		//progressDialog.dismiss();
        		fetchText("http://www.ise.eng.chula.ac.th/web/?q=news",2);
        	}

        	break;
        case 4:
        	String strLine="";
            StringBuilder text = new StringBuilder();
            String cache="";
        	 try {
                 FileReader fReader = new FileReader(tempFile);
                 BufferedReader bReader = new BufferedReader(fReader);
      
                 
                 while( (strLine=bReader.readLine()) != null  ){
                     text.append(strLine);
                 }
                 bReader.close();
                 fReader.close();
                 cache= text.toString();
                 memory="";
                 for(int i=0;i<data.size()/3;i++){
                	 memory+=data.get(3*i+1)+"^"+data.get(3*i+2)+"^";
                 }
                 cache = memory + cache;
                 
                 
                 

                 try {
                     writer = new FileWriter(tempFile);
  

                     writer.write(cache);
  
                     writer.close();
  
                     Toast.makeText(getBaseContext(), "Temporarily saved contents in " + tempFile.getPath(), Toast.LENGTH_LONG).show();
                     

                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                 layouter(cache);
                 

                 
                 
             } catch (FileNotFoundException e) {
                 e.printStackTrace();
             }catch(IOException e){
                 e.printStackTrace();
             }
        	 break;

        }
        //progressDialog.dismiss();
    }
};
private void layouter(String cache)   {
 while(cache.length()>0)
 {
 	RelativeLayout rl = (RelativeLayout) layoutInflater.inflate(R.layout.section_layout, null);
 	TextView text1 = (TextView)rl.findViewById(R.id.head);
 	text1.setText(cache.substring(0,cache.indexOf("^")));
 	cache= cache.substring(cache.indexOf("^")+1);
 	TextView text2 = (TextView)rl.findViewById(R.id.text);
 	text2.setText(cache.substring(0,cache.indexOf("^")));
 	cache= cache.substring(cache.indexOf("^")+1);
 	//ll.addView(rl);
 }
}
}
