import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import net.dv8tion.jda.core.EmbedBuilder;
public class MyEventListener extends ListenerAdapter {
	
	String value1 = "top text";
	String value2 = "bottom text";
	boolean valuesFound = false;
	String template = "";
	
	public void onMessageReceived(MessageReceivedEvent event) {
		
		double chk = Math.random();
		if (chk >= .5) {
			template = "124822590";
		}
		
		if (chk < .5 ) {
			template = "91998305";
		}
		
		if (event.getAuthor().isBot()) return;
		
		Message message = event.getMessage();
		String content = message.getContentRaw();
		MessageChannel channel = event.getChannel();
			
			if (content.contains("or") && valuesFound == false){
				String[] messageParts = content.split(" ");
				if (messageParts.length > 1) {
					int indexOfOr = 0;
					for (int i = 0; i < messageParts.length; i++){
						if(messageParts[i].equals("or")) {
							indexOfOr = i;
						}
					}
					value1 = messageParts[indexOfOr - 1];
					value2 = messageParts[indexOfOr + 1];
					valuesFound = true;
				}
			}
			else if (valuesFound) {
				
				if(content.contains(value1) && content.contains(value2)) {
					
				}
				
				else if(!content.contains(value1) && !content.contains(value2)) {
					
				}
				
				else {
					
					if (content.contains(value1)) {
						String placeholder = value1;
						value1 = value2;
						value2 = placeholder;
					}
	
				URL url;
				try {
					url = new URL("https://api.imgflip.com/caption_image");
					
					URLConnection con = url.openConnection();
					HttpURLConnection http = (HttpURLConnection)con;
					http.setRequestMethod("POST"); // PUT is another valid option
					http.setDoOutput(true);
					
					Map<String,String> arguments = new HashMap<>();
					arguments.put("template_id", template);
					arguments.put("username", "pieforce3"); 
					arguments.put("password", "password1");
					arguments.put("text0", value1); 
					arguments.put("text1", value2);
					StringJoiner sj = new StringJoiner("&");
					for(Map.Entry<String,String> entry : arguments.entrySet())
					    sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" 
					         + URLEncoder.encode(entry.getValue(), "UTF-8"));
					byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
					int length = out.length;
					
					http.setFixedLengthStreamingMode(length);
					http.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
					http.connect();
					try(OutputStream os = http.getOutputStream()) {
					    os.write(out);
					}
					
					BufferedReader bR = new BufferedReader(  new InputStreamReader(http.getInputStream()));
					String line = "";

					StringBuilder responseStrBuilder = new StringBuilder();
					while((line =  bR.readLine()) != null){

					    responseStrBuilder.append(line);
					}
					String jsonOfInput = responseStrBuilder.toString();
					int indexOfUrlStart = jsonOfInput.indexOf("http");
					int indexOfUrlEnd = jsonOfInput.indexOf(".jpg");
					String urlOfInput = jsonOfInput.substring(indexOfUrlStart, indexOfUrlEnd + 4);
					urlOfInput = urlOfInput.replaceAll("\\\\", "");
					EmbedBuilder eb = new EmbedBuilder();
					eb.setImage(urlOfInput);
					channel.sendMessage(eb.build()).queue();
					valuesFound = false;
					} catch (Exception e) {
						
						e.printStackTrace();
						channel.sendMessage("Error").queue();
					}
					} //else doesnt contain both
				} // else
			} //message received event
		}	// class
