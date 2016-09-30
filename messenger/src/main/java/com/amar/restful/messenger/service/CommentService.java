package com.amar.restful.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.amar.restful.messenger.database.Database;
import com.amar.restful.messenger.model.Comment;
import com.amar.restful.messenger.model.ErrorMessage;
import com.amar.restful.messenger.model.Message;

public class CommentService {

	private Map<Long, Message> messages = Database.getMessages();
	
	public List<Comment> getAllComments(long messageId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId) {
		ErrorMessage errorMessage = new ErrorMessage("Not Found", 404, "www.yahoo.com");
		Response response = Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
		Message message = messages.get(messageId);
		if(message == null) {
			//throw new WebApplicationException(Status.NOT_FOUND);
			throw new NotFoundException(response);
		}
		Map<Long, Comment> comments = message.getComments();
		ErrorMessage errorMessage1 = new ErrorMessage("aaaaaaaaa", 123, "www.ebay.com");
		Response response1 = Response.status(Status.NOT_FOUND)
				.entity(errorMessage1)
				.build();
		Comment comment = comments.get(commentId);
		if(comment == null) {
			throw new WebApplicationException(response1);
		}
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if(comment.getId() < 0) {
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
	
}
