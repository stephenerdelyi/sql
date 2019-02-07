///////////////////
// Tokenizer:
///////////////////
public class Tokenizer extends SQL {
    //parse - returns an accepted token (or errored) from a given input string
    public Token parse(String inputString) {
        Token token = new Token();
        Token errorToken = new Token("error");

        formatForParse(token, inputString);

        if(token.workingString.startsWith("create database") || token.workingString.startsWith("drop database") || token.workingString.startsWith("use")) {
            //set command
            if(token.workingString.startsWith("create database")) {
                token.command = "create database";
            } else if(token.workingString.startsWith("drop database")) {
                token.command = "drop database";
            } else if(token.workingString.startsWith("use")) {
                token.command = "use";
            }
            removeCommand(token);

            //set dbName
            if(token.workingString.matches(".*\\s+.*")) {
                errorToken.errorString = "Can not contain white space in db name";
                return errorToken;
            } else if(token.workingString.equals("")) {
                errorToken.errorString = "No database value supplied";
                return errorToken;
            } else {
                token.dbName = token.workingString;
            }
        } else if(token.workingString.startsWith("drop table") || token.workingString.startsWith("select * from")) {
            //set command
            if(token.workingString.startsWith("drop table")) {
                token.command = "drop table";
            } else if(token.workingString.startsWith("select * from")) {
                token.command = "select * from";
            }
            removeCommand(token);

            //set tblName
            if(token.workingString.matches(".*\\s+.*")) {
                errorToken.errorString = "Can not contain white space in table name";
                return errorToken;
            } else if(token.workingString.equals("")) {
                errorToken.errorString = "No table value supplied";
                return errorToken;
            } else {
                token.tblName = token.workingString;
            }
        } else if(inputString.startsWith("create table")) {
            token.command = "create table";
            removeCommand(token);

            //verify resulting string
            /*if(!token.workingString.matches("")) {
                errorToken.errorString = "Create table command invalid (general regex fail)";
                return errorToken;
            }*/

            //set tblName
            token.tblName = getNextWord(token);

            removeOutsideParenthesis(token);

            //set attributes
            token.attributes = token.workingString.split(",\\s+");
        } else if(inputString.startsWith("alter table")) {
            token.command = "alter table";
            removeCommand(token);

            //verify resulting string
            /*if(!token.workingString.matches("")) {
                errorToken.errorString = "Alter table command invalid (general regex fail)";
                return errorToken;
            }*/

            //set tblName
            token.tblName = getNextWord(token);
            //set subCommand
            token.subCommand = getNextWord(token);
            //set attribute value
            token.attributes[0] = token.workingString;
        } else if(inputString.startsWith("--")) {
            token.command = "comment";
        } else if(inputString.startsWith(".exit")) {
            token.command = "exit";
        } else {
            errorToken.errorString = "Invalid command received";
            return errorToken;
        }

        return token;
    }

    //getNextWord - returns the next word in a string, and removes it + all white space behind it
    public String getNextWord(Token token) {
        char nextChar = 'a';
        String nextWord = "";
        while(nextChar != ' ') {
            nextWord += token.workingString.charAt(0);
            nextChar = token.workingString.charAt(1);
            token.workingString = token.workingString.substring(1);
        }
        //remove any number of spaces between next word
        while(token.workingString.startsWith(" ")) {
            token.workingString = token.workingString.substring(1);
        }

        return nextWord;
    }

    //removeCommand - used after the command is ingested, this removes the command from the working string
    public void removeCommand(Token token) {
        token.workingString = token.workingString.substring(token.command.length());
        while(token.workingString.startsWith(" ")) {
            token.workingString = token.workingString.substring(1);
        }
    }

    //removeOutsideParenthesis - used in parse -> create table action
    public void removeOutsideParenthesis(Token token) {
        //remove ( if it is in the end of the string
        if(token.workingString.startsWith("(")) {
            token.workingString = token.workingString.substring(1);
        }

        //remove ) if it is in the end of the string
        if(token.workingString.endsWith(")")) {
            token.workingString = token.workingString.substring(0, token.workingString.length() - 1);
        }
    }

    //formatForParse - formats string for parsing by lowercasing letters and removing ";"
    public void formatForParse(Token token, String inputString) {
        //lowercase all chars and set working string value
        token.workingString = inputString.toLowerCase();

        //remove ; if it is in the string
        if(token.workingString.endsWith(";")) {
            token.workingString = token.workingString.substring(0, token.workingString.length() - 1);
        }
    }
}
