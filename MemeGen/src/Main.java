import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
public class Main {
	public static void main (String[] args) throws Exception {
		try {
			JDA api = new JDABuilder(AccountType.BOT).setToken("NTEzNTA5MjcxNDkzODA0MDMz.DtJCfQ.wJCi_bdmbzQjSbc7ZIx3vzytvk4").buildAsync();
			api.addEventListener(new MyEventListener());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
