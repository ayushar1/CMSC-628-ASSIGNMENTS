package com.ayush.cloudnotes;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.*;

public class DynamoDBHelper {

    private AmazonDynamoDBClient dynamoDBClient;

    public DynamoDBHelper() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(
                Constants.ACCESS_KEY,
                Constants.SECRET_KEY
        );
        dynamoDBClient = new AmazonDynamoDBClient(credentials);
        dynamoDBClient.setRegion(
                Region.getRegion(Regions.fromName(Constants.AWS_REGION))
        );
    }

    public void saveNote(Note note) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("NoteId",    new AttributeValue(note.getNoteId()));
        item.put("UserId",    new AttributeValue(note.getUserId()));
        item.put("Content",   new AttributeValue(note.getContent()));
        item.put("Timestamp", new AttributeValue(note.getTimestamp()));

        PutItemRequest request = new PutItemRequest()
                .withTableName(Constants.DYNAMO_TABLE_NAME)
                .withItem(item);

        dynamoDBClient.putItem(request);
    }

    public List<Note> getNotesForUser(String userId) {
        List<Note> notes = new ArrayList<>();

        Map<String, AttributeValue> expressionValues = new HashMap<>();
        expressionValues.put(":uid", new AttributeValue(userId));

        ScanRequest scanRequest = new ScanRequest()
                .withTableName(Constants.DYNAMO_TABLE_NAME)
                .withFilterExpression("UserId = :uid")
                .withExpressionAttributeValues(expressionValues);

        ScanResult result = dynamoDBClient.scan(scanRequest);

        for (Map<String, AttributeValue> item : result.getItems()) {
            Note note = new Note(
                    item.get("NoteId")    != null ? item.get("NoteId").getS()    : "",
                    item.get("UserId")    != null ? item.get("UserId").getS()    : "",
                    item.get("Content")   != null ? item.get("Content").getS()   : "",
                    item.get("Timestamp") != null ? item.get("Timestamp").getS() : ""
            );
            notes.add(note);
        }
        return notes;
    }
}