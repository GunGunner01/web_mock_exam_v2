package chatapp.controller;

import chatapp.model.ChatMessage;
import chatapp.model.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

//@WebServlet("/api/messages") //old version
@WebServlet("/api/messages/*") // after Ex.3
public class MessageServlet extends HttpServlet {

	private static final ChatService chatService = ChatService.getInstance();
	private static final ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();
	private static final Logger logger = Logger.getLogger(MessageServlet.class.getName());

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("Getting messages");

//	------------------------------------- Ex. 1 ------------------------------------------------------
//		http://localhost:8080/api/messages?topic=simpsons
//		http://localhost:8080/api/messages
		String topic = request.getParameter("topic");
		List<ChatMessage> messages;

		if (topic != null && !topic.isEmpty()) {
			messages = chatService.getMessages(topic);
		} else {
			messages = chatService.getMessages();
		}
//	_____________________________________ Ex. 1 ______________________________________________________
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");
		objectMapper.writeValue(response.getOutputStream(), messages);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			// parse and validate message
			ChatMessage message = objectMapper.readValue(request.getInputStream(), ChatMessage.class);
			if (message.getId() != null || message.getText() == null || message.getText().isEmpty()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			// add message
			logger.info("Adding message");
			chatService.addMessage(message);
			response.setStatus(HttpServletResponse.SC_CREATED);
			response.setContentType("application/json");
			objectMapper.writeValue(response.getOutputStream(), message);
		} catch (JsonProcessingException ex) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	//	------------------------------------- Ex. 3 ------------------------------------------------------
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// Get the message ID from the URL
		String pathInfo = request.getPathInfo();
		if (pathInfo == null || pathInfo.equals("/")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		// Extract ID from the path
		String idString = pathInfo.substring(1); // Remove the leading '/'
		try {
			Integer id = Integer.parseInt(idString);
			logger.info("Deleting message with ID: " + id);

			// Remove the message
			chatService.removeMessage(id);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	//	_____________________________________ Ex. 3 ______________________________________________________
}
