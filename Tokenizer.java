///////////////////
// Tokenizer: This class is used to parse string input data from a user into a usable token with actionable, validated options
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
        } else if(token.workingString.startsWith("drop table")) {
            //set command
            token.command = "drop table";
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
        } else if(token.workingString.startsWith("select * from")) {
            //set command
            token.command = "select * from";
            removeCommand(token);

            if(token.workingString.contains(",")) {
                //select type has two tables
                token.subCommand = "inner join";
                //remove the comma from the working string
                token.workingString = token.workingString.replace(",", "");
                //set the values
                token.tblName = getNextWord(token);
                token.tblSuffix = getNextWord(token);
                token.secondTblName = getNextWord(token);
                token.secondTblSuffix = getNextWord(token);
                //remove where
                getNextWord(token);
                //set the rest of the values
                token.whereClause = getNextWord(token).split("[.]")[1];
                token.testClause = getNextWord(token);
                token.secondWhereClause = getNextWord(token).split("[.]")[1];
            } else if(token.workingString.contains("inner join")) {
                //select type is an inner join
                token.subCommand = "inner join";
                //set the values
                token.tblName = getNextWord(token);
                token.tblSuffix = getNextWord(token);
                //remove inner join
                getNextWord(token);
                getNextWord(token);
                //set the rest of the values
                token.secondTblName = getNextWord(token);
                token.secondTblSuffix = getNextWord(token);
                //remove on
                getNextWord(token);
                //get the rest of the values
                token.whereClause = getNextWord(token).split("[.]")[1];
                token.testClause = getNextWord(token);
                token.secondWhereClause = getNextWord(token).split("[.]")[1];
            } else if(token.workingString.contains("left outer join")) {
                //select type is a left outer join
                token.subCommand = "left outer join";
                //set the values
                token.tblName = getNextWord(token);
                token.tblSuffix = getNextWord(token);
                //remove left outer join
                getNextWord(token);
                getNextWord(token);
                getNextWord(token);
                //set the rest of the values
                token.secondTblName = getNextWord(token);
                token.secondTblSuffix = getNextWord(token);
                //remove on
                getNextWord(token);
                //get the rest of the values
                token.whereClause = getNextWord(token).split("[.]")[1];
                token.testClause = getNextWord(token);
                token.secondWhereClause = getNextWord(token).split("[.]")[1];
            } else {
                //select type is standard
                token.subCommand = "standard";
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
            }
        } else if(token.workingString.startsWith("create table")) {
            token.command = "create table";
            removeCommand(token);

            //set tblName
            token.tblName = getNextWord(token);

            //set attributes
            removeOutsideParenthesis(token);
            token.attributes = token.workingString.split(",\\s+");
        } else if(token.workingString.startsWith("alter table")) {
            token.command = "alter table";
            removeCommand(token);

            //set tblName
            token.tblName = getNextWord(token);
            //set subCommand
            token.subCommand = getNextWord(token);
            //set attribute value
            token.attributes[0] = token.workingString;
            token.attributesCount++;
        } else if(token.workingString.startsWith("insert into")) {
            token.command = "insert into";
            removeCommand(token);

            //set tblName
            token.tblName = getNextWord(token);
            //set attribute values
            token.attributes = getAttributes(token.workingString);
        } else if(token.workingString.startsWith("select")) {
            token.command = "select";
            removeCommand(token);

            //get the selected tokens
            String nextWord = getNextWord(token);
            while(!nextWord.equals("from")) {
                token.selected[token.selectedCount] = nextWord;
                token.selectedCount++;
                nextWord = getNextWord(token);
            }

            //set tblName
            token.tblName = getNextWord(token);
            //remove where
            getNextWord(token);
            //set where clause
            token.whereClause = getNextWord(token);
            //set test clause
            token.testClause = getNextWord(token);
            //set value clause
            token.valueClause = getNextWord(token);
        } else if(token.workingString.startsWith("update")) {
            token.command = "update";
            removeCommand(token);

            //set tblName
            token.tblName = getNextWord(token);
            //remove "set"
            getNextWord(token);
            //set setClause
            token.setClause = getNextWord(token);
            //remove "="
            getNextWord(token);
            //set setValueClause
            String testClause = getNextWord(token);
            token.setValueClause = removeOusideQuotes(testClause);
            //remove "where"
            getNextWord(token);
            //set whereClause
            token.whereClause = getNextWord(token);
            //set testClause
            token.testClause = getNextWord(token);
            //set valueClause
            token.valueClause = removeOusideQuotes(getNextWord(token));
        } else if(token.workingString.startsWith("delete")) {
            token.command = "delete";
            removeCommand(token);

            //remove "from"
            getNextWord(token);
            //set tblName
            token.tblName = getNextWord(token);
            //remove "where"
            getNextWord(token);
            //set whereClause
            token.whereClause = getNextWord(token);
            //set testClause
            token.testClause = getNextWord(token);
            //set valueClause
            token.valueClause = removeOusideQuotes(getNextWord(token));
        } else if(token.workingString.startsWith("--") || token.workingString.matches("\\s+") || token.workingString.equals("")) {
            token.command = "comment";
        } else if(token.workingString.startsWith(".exit")) {
            token.command = "exit";
        } else {
            errorToken.errorString = "Invalid command received";
            return errorToken;
        }

        return token;
    }

    //getAttributes - returns a string array of attributes based on an input string
    public String[] getAttributes(String inputString) {
        inputString.trim();

        if(inputString.startsWith("values")) {
            inputString = inputString.substring(6);
        }
        if(inputString.startsWith("(")) {
            inputString = inputString.substring(1);
        }
        if(inputString.endsWith(")")) {
            inputString = inputString.substring(0, inputString.length() - 1);
        }
        String returnArray[] = new String[20];
        returnArray = inputString.split(",\\s+");

        for(int i = 0; i < returnArray.length; i++) {
            if(returnArray[i].startsWith("'")) {
                returnArray[i] = returnArray[i].substring(1);
            }
            if(returnArray[i].endsWith("'")) {
                returnArray[i] = returnArray[i].substring(0, returnArray[i].length() - 1);
            }
        }

        return returnArray;
    }

    //getNextWord - returns the next word in a string, and removes it + all white space behind it
    public String getNextWord(Token token) {
        char nextChar = 'a';
        String nextWord = "";
        if(token.workingString.contains(" ")) {
            while(nextChar !=  ' ') {
                nextWord += token.workingString.charAt(0);
                nextChar = token.workingString.charAt(1);
                token.workingString = token.workingString.substring(1);
            }
        } else {
            nextWord = token.workingString;
        }
        //remove any number of spaces between next word
        while(token.workingString.startsWith(" ")) {
            token.workingString = token.workingString.substring(1);
        }
        //remove the comma at the end, if there is one
        if(nextWord.endsWith(",")) {
            nextWord = nextWord.substring(0, nextWord.length() - 1);
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

    //removeOusideQuotes - removes outside quotes in parse -> update, delete, select
    public String removeOusideQuotes(String inputString) {
        //remove ' if it is in the beginnng of the string
        if(inputString.startsWith("'")) {
            inputString = inputString.substring(1);
        }
        //remove ' if it is in the end of the string
        if(inputString.endsWith("'")) {
            inputString = inputString.substring(0, inputString.length() - 1);
        }

        return inputString;
    }

    //removeOutsideParenthesis - used in parse -> create table action
    public void removeOutsideParenthesis(Token token) {
        //remove ( if it is in the beginning of the string
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
