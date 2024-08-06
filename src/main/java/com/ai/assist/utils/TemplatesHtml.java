package com.ai.assist.utils;

import java.util.List;

public interface TemplatesHtml {

    List<String> CREATE_USER = List.of("Welcome to AI Assist", "create-user.html");

    List<String> UPDATE_USER = List.of("AI Assist - Updated User", "update-user.html");

    List<String> DELETE_USER = List.of("AI Assist - Deleted User", "delete-user.html");

    List<String> MESSAGE_RECEIVED = List.of("AI Assist - New Message", "message-received.html");

    List<String> MESSAGE_SENT = List.of("AI Assist - Message Sent", "message-sent.html");

    List<String> CREATE_TICKET = List.of("AI Assist - New Ticket", "create-ticket.html");

}
