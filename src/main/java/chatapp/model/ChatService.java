package chatapp.model;

import java.util.*;

public class ChatService {

	private static final ChatService instance = new ChatService();

	private final List<ChatMessage> messages = new ArrayList<>();
	private int lastId = 0;

	public static ChatService getInstance() {
		return instance;
	}

	private ChatService() {
		try (Scanner scanner = new Scanner(ChatService.class.getResourceAsStream("/messages.txt"))) {
			while (scanner.hasNextLine()) {
				String[] tokens = scanner.nextLine().split(":");
				addMessage(new ChatMessage(tokens[0], tokens[1]));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public List<ChatMessage> getMessages() {
		return messages;
	}

	//	------------------------------------- Ex. 1 ------------------------------------------------------
	public List<ChatMessage> getMessages(String topic) {
		List<ChatMessage> output = new ArrayList<>();

		for (ChatMessage message : messages) {
			if (message.getTopic().equalsIgnoreCase(topic)) {
				output.add(message);
			}
		}
		return output;
	}
	//	_____________________________________ Ex. 1 ______________________________________________________
	//	------------------------------------- Ex. 2 ------------------------------------------------------
	public List<String> getTopics() {

		Set<String> topicsSet = new HashSet<>();
		for (ChatMessage message : messages) {
			topicsSet.add(message.getTopic());
		}
		return new ArrayList<>(topicsSet);
	}
	//	_____________________________________ Ex. 2 ______________________________________________________

	public void addMessage(ChatMessage message) {
		message.setId(++lastId);
		messages.add(message);
	}

	//	------------------------------------- Ex. 3 ------------------------------------------------------
	public void removeMessage(Integer id) {
		for (int i = 0; i < messages.size(); ++i) {
			ChatMessage message = messages.get(i);
			if (message.getId().equals(id)) {
				messages.remove(i);
				break; // Exit the loop once the message is found and removed
			}
		}
	}
	//	_____________________________________ Ex. 3 ______________________________________________________

}
