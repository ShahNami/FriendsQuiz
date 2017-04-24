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
public class User {
    private String _name;
    private String _password;
    private int _currentScore;
    private List<Integer> _score;
    private int _rank;
    
    public User(String name, String password){
        this._name = name;
        this._password = password;
        this._score = new ArrayList<>();
        this._currentScore = 0;
        this._rank = -1;
    }

    /**
     * @return the _name
     */
    public String getName() {
        return _name;
    }

    /**
     * @param _name the _name to set
     */
    public void setName(String _name) {
        this._name = _name;
    }

    /**
     * @return the _password
     */
    public String getPassword() {
        return _password;
    }

    /**
     * @param _password the _password to set
     */
    public void setPassword(String _password) {
        this._password = _password;
    }

    /**
     * @return the _score
     */
    public List<Integer> getScore() {
        return _score;
    }

    /**
     * @param _score the _score to set
     */
    public void setScore(List<Integer> _score) {
        this._score = _score;
    }

    /**
     * @return the _rank
     */
    public int getRank() {
        return _rank;
    }

    /**
     * @param _rank the _rank to set
     */
    public void setRank(int _rank) {
        this._rank = _rank;
    }

    /**
     * @return the _currentScore
     */
    public int getCurrentScore() {
        return _currentScore;
    }

    /**
     * @param _currentScore the _currentScore to set
     */
    public void setCurrentScore(int _currentScore) {
        this._currentScore = _currentScore;
    }
    
   
}
