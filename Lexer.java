package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.System.exit;

public class Lexer {
    private static BufferedReader fileReader;
    private static char currentChar;
    private static int stringIndex;
    private static int positionInFile;
    private static String token;
    private static String inputFileString;
    private static String inputFileName;
    private static char nextChar;
    private static String lastSuccessfulMatch;


    public Lexer(String fileName) throws IOException {
        this.fileReader = new BufferedReader(new FileReader(fileName));
        this.inputFileName = fileName;
        this.inputFileString = "";
        this.token = "";
        this.stringIndex = 0;
        lastSuccessfulMatch = "";
        this.readFile();

    }
    public static void readFile() throws IOException {
        while((positionInFile = fileReader.read()) != -1){
            currentChar = (char) positionInFile;
            inputFileString += currentChar;
        }
    }

    public static char peekNextChar(int stringIndex){
        return inputFileString.charAt(stringIndex);
    }
    public static char getChar(){
        return inputFileString.charAt(stringIndex);
    }
    public static void addToToken(){
        nextChar = inputFileString.charAt(stringIndex);
        token += nextChar;
    }

    public static void clearToken(){
        token = "";
    }

    public static String findMatches() {

        char currentChar = getChar();

        /* If the first character of token is alphanumeric */
        if (Character.isLetterOrDigit(token.charAt(0))) {

            /* If token is digit at token[0] */
            if(Character.isDigit(token.charAt(0))){

                /* If a letter follows a digit, return error */
                if(Character.isLetter(peekNextChar(stringIndex+1))){
                    return "SYNTAX ERROR: INVALID IDENTIFIER NAME";
                }

                /* If the next character not is a digit, return INT_CONST*/
                if(!Character.isDigit(peekNextChar(stringIndex+1))){
                    return "INT_CONST";
                }
            }

            if (token.equals("if")) {
                return "IF";
            } else if (token.equals("for")) {
                return "FOR";
            } else if (token.equals("while")) {
                return "WHILE";
            } else if (token.equals("procedure")) {
                return "PROC";
            } else if (token.equals("return")) {
                return "RETURN";
            } else if (token.equals("if")) {
                return "IF";
            } else if (token.equals("int")) {
                return "INT";
            } else if (token.equals("else")) {
                return "ELSE";
            } else if (token.equals("do")) {
                return "DO";
            } else if (token.equals("break")) {
                return "BREAK";
            } else if (token.equals("end")) {
                return "END";
            }

            else if(!Character.isLetterOrDigit(peekNextChar(stringIndex+1))) {
                    return "IDENT";
                }
            }

        if (!Character.isAlphabetic(token.charAt(0))) {
            if (token.equals("=")) {
                return "ASSIGN";
            } else if (token.equals("+")) {
                if (peekNextChar(stringIndex + 1) == '+') {
                    token += peekNextChar(stringIndex + 1);
                    stringIndex += 1;
                    return "INC";
                } else {
                    return "ADD_OP";
                }
            } else if (token.equals("-")) {
                return "SUB_OP";
            } else if (token.equals("*")) {
                return "MUL_OP";
            } else if (token.equals("/")) {
                return "DIV_OP";
            } else if (token.equals("%")) {
                return "MOD_OP";
            } else if (token.equals(">")) {
                if(peekNextChar(stringIndex+1) == '='){
                    token += peekNextChar(stringIndex+1);
                    stringIndex+=1;
                    return "GE";
                }
                else {
                    return "GT";
                }
            } else if (token.equals("<")) {
                if(peekNextChar(stringIndex+1) == '='){
                    token += peekNextChar(stringIndex+1);
                    stringIndex+=1;
                    return "LE";
                }
                else {
                    return "LT";
                }
            } else if (token.equals(">=")) {
                return "GE";
            } else if (token.equals("<=")) {
                return "LE";
            } else if (token.equals("++")) {
                return "INC";
            } else if (token.equals("(")) {
                return "LP";
            } else if (token.equals(")")) {
                return "RP";
            } else if (token.equals("{")) {
                return "LB";
            } else if (token.equals("}")) {
                return "RB";
            } else if (token.equals("|")) {
                return "OR";
            } else if (token.equals("&")) {
                return "AND";
            } else if (token.equals("==")) {
                return "EE";
            } else if (token.equals("!")) {
                return "NEG";
            } else if (token.equals(",")) {
                return "COMMA";
            } else if (token.equals(";")) {
                return "SEMI";
            }

            else if(!Character.isLetterOrDigit(currentChar)){
                return "SYNTAX ERROR: INVALID IDENTIFIER NAME";
            }
        }
        return "NOT FOUND";
    }

     public static void Tokenize(String fileName) throws IOException{

        Lexer lexer = new Lexer(fileName);

        String returnedMatch = new String();

        while(stringIndex != inputFileString.length()){

            char nextChar = peekNextChar(stringIndex);

            if(!Character.isWhitespace(peekNextChar(stringIndex))){
                addToToken();
                returnedMatch = findMatches();

                if(!returnedMatch.equals("NOT FOUND")){
                    lastSuccessfulMatch = returnedMatch;

                    if(lastSuccessfulMatch.equals("SYNTAX ERROR: INVALID IDENTIFIER NAME")){
                        System.out.println(lastSuccessfulMatch);
                        exit(0);
                    }

                    System.out.println(lastSuccessfulMatch);
                    clearToken();
                }
            }
            stringIndex += 1;
        }
        System.out.println(token);
    }
    public static void main(String[] args) throws IOException {

        Tokenize("/Users/nickgendron/Desktop/testcaseDemo.txt");
    }
}