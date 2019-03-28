package com.huawei.vca.message;

import java.util.*;

public class Dialogue {

    private UUID sessionId;
    private Set<String> dialogueState;
    private String text;
    private List<String> history;
    private Map<String, String> slots;
    private int negativeCount;
    private Set<String> bagOfWords;
    private Set<String> observables;
    private String feedbackUrl;

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId() {
        sessionId = UUID.randomUUID();
    }

    public Set<String> getDialogueState() {
        return dialogueState;
    }

    public void setDialogueState(Set<String> dialogueState) {
        this.dialogueState = dialogueState;
    }

    public void addState(String state) {
        if (dialogueState == null) {
            dialogueState = new HashSet<>();
        }

        dialogueState.add(state);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (this.history == null)
            history = new ArrayList<>();

        history.add(text);
        this.text = text;
    }

    public List<String> getHistory() {
        return history;
    }

    public int getNegativeCount() {
        return negativeCount;
    }

    public void increaseNegativeCount() {
        this.negativeCount++;
    }

    public void resetNegativeCount() {
        this.negativeCount = 0;
    }

    public Map<String, String> getSlots() {
        return slots;
    }

    public void setSlots(Map<String, String> slots) {
        this.slots = slots;
    }

    public void addSlot(String key, String value) {
        if (slots == null) {
            slots = new HashMap<>();
        }

        slots.put(key, value);
    }

    public void addSlots(Map<String, String> additionalSlots) {
        this.slots.putAll(additionalSlots);
    }

    public Set<String> getBagOfWords() {
        return bagOfWords;
    }

    public void setBagOfWords(Set<String> bagOfWords) {
        this.bagOfWords = bagOfWords;
    }

    public void addWordToBag(String word) {
        if (bagOfWords == null) {
            bagOfWords = new HashSet<>();
        }

        bagOfWords.add(word);
    }

    public Set<String> getObservables() {
        return observables;
    }

    public void addObservable(String state) {
        if (observables == null)
            observables = new HashSet<>();
        observables.add(state);
    }

    public void clearObservables() {
        if (observables != null)
            observables.clear();
    }

    public void setFeedbackUrl(String feedbackUrl) {
        this.feedbackUrl = feedbackUrl;
    }

    public String getFeedbackUrl() {
        return feedbackUrl;
    }

    @Override
    public String toString() {
        return "Dialogue{" +
                "sessionId=" + sessionId +
                ", dialogueState=" + dialogueState +
                ", text='" + text + '\'' +
                ", history=" + history +
                ", slots=" + slots +
                ", negativeCount=" + negativeCount +
                ", bagOfWords=" + bagOfWords +
                ", observables=" + observables +
                '}';
    }
}
