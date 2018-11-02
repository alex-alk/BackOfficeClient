package backoffice.client;

import javax.swing.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import com.google.gson.reflect.TypeToken;
import java.util.*;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	JTextArea jta = new JTextArea();
	int ok = 0;
    public Main() {
        JScrollPane jsp = new JScrollPane(jta);
        this.getContentPane().add(jsp);
        Thread t = new Thread() {
            @Override
            public void run() {
                while (true) {
                    List<String> l = getPaymentList();
                    updateUI(l);
                    setTitle("Backoffice " + new Date());
                    if(ok==0) {
                    	try {sleep(1000);} catch (InterruptedException r) {}
                    }
                    else {
                    	try {sleep(500);} catch (InterruptedException r) {}
                    }
                    	
                }
            }
 
        };
        t.start();
    }
    
    public static void main(String[] args) {
        Main m = new Main();
        m.setBounds(20, 20, 450, 300);
        m.setVisible(true);
        m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    void updateUI(List<String> l) {
        this.jta.setText("");
        for (String next : l) {
            jta.append(next + "\n");
        }
    }
    public List<String> getPaymentList(){
        Client client = ClientBuilder.newClient();
        WebTarget webTarget =  client.target("http://104.197.239.22/BackOfficeWar-1/services/payment");
        Response response = webTarget.request("application/json").get();
        
        String ps = response.readEntity(String.class);
        if(ps.charAt(0)!='[') {
        	List<String> list = new ArrayList<>();
        	list.add("Connection timeout, please wait");
        	ok=1;
        	return list;
        }
        Jsonb jsonb = JsonbBuilder.create();
        TypeToken<List<Payment>> token = new TypeToken<List<Payment>>(){};
        List<Payment> p = jsonb.fromJson(ps, token.getType());
        List<String> list = new ArrayList<>();
        for(Payment pp:p) {
        list.add(pp.getName());
        }
        ok=0;
        return list;
    }
    
}
