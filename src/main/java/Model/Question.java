/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author namimac
 */
public class Question {
    private String _question;
    private String _answer;
    private List<String> _options;
    private List<String> _allOptions;
    
    public Question(String question, String answer, List<String> options){
        this._question = question;
        this._answer = answer;
        this._options = options;
        this._allOptions = new ArrayList<>();
        this._allOptions.add(answer);
        this._allOptions.addAll(options);
    }

    /**
     * @return the _question
     */
    public String getQuestion() {
        return _question;
    }

    /**
     * @param _question the _question to set
     */
    public void setQuestion(String _question) {
        this._question = _question;
    }

    /**
     * @return the _answer
     */
    public String getAnswer() {
        return _answer;
    }

    /**
     * @param _answer the _answer to set
     */
    public void setAnswer(String _answer) {
        this._answer = _answer;
    }

    /**
     * @return the _options
     */
    public List<String> getOptions() {
        return _options;
    }

    /**
     * @param _options the _options to set
     */
    public void setOptions(List<String> _options) {
        this._options = _options;
    }

    /**
     * @return the _allOptions
     */
    public List<String> getAllOptions() {
        return _allOptions;
    }

    /**
     * @param _allOptions the _allOptions to set
     */
    public void setAllOptions(List<String> _allOptions) {
        this._allOptions = _allOptions;
    }
}
