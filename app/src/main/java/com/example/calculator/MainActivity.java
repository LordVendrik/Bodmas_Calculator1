package com.example.calculator;

import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public TextView Write;
    public TextView Result;

    // Array list to store number and operators Outside brackets a equation
    ArrayList<String> numbers = new ArrayList<>();
    ArrayList<String> Operators = new ArrayList<>();
    // Array list to store number and operators Outside brackets a equation


    // Array list to store number and operators Inside brackets a equation
    ArrayList<String> Operation2 = new ArrayList<>();
    ArrayList<String> Number2 = new ArrayList<>();
    // Array list to store number and operators Inside brackets a equation


    int i,index,index2,index3;  // To store Different Index used to store numbers and Operators inside list

    float a;             // Ans of calculations are stored in this number temporarily

    int pos1;               //To store Position of Open Bracket
    int pos2;               //To store Position of Close Bracket


    String Equation;       // To store Equation Inside Bracket

    boolean BracketDetected;     // To Check if there is any bracket
    boolean bracketOpen;         // TO check if bracket is opened currently

    String Error = "Equation Invalid"; //To Show error if there is any

    int bracketcount,bracketcount2; // To count no. of Open and CLose brackets

    boolean AnsMode;             //To check if ans is given already
    boolean isOperatorGiven;       //To check if Operator is already provided or not for conflicts like +* and /* and so on

    String SetAns;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Write = findViewById(R.id.textView2);
        Result = findViewById(R.id.textView);
        Write.setAutoSizeTextTypeUniformWithConfiguration(12,30,1,1);
        Write.setText("");
        Result.setText("");
    }



    /////////////////////////////////////////
    // Operators Are Shown in TextView Here//
    /////////////////////////////////////////
    public void Addition(View v){
        if (!isOperatorGiven) {                   //Checking if any operator is already given for double input
            if (!AnsMode) {                       // Checking if there is any ans already
                Write.append("+");
                if(numbers.size()==0 && !bracketOpen){                  // If there is no number and bracket is not open then
                    numbers.add("+");                                   // It will add Operator with number such as +2 or -4
                }
                else if (numbers.size() != 0 && !bracketOpen) {          // If number is already present add Plus Operator
                    Operators.add("+");
                    index++;
                }
                isOperatorGiven = true;                   // Operator is given now no other Operator can be used
            }
            else
            {
                SetAns = Result.getText().toString();
                Clear(Result);
                Write.setText(SetAns);
                numbers.add(SetAns);
                Write.append("+");
                Operators.add("+");
                index++;
                AnsMode = false;
            }
        }
    }


    public void Subtraction(View v){
        if (!isOperatorGiven) {
            if (!AnsMode) {
                if (!bracketOpen) {
                    if (numbers.isEmpty()) {
                        numbers.add("-");
                        Write.append("-");
                    } else {
                        Write.append("-");
                        Operators.add("-");
                        index++;
                    }
                } else {
                    Write.append("-");
                }
                isOperatorGiven = true;
            }
            else
            {
                SetAns = Result.getText().toString();
                Clear(Result);
                Write.setText(SetAns);
                numbers.add(SetAns);
                Write.append("-");
                Operators.add("-");
                index++;
                AnsMode = false;
            }
        }
    }


    public void Multiplication(View v){
        if (!isOperatorGiven) {
            if (!AnsMode) {
                if(numbers.size()==0 && !bracketOpen)
                    return;

                else if (numbers.size()!=0 && !bracketOpen) {
                    Operators.add("*");
                    index++;
                }
                Write.append("*");
                isOperatorGiven = true;
            }
            else
            {
                SetAns = Result.getText().toString();
                Clear(Result);
                Write.setText(SetAns);
                numbers.add(SetAns);
                Write.append("*");
                Operators.add("*");
                index++;
                AnsMode = false;
            }
        }
    }

    public void Division(View v){
        if (!isOperatorGiven) {
            if (!AnsMode) {
                if(numbers.size()==0 && !bracketOpen)
                    return;

                else if (numbers.size()!=0 &&!bracketOpen) {
                    Operators.add("/");
                    index++;
                }
                Write.append("/");
                isOperatorGiven = true;
            }
            else
            {
                SetAns = Result.getText().toString();
                Clear(Result);
                Write.setText(SetAns);
                numbers.add(SetAns);
                Write.append("/");
                Operators.add("/");
                index++;
                AnsMode = false;
            }
        }
    }
    /////////////////////////////////////////
    // Operators Are Shown in TextView Here//
    /////////////////////////////////////////



    ////////////////////////////////////
    // Calculations are performed Here//
    ////////////////////////////////////
    public void Equals(View v)
    {
        if (Write.getText().equals("")){return;}                    // If nothing is input then no response
        if (!AnsMode) {
            for (i = 0; i < Write.getText().toString().length(); i++) {                 // Looping through given equation for finding any Error in Equation and looking for bracket if any

                if(Write.getText().toString().length() == 1)
                {
                    if (numbers.get(0).equals("+")||numbers.get(0).equals("-")||numbers.get(0).equals("*")||numbers.get(0).equals("/"))
                    {
                        Result.setText(Error);
                        return;
                    }
                }
                if (Write.getText().toString().charAt(i) == '+' || Write.getText().toString().charAt(i) == '-' || Write.getText().toString().charAt(i) == '*' || Write.getText().toString().charAt(i) == '/') {
                    if (Write.getText().toString().charAt(i + 1) == '+' || Write.getText().toString().charAt(i + 1) == '-' || Write.getText().toString().charAt(i + 1) == '*' || Write.getText().toString().charAt(i + 1) == '/') {
                        Result.setText(Error);
                        return;
                    }
                }
                if (Write.getText().toString().endsWith("+") || Write.getText().toString().endsWith("-") || Write.getText().toString().endsWith("/") || Write.getText().toString().endsWith("*")) {
                    Result.setText(Error);
                    return;
                }
                if (Write.getText().toString().charAt(i) == '(') {
                    BracketDetected = true;                          // Bracket Detected now calculations will be done differently
                    bracketcount++;
                }

                if (Write.getText().toString().charAt(i) == ')') {
                    if(Write.getText().toString().charAt(i-1) == '+' || Write.getText().toString().charAt(i-1) == '-' || Write.getText().toString().charAt(i-1) == '*' || Write.getText().toString().charAt(i-1) == '/')
                    {
                        Result.setText(Error);
                        return;
                    }else {
                        bracketcount2++;
                    }
                }
            }

            if (bracketcount != bracketcount2) {                        // Checking if brackets are Closed Properly
                Result.setText(Error);
                return;
            }

            if (BracketDetected) {
                CheckBracket();                                         //Calling CheckBracket function to solve equation in bracket
                Operators.removeAll(Operators);
                numbers.removeAll(numbers);
                for (i = 0; i < Write.getText().toString().length(); i++) {              // Looping through the final Equation after solving bracket to provide final Answer
                    numbers.add("");
                    switch (Write.getText().toString().charAt(i)) {                      // Checking for the Operators and number
                        case '+':
                            if (i == 0) {                                                //Checking if there is any +1 or +2 type number
                                numbers.set(index3, numbers.get(index3) + Write.getText().toString().charAt(i));
                            }else {                                                     // If not Storing + in Operators List
                                Operators.add("+");
                                numbers.add("");
                                index3++;
                            }
                            break;
                        case '-':
                            if (i == 0) {                                                 //Checking if there is any -1 or -2 type number
                                numbers.set(index3, numbers.get(index3) + Write.getText().toString().charAt(i));
                            } else if (Write.getText().toString().charAt(i - 1) == '+' || Write.getText().toString().charAt(i - 1) == '-' || Write.getText().toString().charAt(i - 1) == '/' || Write.getText().toString().charAt(i - 1) == '*') {               // Checking for +- or -+ kind of condition so that it don't give error
                                numbers.set(index3, numbers.get(index3) + Write.getText().toString().charAt(i));
                            } else {
                                Operators.add("-");                                // If not found add - in Operator list
                                numbers.add("");                                  // SAME THINGS BELOW WITH DIFFERENT OPERATORS
                                index3++;
                            }
                            break;
                        case '/':
                            if (i == 0) {
                            continue;
                            }else {
                                Operators.add("/");
                                numbers.add("");
                                index3++;
                            }
                            break;
                        case '*':
                            if (i == 0) {
                                continue;
                            }else {
                                Operators.add("*");
                                numbers.add("");
                                index3++;
                            }
                            break;
                        default:
                            numbers.set(index3, numbers.get(index3) + Write.getText().toString().charAt(i));                // If any number found store in number list
                            break;
                    }
                }

            }

            //Performing the Calculations After solving bracket Sequence wise
            CheckDivide();
            CheckMultiply();
            CheckAddition();
            CheckSubtract();

            Result.setText(numbers.get(0));                // Giving the ans and saying that ans mode is on
            AnsMode = true;
        }
   }
    ////////////////////////////////////
    // Calculations are performed Here//
    ////////////////////////////////////




////////////////////////////////////////////////////////////////
// Main Calculations After Solving Bracket are performed here///
////////////////////////////////////////////////////////////////
    // for Divide operation see here
    public void CheckDivide()
    {
        for (i = 0;i<Operators.size();i++)       // Looping through Final Equation for Checking Divide Operator
        {
            if (Operators.get(i).equals("/"))    // If Operator Found the Index of Operator is taken and number with same index and one ahead is taken and we divide them
            {
                a = Float.parseFloat(numbers.get(i)) / Float.parseFloat(numbers.get(i+1));
                numbers.set(i,String.valueOf(a));
                Operators.remove(i);
                numbers.remove(i+1);
                CheckDivide();                  // After solution again checking for Divide Operator
            }
        }
    }

    // for Multiply operation see here
    public void CheckMultiply()
    {
        for (i = 0;i<Operators.size();i++)            // Looping through Final Equation for Checking Multiply Operator
        {
            if (Operators.get(i).equals("*"))           // If Operator Found the Index of Operator is taken and number with same index and one ahead is taken and we multiply them
            {
                a = Float.parseFloat(numbers.get(i)) * Float.parseFloat(numbers.get(i+1));
                numbers.set(i,String.valueOf(a));
                Operators.remove(i);
                numbers.remove(i+1);
                CheckMultiply();                       // After solution again checking for Multiply Operator
            }
        }
    }

    // for Add operation see here
    public void CheckAddition()
    {
        for (i = 0;i<Operators.size();i++)                 // Looping through Final Equation for Checking Plus Operator
        {
            if (Operators.get(i).equals("+"))
            {
                if (Operators.indexOf("+") != 0 && Operators.get(i-1).equals("-"))          // If Before plus Operator there is Minus we solve minus first
                {
                    a = Float.parseFloat(numbers.get(0)) - Float.parseFloat(numbers.get(1));
                    numbers.set(0,String.valueOf(a));
                    Operators.remove(0);
                    numbers.remove(1);
                }
                else{
                    a = Float.parseFloat(numbers.get(i)) + Float.parseFloat(numbers.get(i+1));         // If Before not we solve plus first
                    numbers.set(i,String.valueOf(a));
                    Operators.remove(i);
                    numbers.remove(i+1);
                }
                CheckAddition();                     // Again Checking for Plus
            }
        }
    }

    // for Minus operation see here
    public void CheckSubtract()
    {
        for (i = 0;i<Operators.size();i++)             // Same as Above Operators Look Above Comments for More Help
        {
            if (Operators.get(i).equals("-"))
            {
                a = Float.parseFloat(numbers.get(i)) - Float.parseFloat(numbers.get(i+1));
                numbers.set(i,String.valueOf(a));
                Operators.remove(i);
                numbers.remove(i+1);
                CheckSubtract();
            }
        }
    }
////////////////////////////////////////////////////////////////
// Main Calculations After Solving Bracket are performed here///
////////////////////////////////////////////////////////////////





////////////////////////////////////////////////////
// Calculations Inside Bracket Are Performed Here///
////////////////////////////////////////////////////

    // Below Code Is Almost Same as Above Main Calculations
    // It just does that between Brackets and above does this to whole equations
    public void CheckBDivide()
    {
        for(i=0;i<Operation2.size();i++)
        {
            if (Operation2.get(i).equals("/"))
            {
                a = Float.parseFloat(Number2.get(i)) / Float.parseFloat(Number2.get(i+1));
                Number2.set(i,String.valueOf(a));
                Operation2.remove(i);
                Number2.remove(i+1);
                CheckBDivide();
            }
        }
    }

    public void CheckBMultiply()
    {
        for(i=0;i<Operation2.size();i++)
        {
            if (Operation2.get(i).equals("*"))
            {
                a = Float.parseFloat(Number2.get(i)) * Float.parseFloat(Number2.get(i+1));
                Number2.set(i,String.valueOf(a));
                Operation2.remove(i);
                Number2.remove(i+1);
                CheckBMultiply();
            }
        }
    }

    public void CheckBAddition()
    {
        for (i = 0;i<Operation2.size();i++)
        {
            if (Operation2.get(i).equals("+"))
            {
                if (Operation2.indexOf("+") != 0 && Operation2.get(i-1).equals("-"))
                {
                    a = Float.parseFloat(Number2.get(0)) - Float.parseFloat(Number2.get(1));
                    Number2.set(0,String.valueOf(a));
                    Operation2.remove(0);
                    Number2.remove(1);
                }
                else{
                    a = Float.parseFloat(Number2.get(i)) + Float.parseFloat(Number2.get(i+1));
                    Number2.set(i,String.valueOf(a));
                    Operation2.remove(i);
                    Number2.remove(i+1);
                }
                CheckBAddition();
            }
        }
    }

    public  void CheckBSubtract()
    {
        for(i=0;i<Operation2.size();i++)
        {
            if (Operation2.get(i).equals("-"))
            {
                a = Float.parseFloat(Number2.get(i)) - Float.parseFloat(Number2.get(i+1));
                Number2.set(i,String.valueOf(a));
                Operation2.remove(i);
                Number2.remove(i+1);
                CheckBSubtract();
            }
        }
    }
////////////////////////////////////////////////////
// Calculations Inside Bracket Are Performed Here///
////////////////////////////////////////////////////




//////////////////////////////////////////////////////////////////////////////////////////////
//Here Equation Between Bracket is Extracted and given to Equation solve Method for solution//
//////////////////////////////////////////////////////////////////////////////////////////////
    public void CheckBracket()
    {
     for (i=0;i<Write.getText().toString().length();i++)                       // First we Loop through Text to set Bracket Postion
     {
         if (Write.getText().toString().charAt(i) == '(')                      // If bracket found we say that first bracket position is this
         {
             pos1 = i;
         }
         else if (Write.getText().toString().charAt(i) == ')')                 // if Close bracket found we conclude that our equation is between the Open and Close bracket positions
         {
             pos2 = i;
             break;
         }
     }
     if (pos2 != 0)                                            // Then we say that if bracket is closed properly solve the equation between Open bracket and CLose bracket
     {
        Equation = Write.getText().toString().substring(pos1 + 1 ,pos2);
        String Ans = EquationToSolve(Equation);             // After Extraction of Equation Inside bracket we put it in fuction to solve the Equation
        Write.setText(Write.getText().toString().replace("(" +Equation+ ")",Ans));  // Replacing the Equation with brackets with final Answer
         index2 = 0;
         Operation2.removeAll(Operation2);
         Number2.removeAll(Number2);
         pos2=0;
         pos1 = 0;
         CheckBracket();                                  // Again check for bracket after solving one pair
     }
    }
//////////////////////////////////////////////////////////////////////////////////////////////
//Here Equation Between Bracket is Extracted and given to Equation solve Method for solution//
//////////////////////////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////
    //Equation Provided From Above Function is SOlved Here//
    ////////////////////////////////////////////////////////
    public String EquationToSolve(String Equation)
    {
        Number2.add("");
        for(i=0;i<Equation.length();i++)                        // Looping through Equation for Operators and numbers
        {
            switch (Equation.charAt(i))                            // Below Switch Statement does same work as it does in EQUALS FUNCTION ABOVE SEE COMMENT THERE FOR HELP
            {                                                      // It Just Does same thing for Equation inside Bracket
                case '+':
                    if(i==0)
                    {
                        Number2.set(index2,Number2.get(index2)+Equation.charAt(i));
                    }else {
                    Operation2.add("+");
                    Number2.add("");
                    index2++;}
                    break;
                case '-':
                    if(i==0)
                    {
                        Number2.set(index2,Number2.get(index2)+Equation.charAt(i));
                    }
                    else if (Equation.charAt(i-1) == '+' || Equation.charAt(i-1) == '-' || Equation.charAt(i-1) == '/' || Equation.charAt(i-1) == '*') {
                        Number2.set(index2,Number2.get(index2)+Equation.charAt(i));
                    }
                    else{
                    Operation2.add("-");
                    Number2.add("");
                    index2++;}
                    break;
                case '*':
                    if(i==0)
                    {
                        continue;
                    }else{
                    Operation2.add("*");
                    Number2.add("");
                    index2++;}
                    break;
                case '/':
                    if(i==0)
                    {
                        continue;
                    }else{
                    Operation2.add("/");
                    Number2.add("");
                    index2++;}
                    break;
                    default:
                        Number2.set(index2,Number2.get(index2)+Equation.charAt(i));
                        break;
            }
        }

        CheckBDivide();
        CheckBMultiply();
        CheckBAddition();
        CheckBSubtract();

        return Number2.get(0);             // Gives Final ans After Solving Equation
    }
    ////////////////////////////////////////////////////////
    //Equation Provided From Above Function is SOlved Here//
    ////////////////////////////////////////////////////////



    /////////////////////////////////////////////////////////////////////////////////
    // Clear all the Variables and list so that new calculations can ber done again//
    /////////////////////////////////////////////////////////////////////////////////
    public void Clear(View v){
        Write.setText("");
        numbers.removeAll(numbers);
        Operators.removeAll(Operators);
        Operation2.removeAll(Operation2);
        Number2.removeAll(Number2);
        Result.setText("");
        bracketOpen = false;
        BracketDetected = false;
        bracketcount = 0;
        bracketcount2 = 0;
        isOperatorGiven = false;
        i = 0;
        index = 0;
        index2 = 0;
        index3 =0;
        AnsMode = false;
    }
    /////////////////////////////////////////////////////////////////////////////////
    // Clear all the Variables and list so that new calculations can ber done again//
    /////////////////////////////////////////////////////////////////////////////////



    //////////////////////////////////////////////////////////////
    // Input of Decimals and number are dealt here with brackets//
    //////////////////////////////////////////////////////////////
    public void Number1(View v) {
        if (!AnsMode) {
            Write.append("1");
            if (!bracketOpen) {
                if (numbers.isEmpty() || numbers.size() == index) {
                    numbers.add("1");
                } else if (numbers.size() < index) {
                } else {
                    numbers.set(index, numbers.get(index) + "1");
                }
            }
            isOperatorGiven = false;
        }
    }
    public void Number2(View v) {
        if (!AnsMode) {
            Write.append("2");
            if (!bracketOpen) {
                if (numbers.isEmpty() || numbers.size() == index) {
                    numbers.add("2");
                } else if (numbers.size() < index) {
                } else {
                    numbers.set(index, numbers.get(index) + "2");
                }
                isOperatorGiven = false;
            }
            isOperatorGiven = false;
        }
    }
    public void Number3(View v) {
        if (!AnsMode) {
            Write.append("3");
            if (!bracketOpen) {
                if (numbers.isEmpty() || numbers.size() == index) {
                    numbers.add("3");
                } else if (numbers.size() < index) {
                } else {
                    numbers.set(index, numbers.get(index) + "3");
                }
            }
            isOperatorGiven = false;
        }
    }
    public void Number4(View v) {
        if (!AnsMode) {
            Write.append("4");
            if (!bracketOpen) {
                if (numbers.isEmpty() || numbers.size() == index) {
                    numbers.add("4");
                } else if (numbers.size() < index) {
                } else {
                    numbers.set(index, numbers.get(index) + "4");
                }
            }
            isOperatorGiven = false;
        }
    }
    public void Number5(View v) {
        if (!AnsMode) {
            Write.append("5");
            if (!bracketOpen) {
                if (numbers.isEmpty() || numbers.size() == index) {
                    numbers.add("5");
                } else if (numbers.size() < index) {
                } else {
                    numbers.set(index, numbers.get(index) + "5");
                }
            }
            isOperatorGiven = false;
        }
    }
    public void Number6(View v) {
        if (!AnsMode) {
            Write.append("6");
            if (!bracketOpen) {
                if (numbers.isEmpty() || numbers.size() == index) {
                    numbers.add("6");
                } else if (numbers.size() < index) {
                } else {
                    numbers.set(index, numbers.get(index) + "6");
                }
            }
            isOperatorGiven = false;
        }
    }
    public void Number7(View v) {
        if (!AnsMode) {
            Write.append("7");
            if (!bracketOpen) {
                if (numbers.isEmpty() || numbers.size() == index) {
                    numbers.add("7");
                } else if (numbers.size() < index) {
                } else {
                    numbers.set(index, numbers.get(index) + "7");
                }
            }
            isOperatorGiven = false;
        }
    }
    public void Number8(View v) {
        if (!AnsMode) {
            Write.append("8");
            if (!bracketOpen) {
                if (numbers.isEmpty() || numbers.size() == index) {
                    numbers.add("8");
                } else if (numbers.size() < index) {
                } else {
                    numbers.set(index, numbers.get(index) + "8");
                }
            }
            isOperatorGiven = false;
        }
    }
    public void Number9(View v) {
        if (!AnsMode) {
            Write.append("9");
            if (!bracketOpen) {
                if (numbers.isEmpty() || numbers.size() == index) {
                    numbers.add("9");
                } else if (numbers.size() < index) {
                } else {
                    numbers.set(index, numbers.get(index) + "9");
                }
            }
            isOperatorGiven = false;
        }
    }
    public void Number0(View v) {
        if (!AnsMode) {
            Write.append("0");
            if (!bracketOpen) {
                if (numbers.isEmpty() || numbers.size() == index) {
                    numbers.add("0");
                } else if (numbers.size() < index) {
                } else {
                    numbers.set(index, numbers.get(index) + "0");
                }
            }
            isOperatorGiven = false;
        }
    }
    public void BracketL(View v) {
        if (!AnsMode) {
            Write.append("(");
            bracketOpen = true;
            isOperatorGiven = false;
        }
    }
    public void BracketR(View v) {
        if (!AnsMode) {
            Write.append(")");
            isOperatorGiven = false;
        }
    }
    public void Decimal(View view) {
        if (!AnsMode) {
            Write.append(".");
            if (!bracketOpen) {
                if (numbers.isEmpty() || numbers.size() == index) {
                    numbers.add("0.");
                } else if (numbers.size() < index) {
                } else {
                    numbers.set(index, numbers.get(index) + ".");
                }
            }
            isOperatorGiven = false;
        }
    }
    //////////////////////////////////////////////////////////////
    // Input of Decimals and number are dealt here with brackets//
    //////////////////////////////////////////////////////////////

////////////////////////////////////////
//Deleting the last word from Equation//
////////////////////////////////////////
    public void Delete(View view) {
        if (!AnsMode) {
            if (bracketOpen) {                  //If bracket is Open we SImply Delete because there is nothing inside Our list
                Write.setText(Write.getText().toString().substring(0, Write.getText().toString().length() - 1));
            }
            else {                            //if Bracket is not Open and there is Operator as last word we remove Operator from list
                if (Write.getText().toString().endsWith("+") || Write.getText().toString().endsWith("-") || Write.getText().toString().endsWith("*") || Write.getText().toString().endsWith("/")) {
                    Operators.remove(Operators.size() - 1);
                    Write.setText(Write.getText().toString().substring(0, Write.getText().toString().length() - 1));
                    index--;
                    isOperatorGiven = false;
                }

                else if (Write.getText().toString().equals("")){}               // If nothing to delete then chill

                else {                         //if Bracket is not Open and there is number as last word we remove number from list
                    numbers.set(index, numbers.get(index).substring(0, numbers.get(index).length() - 1));
                    Write.setText(Write.getText().toString().substring(0, Write.getText().toString().length() - 1));
                }
            }
        }
    }
////////////////////////////////////////
//Deleting the last word from Equation//
////////////////////////////////////////
}
