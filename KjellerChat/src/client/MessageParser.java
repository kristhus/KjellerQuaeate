package client;

import org.json.*;

public class MessageParser {
	
	private final String[] commands = {"login", "logout", "msg", "names", "help"};
	private final String[] answers = {"timestamp", "sender", "response", "content"};
	
	
	
	public JSONObject login(String uid, String pw) {
		JSONObject obj = new JSONObject();
		JSONObject o2 = new JSONObject();
		obj.append("uid", uid);
		obj.append("pw",  pw);
		o2.append(commands[0], obj);
		return obj;
	}
	
	public JSONObject createMessage(String msg) {
		JSONObject obj = new JSONObject();
		obj.append("msg", msg);
		return obj;
	}
	
	public JSONObject getNames() {
		JSONObject obj = new JSONObject();
		obj.append("names", "");
		return obj;
	}
	
	public JSONObject getHelp() {
		JSONObject obj = new JSONObject();
		obj.append("help", "");
		return obj;
	}

	private String[] getCommands(){
		return commands;
	}
	private String[] getAnswers() {
		return answers;
	}
	
}
